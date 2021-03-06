/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package android.media.cts;

import android.media.AudioManager;
import android.media.MediaCodec;
import android.media.MediaCodecInfo;
import android.media.MediaCodecList;
import android.media.MediaCrypto;
import android.media.MediaCryptoException;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.net.Uri;
import android.util.Log;
import android.view.SurfaceHolder;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * JB(API 16) introduces {@link MediaCodec} API.  It allows apps have more control over
 * media playback, pushes individual frames to decoder and supports decryption via
 * {@link MediaCrypto} API.
 *
 * {@link MediaDrm} can be used to obtain keys for decrypting protected media streams,
 * in conjunction with MediaCrypto.
 */
public class MediaCodecCencPlayer implements MediaTimeProvider {
    private static final String TAG = MediaCodecCencPlayer.class.getSimpleName();

    private static final int STATE_IDLE = 1;
    private static final int STATE_PREPARING = 2;
    private static final int STATE_PLAYING = 3;
    private static final int STATE_PAUSED = 4;

    private static final UUID CLEARKEY_SCHEME_UUID =
            new UUID(0x1077efecc0b24d02L, 0xace33c1e52e2fb4bL);

    private boolean mEncryptedAudio;
    private boolean mEncryptedVideo;
    private volatile boolean mThreadStarted = false;
    private byte[] mSessionId;
    private CodecState mAudioTrackState;
    private int mMediaFormatHeight;
    private int mMediaFormatWidth;
    private int mState;
    private long mDeltaTimeUs;
    private long mDurationUs;
    private Map<Integer, CodecState> mAudioCodecStates;
    private Map<Integer, CodecState> mVideoCodecStates;
    private Map<String, String> mAudioHeaders;
    private Map<String, String> mVideoHeaders;
    private Map<UUID, byte[]> mPsshInitData;
    private MediaCrypto mCrypto;
    private MediaExtractor mAudioExtractor;
    private MediaExtractor mVideoExtractor;
    private SurfaceHolder mSurfaceHolder;
    private Thread mThread;
    private Uri mAudioUri;
    private Uri mVideoUri;

    private static final byte[] PSSH = hexStringToByteArray(
            "0000003470737368" +  // BMFF box header (4 bytes size + 'pssh')
            "01000000" +          // Full box header (version = 1 flags = 0)
            "1077efecc0b24d02" +  // SystemID
            "ace33c1e52e2fb4b" +
            "00000001" +          // Number of key ids
            "60061e017e477e87" +  // Key id
            "7e57d00d1ed00d1e" +
            "00000000"            // Size of Data, must be zero
            );

    /**
     * Convert a hex string into byte array.
     */
    private static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }

    /*
     * Media player class to stream CENC content using MediaCodec class.
     */
    public MediaCodecCencPlayer(SurfaceHolder holder, byte[] sessionId) {
        mSessionId = sessionId;
        mSurfaceHolder = holder;
        mState = STATE_IDLE;
        mThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (mThreadStarted == true) {
                    doSomeWork();
                    if (mAudioTrackState != null) {
                        mAudioTrackState.process();
                    }
                    try {
                        Thread.sleep(5);
                    } catch (InterruptedException ex) {
                        Log.d(TAG, "Thread interrupted");
                    }
                }
            }
        });
    }

    public void setAudioDataSource(Uri uri, Map<String, String> headers, boolean encrypted) {
        mAudioUri = uri;
        mAudioHeaders = headers;
        mEncryptedAudio = encrypted;
    }

    public void setVideoDataSource(Uri uri, Map<String, String> headers, boolean encrypted) {
        mVideoUri = uri;
        mVideoHeaders = headers;
        mEncryptedVideo = encrypted;
    }

    public final int getMediaFormatHeight() {
        return mMediaFormatHeight;
    }

    public final int getMediaFormatWidth() {
        return mMediaFormatWidth;
    }

    public final Map<UUID, byte[]> getPsshInfo() {
        // TODO (edwinwong@)
        // Remove the if statement when we get content that has the clear key system id.
        if (mPsshInitData == null ||
                (mPsshInitData != null && !mPsshInitData.containsKey(CLEARKEY_SCHEME_UUID))) {
            mPsshInitData = new HashMap<UUID, byte[]>();
            mPsshInitData.put(CLEARKEY_SCHEME_UUID, PSSH);
        }
        return mPsshInitData;
    }

    private void prepareAudio() throws IOException {
        boolean hasAudio = false;
        for (int i = mAudioExtractor.getTrackCount(); i-- > 0;) {
            MediaFormat format = mAudioExtractor.getTrackFormat(i);
            String mime = format.getString(MediaFormat.KEY_MIME);

            Log.d(TAG, "audio track #" + i + " " + format + " " + mime +
                  " Is ADTS:" + getMediaFormatInteger(format, MediaFormat.KEY_IS_ADTS) +
                  " Sample rate:" + getMediaFormatInteger(format, MediaFormat.KEY_SAMPLE_RATE) +
                  " Channel count:" +
                  getMediaFormatInteger(format, MediaFormat.KEY_CHANNEL_COUNT));

            if (!hasAudio) {
                mAudioExtractor.selectTrack(i);
                addTrack(i, format, mEncryptedAudio);
                hasAudio = true;

                if (format.containsKey(MediaFormat.KEY_DURATION)) {
                    long durationUs = format.getLong(MediaFormat.KEY_DURATION);

                    if (durationUs > mDurationUs) {
                        mDurationUs = durationUs;
                    }
                    Log.d(TAG, "audio track format #" + i +
                            " Duration:" + mDurationUs + " microseconds");
                }

                if (hasAudio) {
                    break;
                }
            }
        }
    }

    private void prepareVideo() throws IOException {
        boolean hasVideo = false;

        for (int i = mVideoExtractor.getTrackCount(); i-- > 0;) {
            MediaFormat format = mVideoExtractor.getTrackFormat(i);
            String mime = format.getString(MediaFormat.KEY_MIME);

            mMediaFormatHeight = getMediaFormatInteger(format, MediaFormat.KEY_HEIGHT);
            mMediaFormatWidth = getMediaFormatInteger(format, MediaFormat.KEY_WIDTH);
            Log.d(TAG, "video track #" + i + " " + format + " " + mime +
                  " Width:" + mMediaFormatWidth + ", Height:" + mMediaFormatHeight);

            if (!hasVideo) {
                mVideoExtractor.selectTrack(i);
                addTrack(i, format, mEncryptedVideo);

                hasVideo = true;

                if (format.containsKey(MediaFormat.KEY_DURATION)) {
                    long durationUs = format.getLong(MediaFormat.KEY_DURATION);

                    if (durationUs > mDurationUs) {
                        mDurationUs = durationUs;
                    }
                    Log.d(TAG, "track format #" + i + " Duration:" +
                            mDurationUs + " microseconds");
                }

                if (hasVideo) {
                    break;
                }
            }
        }
        return;
    }

    public void prepare() throws IOException, MediaCryptoException {
        if (null == mAudioExtractor) {
            mAudioExtractor = new MediaExtractor();
            if (null == mAudioExtractor) {
                Log.e(TAG, "Cannot create Audio extractor.");
                return;
            }
        }

        if (null == mVideoExtractor){
            mVideoExtractor = new MediaExtractor();
            if (null == mVideoExtractor) {
                Log.e(TAG, "Cannot create Video extractor.");
                return;
            }
        }

        mAudioExtractor.setDataSource(mAudioUri.toString(), mAudioHeaders);
        mVideoExtractor.setDataSource(mVideoUri.toString(), mVideoHeaders);
        mPsshInitData = mVideoExtractor.getPsshInfo();

        if (null == mCrypto && (mEncryptedVideo || mEncryptedAudio)) {
            try {
                byte[] initData = new byte[0];
                mCrypto = new MediaCrypto(CLEARKEY_SCHEME_UUID, initData);
            } catch (MediaCryptoException e) {
                reset();
                Log.e(TAG, "Failed to create MediaCrypto instance.");
                throw e;
            }
            mCrypto.setMediaDrmSession(mSessionId);
        } else {
            reset();
            mCrypto.release();
            mCrypto = null;
        }

        if (null == mVideoCodecStates) {
            mVideoCodecStates = new HashMap<Integer, CodecState>();
        } else {
            mVideoCodecStates.clear();
        }

        if (null == mAudioCodecStates) {
            mAudioCodecStates = new HashMap<Integer, CodecState>();
        } else {
            mAudioCodecStates.clear();
        }

        prepareVideo();
        prepareAudio();

        mState = STATE_PAUSED;
    }

    private void addTrack(int trackIndex, MediaFormat format,
            boolean encrypted) throws IOException {
        String mime = format.getString(MediaFormat.KEY_MIME);
        boolean isVideo = mime.startsWith("video/");
        boolean isAudio = mime.startsWith("audio/");

        MediaCodec codec;

        if (encrypted && mCrypto.requiresSecureDecoderComponent(mime)) {
            codec = MediaCodec.createByCodecName(
                    getSecureDecoderNameForMime(mime));
        } else {
            codec = MediaCodec.createDecoderByType(mime);
        }

        codec.configure(
                format,
                isVideo ? mSurfaceHolder.getSurface() : null,
                mCrypto,
                0);

        CodecState state;
        if (isVideo) {
            state = new CodecState((MediaTimeProvider)this, mVideoExtractor,
                            trackIndex, format, codec, true, false,
                            AudioManager.AUDIO_SESSION_ID_GENERATE);
            mVideoCodecStates.put(Integer.valueOf(trackIndex), state);
        } else {
            state = new CodecState((MediaTimeProvider)this, mAudioExtractor,
                            trackIndex, format, codec, true, false,
                            AudioManager.AUDIO_SESSION_ID_GENERATE);
            mAudioCodecStates.put(Integer.valueOf(trackIndex), state);
        }

        if (isAudio) {
            mAudioTrackState = state;
        }
    }

    protected int getMediaFormatInteger(MediaFormat format, String key) {
        return format.containsKey(key) ? format.getInteger(key) : 0;
    }

    protected String getSecureDecoderNameForMime(String mime) {
        int n = MediaCodecList.getCodecCount();
        for (int i = 0; i < n; ++i) {
            MediaCodecInfo info = MediaCodecList.getCodecInfoAt(i);

            if (info.isEncoder()) {
                continue;
            }

            String[] supportedTypes = info.getSupportedTypes();

            for (int j = 0; j < supportedTypes.length; ++j) {
                if (supportedTypes[j].equalsIgnoreCase(mime)) {
                    return info.getName() + ".secure";
                }
            }
        }
        return null;
    }

    public void start() {
        Log.d(TAG, "start");

        if (mState == STATE_PLAYING || mState == STATE_PREPARING) {
            return;
        } else if (mState == STATE_IDLE) {
            mState = STATE_PREPARING;
            return;
        } else if (mState != STATE_PAUSED) {
            throw new IllegalStateException();
        }

        for (CodecState state : mVideoCodecStates.values()) {
            state.start();
        }

        for (CodecState state : mAudioCodecStates.values()) {
            state.start();
        }

        mDeltaTimeUs = -1;
        mState = STATE_PLAYING;
    }

    public void startWork() throws IOException, MediaCryptoException, Exception {
        try {
            // Just change state from STATE_IDLE to STATE_PREPARING.
            start();
            // Extract media information from uri asset, and change state to STATE_PAUSED.
            prepare();
            // Start CodecState, and change from STATE_PAUSED to STATE_PLAYING.
            start();
        } catch (IOException e) {
            throw e;
        } catch (MediaCryptoException e) {
            throw e;
        }

        mThreadStarted = true;
        mThread.start();
    }

    public void startThread() {
        start();
        mThreadStarted = true;
        mThread.start();
    }

    public void pause() {
        Log.d(TAG, "pause");

        if (mState == STATE_PAUSED) {
            return;
        } else if (mState != STATE_PLAYING) {
            throw new IllegalStateException();
        }

        for (CodecState state : mVideoCodecStates.values()) {
            state.pause();
        }

        for (CodecState state : mAudioCodecStates.values()) {
            state.pause();
        }

        mState = STATE_PAUSED;
    }

    public void reset() {
        if (mState == STATE_PLAYING) {
            mThreadStarted = false;

            try {
                mThread.join();
            } catch (InterruptedException ex) {
                Log.d(TAG, "mThread.join " + ex);
            }

            pause();
        }

        if (mVideoCodecStates != null) {
            for (CodecState state : mVideoCodecStates.values()) {
                state.release();
            }
            mVideoCodecStates = null;
        }

        if (mAudioCodecStates != null) {
            for (CodecState state : mAudioCodecStates.values()) {
                state.release();
            }
            mAudioCodecStates = null;
        }

        if (mAudioExtractor != null) {
            mAudioExtractor.release();
            mAudioExtractor = null;
        }

        if (mVideoExtractor != null) {
            mVideoExtractor.release();
            mVideoExtractor = null;
        }

        if (mCrypto != null) {
            mCrypto.release();
            mCrypto = null;
        }

        mDurationUs = -1;
        mState = STATE_IDLE;
    }

    public boolean isEnded() {
        for (CodecState state : mVideoCodecStates.values()) {
          if (!state.isEnded()) {
            return false;
          }
        }

        for (CodecState state : mAudioCodecStates.values()) {
            if (!state.isEnded()) {
              return false;
            }
        }

        return true;
    }

    private void doSomeWork() {
        try {
            for (CodecState state : mVideoCodecStates.values()) {
                state.doSomeWork();
            }
        } catch (MediaCodec.CryptoException e) {
            throw new Error("Video CryptoException w/ errorCode "
                    + e.getErrorCode() + ", '" + e.getMessage() + "'");
        } catch (IllegalStateException e) {
            throw new Error("Video CodecState.feedInputBuffer IllegalStateException " + e);
        }

        try {
            for (CodecState state : mAudioCodecStates.values()) {
                state.doSomeWork();
            }
        } catch (MediaCodec.CryptoException e) {
            throw new Error("Audio CryptoException w/ errorCode "
                    + e.getErrorCode() + ", '" + e.getMessage() + "'");
        } catch (IllegalStateException e) {
            throw new Error("Aduio CodecState.feedInputBuffer IllegalStateException " + e);
        }

    }

    public long getNowUs() {
        if (mAudioTrackState == null) {
            return System.currentTimeMillis() * 1000;
        }

        return mAudioTrackState.getAudioTimeUs();
    }

    public long getRealTimeUsForMediaTime(long mediaTimeUs) {
        if (mDeltaTimeUs == -1) {
            long nowUs = getNowUs();
            mDeltaTimeUs = nowUs - mediaTimeUs;
        }

        return mDeltaTimeUs + mediaTimeUs;
    }

    public int getDuration() {
        return (int)((mDurationUs + 500) / 1000);
    }

    public int getCurrentPosition() {
        if (mVideoCodecStates == null) {
                return 0;
        }

        long positionUs = 0;

        for (CodecState state : mVideoCodecStates.values()) {
            long trackPositionUs = state.getCurrentPositionUs();

            if (trackPositionUs > positionUs) {
                positionUs = trackPositionUs;
            }
        }
        return (int)((positionUs + 500) / 1000);
    }

}
