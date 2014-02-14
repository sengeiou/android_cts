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

// Don't edit this file!  It is auto-generated by frameworks/rs/api/gen_runtime.

package android.renderscript.cts;

import android.renderscript.Allocation;
import android.renderscript.RSRuntimeException;
import android.renderscript.Element;

public class TestFloor extends RSBaseCompute {

    private ScriptC_TestFloor script;
    private ScriptC_TestFloorRelaxed scriptRelaxed;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        script = new ScriptC_TestFloor(mRS);
        scriptRelaxed = new ScriptC_TestFloorRelaxed(mRS);
    }

    public class ArgumentsFloatFloat {
        public float in;
        public float out;

        public int ulf;
        public int ulfRelaxed;
    }

    private void checkFloorFloatFloat() {
        Allocation in = CreateRandomAllocation(mRS, Element.DataType.FLOAT_32, 1, 0xd87ca3ec721429f4L);
        try {
            Allocation out = Allocation.createSized(mRS, GetElement(mRS, Element.DataType.FLOAT_32, 1), INPUTSIZE);
            script.forEach_testFloorFloatFloat(in, out);
            verifyResultsFloorFloatFloat(in, out, false);
        } catch (Exception e) {
            throw new RSRuntimeException("RenderScript. Can't invoke forEach_testFloorFloatFloat: " + e.toString());
        }
        try {
            Allocation out = Allocation.createSized(mRS, GetElement(mRS, Element.DataType.FLOAT_32, 1), INPUTSIZE);
            scriptRelaxed.forEach_testFloorFloatFloat(in, out);
            verifyResultsFloorFloatFloat(in, out, true);
        } catch (Exception e) {
            throw new RSRuntimeException("RenderScript. Can't invoke forEach_testFloorFloatFloat: " + e.toString());
        }
    }

    private void verifyResultsFloorFloatFloat(Allocation in, Allocation out, boolean relaxed) {
        float[] arrayIn = new float[INPUTSIZE * 1];
        in.copyTo(arrayIn);
        float[] arrayOut = new float[INPUTSIZE * 1];
        out.copyTo(arrayOut);
        for (int i = 0; i < INPUTSIZE; i++) {
            for (int j = 0; j < 1 ; j++) {
                // Extract the inputs.
                ArgumentsFloatFloat args = new ArgumentsFloatFloat();
                args.in = arrayIn[i];
                // Figure out what the outputs should have been.
                CoreMathVerifier.computeFloor(args);
                int ulf = relaxed ? args.ulfRelaxed : args.ulf;
                // Figure out what the outputs should have been.
                boolean valid = true;
                int neededUlf = 0;
                neededUlf = (int) (Math.abs(args.out - arrayOut[i * 1 + j]) / Math.ulp(args.out) + 0.5);
                if (neededUlf > ulf) {
                    valid = false;
                }
                if (!valid) {
                    StringBuilder message = new StringBuilder();
                    message.append(String.format("Input in: %x %.16f", Float.floatToRawIntBits(args.in), args.in));
                    message.append("\n");
                    message.append(String.format("Expected output out: %x %.16f", Float.floatToRawIntBits(args.out), args.out));
                    message.append("\n");
                    message.append(String.format("Actual   output out: %x %.16f", Float.floatToRawIntBits(arrayOut[i * 1 + j]), arrayOut[i * 1 + j]));
                    neededUlf = (int) (Math.abs(args.out - arrayOut[i * 1 + j]) / Math.ulp(args.out) + 0.5);
                    if (neededUlf > ulf) {
                        message.append(String.format(" FAILED, ulf needed %d, specified %d", neededUlf, ulf));
                    }
                    message.append("\n");
                    assertTrue("Incorrect output for checkFloorFloatFloat" + (relaxed ? "_relaxed" : "") + ":\n" + message.toString(), valid);
                }
            }
        }
    }

    private void checkFloorFloat2Float2() {
        Allocation in = CreateRandomAllocation(mRS, Element.DataType.FLOAT_32, 2, 0x8ee342e98040e0d0L);
        try {
            Allocation out = Allocation.createSized(mRS, GetElement(mRS, Element.DataType.FLOAT_32, 2), INPUTSIZE);
            script.forEach_testFloorFloat2Float2(in, out);
            verifyResultsFloorFloat2Float2(in, out, false);
        } catch (Exception e) {
            throw new RSRuntimeException("RenderScript. Can't invoke forEach_testFloorFloat2Float2: " + e.toString());
        }
        try {
            Allocation out = Allocation.createSized(mRS, GetElement(mRS, Element.DataType.FLOAT_32, 2), INPUTSIZE);
            scriptRelaxed.forEach_testFloorFloat2Float2(in, out);
            verifyResultsFloorFloat2Float2(in, out, true);
        } catch (Exception e) {
            throw new RSRuntimeException("RenderScript. Can't invoke forEach_testFloorFloat2Float2: " + e.toString());
        }
    }

    private void verifyResultsFloorFloat2Float2(Allocation in, Allocation out, boolean relaxed) {
        float[] arrayIn = new float[INPUTSIZE * 2];
        in.copyTo(arrayIn);
        float[] arrayOut = new float[INPUTSIZE * 2];
        out.copyTo(arrayOut);
        for (int i = 0; i < INPUTSIZE; i++) {
            for (int j = 0; j < 2 ; j++) {
                // Extract the inputs.
                ArgumentsFloatFloat args = new ArgumentsFloatFloat();
                args.in = arrayIn[i * 2 + j];
                // Figure out what the outputs should have been.
                CoreMathVerifier.computeFloor(args);
                int ulf = relaxed ? args.ulfRelaxed : args.ulf;
                // Figure out what the outputs should have been.
                boolean valid = true;
                int neededUlf = 0;
                neededUlf = (int) (Math.abs(args.out - arrayOut[i * 2 + j]) / Math.ulp(args.out) + 0.5);
                if (neededUlf > ulf) {
                    valid = false;
                }
                if (!valid) {
                    StringBuilder message = new StringBuilder();
                    message.append(String.format("Input in: %x %.16f", Float.floatToRawIntBits(args.in), args.in));
                    message.append("\n");
                    message.append(String.format("Expected output out: %x %.16f", Float.floatToRawIntBits(args.out), args.out));
                    message.append("\n");
                    message.append(String.format("Actual   output out: %x %.16f", Float.floatToRawIntBits(arrayOut[i * 2 + j]), arrayOut[i * 2 + j]));
                    neededUlf = (int) (Math.abs(args.out - arrayOut[i * 2 + j]) / Math.ulp(args.out) + 0.5);
                    if (neededUlf > ulf) {
                        message.append(String.format(" FAILED, ulf needed %d, specified %d", neededUlf, ulf));
                    }
                    message.append("\n");
                    assertTrue("Incorrect output for checkFloorFloat2Float2" + (relaxed ? "_relaxed" : "") + ":\n" + message.toString(), valid);
                }
            }
        }
    }

    private void checkFloorFloat3Float3() {
        Allocation in = CreateRandomAllocation(mRS, Element.DataType.FLOAT_32, 3, 0x9bc8c6a50a31443eL);
        try {
            Allocation out = Allocation.createSized(mRS, GetElement(mRS, Element.DataType.FLOAT_32, 3), INPUTSIZE);
            script.forEach_testFloorFloat3Float3(in, out);
            verifyResultsFloorFloat3Float3(in, out, false);
        } catch (Exception e) {
            throw new RSRuntimeException("RenderScript. Can't invoke forEach_testFloorFloat3Float3: " + e.toString());
        }
        try {
            Allocation out = Allocation.createSized(mRS, GetElement(mRS, Element.DataType.FLOAT_32, 3), INPUTSIZE);
            scriptRelaxed.forEach_testFloorFloat3Float3(in, out);
            verifyResultsFloorFloat3Float3(in, out, true);
        } catch (Exception e) {
            throw new RSRuntimeException("RenderScript. Can't invoke forEach_testFloorFloat3Float3: " + e.toString());
        }
    }

    private void verifyResultsFloorFloat3Float3(Allocation in, Allocation out, boolean relaxed) {
        float[] arrayIn = new float[INPUTSIZE * 4];
        in.copyTo(arrayIn);
        float[] arrayOut = new float[INPUTSIZE * 4];
        out.copyTo(arrayOut);
        for (int i = 0; i < INPUTSIZE; i++) {
            for (int j = 0; j < 3 ; j++) {
                // Extract the inputs.
                ArgumentsFloatFloat args = new ArgumentsFloatFloat();
                args.in = arrayIn[i * 4 + j];
                // Figure out what the outputs should have been.
                CoreMathVerifier.computeFloor(args);
                int ulf = relaxed ? args.ulfRelaxed : args.ulf;
                // Figure out what the outputs should have been.
                boolean valid = true;
                int neededUlf = 0;
                neededUlf = (int) (Math.abs(args.out - arrayOut[i * 4 + j]) / Math.ulp(args.out) + 0.5);
                if (neededUlf > ulf) {
                    valid = false;
                }
                if (!valid) {
                    StringBuilder message = new StringBuilder();
                    message.append(String.format("Input in: %x %.16f", Float.floatToRawIntBits(args.in), args.in));
                    message.append("\n");
                    message.append(String.format("Expected output out: %x %.16f", Float.floatToRawIntBits(args.out), args.out));
                    message.append("\n");
                    message.append(String.format("Actual   output out: %x %.16f", Float.floatToRawIntBits(arrayOut[i * 4 + j]), arrayOut[i * 4 + j]));
                    neededUlf = (int) (Math.abs(args.out - arrayOut[i * 4 + j]) / Math.ulp(args.out) + 0.5);
                    if (neededUlf > ulf) {
                        message.append(String.format(" FAILED, ulf needed %d, specified %d", neededUlf, ulf));
                    }
                    message.append("\n");
                    assertTrue("Incorrect output for checkFloorFloat3Float3" + (relaxed ? "_relaxed" : "") + ":\n" + message.toString(), valid);
                }
            }
        }
    }

    private void checkFloorFloat4Float4() {
        Allocation in = CreateRandomAllocation(mRS, Element.DataType.FLOAT_32, 4, 0xa8ae4a609421a7acL);
        try {
            Allocation out = Allocation.createSized(mRS, GetElement(mRS, Element.DataType.FLOAT_32, 4), INPUTSIZE);
            script.forEach_testFloorFloat4Float4(in, out);
            verifyResultsFloorFloat4Float4(in, out, false);
        } catch (Exception e) {
            throw new RSRuntimeException("RenderScript. Can't invoke forEach_testFloorFloat4Float4: " + e.toString());
        }
        try {
            Allocation out = Allocation.createSized(mRS, GetElement(mRS, Element.DataType.FLOAT_32, 4), INPUTSIZE);
            scriptRelaxed.forEach_testFloorFloat4Float4(in, out);
            verifyResultsFloorFloat4Float4(in, out, true);
        } catch (Exception e) {
            throw new RSRuntimeException("RenderScript. Can't invoke forEach_testFloorFloat4Float4: " + e.toString());
        }
    }

    private void verifyResultsFloorFloat4Float4(Allocation in, Allocation out, boolean relaxed) {
        float[] arrayIn = new float[INPUTSIZE * 4];
        in.copyTo(arrayIn);
        float[] arrayOut = new float[INPUTSIZE * 4];
        out.copyTo(arrayOut);
        for (int i = 0; i < INPUTSIZE; i++) {
            for (int j = 0; j < 4 ; j++) {
                // Extract the inputs.
                ArgumentsFloatFloat args = new ArgumentsFloatFloat();
                args.in = arrayIn[i * 4 + j];
                // Figure out what the outputs should have been.
                CoreMathVerifier.computeFloor(args);
                int ulf = relaxed ? args.ulfRelaxed : args.ulf;
                // Figure out what the outputs should have been.
                boolean valid = true;
                int neededUlf = 0;
                neededUlf = (int) (Math.abs(args.out - arrayOut[i * 4 + j]) / Math.ulp(args.out) + 0.5);
                if (neededUlf > ulf) {
                    valid = false;
                }
                if (!valid) {
                    StringBuilder message = new StringBuilder();
                    message.append(String.format("Input in: %x %.16f", Float.floatToRawIntBits(args.in), args.in));
                    message.append("\n");
                    message.append(String.format("Expected output out: %x %.16f", Float.floatToRawIntBits(args.out), args.out));
                    message.append("\n");
                    message.append(String.format("Actual   output out: %x %.16f", Float.floatToRawIntBits(arrayOut[i * 4 + j]), arrayOut[i * 4 + j]));
                    neededUlf = (int) (Math.abs(args.out - arrayOut[i * 4 + j]) / Math.ulp(args.out) + 0.5);
                    if (neededUlf > ulf) {
                        message.append(String.format(" FAILED, ulf needed %d, specified %d", neededUlf, ulf));
                    }
                    message.append("\n");
                    assertTrue("Incorrect output for checkFloorFloat4Float4" + (relaxed ? "_relaxed" : "") + ":\n" + message.toString(), valid);
                }
            }
        }
    }

    public void testFloor() {
        checkFloorFloatFloat();
        checkFloorFloat2Float2();
        checkFloorFloat3Float3();
        checkFloorFloat4Float4();
    }
}
