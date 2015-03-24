/*
 * Copyright (C) 2015 The Android Open Source Project
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

public class TestFdim extends RSBaseCompute {

    private ScriptC_TestFdim script;
    private ScriptC_TestFdimRelaxed scriptRelaxed;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        script = new ScriptC_TestFdim(mRS);
        scriptRelaxed = new ScriptC_TestFdimRelaxed(mRS);
    }

    public class ArgumentsFloatFloatFloat {
        public float inA;
        public float inB;
        public Target.Floaty out;
    }

    private void checkFdimFloatFloatFloat() {
        Allocation inA = createRandomAllocation(mRS, Element.DataType.FLOAT_32, 1, 0xf5dd38fbc3a47366l, false);
        Allocation inB = createRandomAllocation(mRS, Element.DataType.FLOAT_32, 1, 0xf5dd38fbc3a47367l, false);
        try {
            Allocation out = Allocation.createSized(mRS, getElement(mRS, Element.DataType.FLOAT_32, 1), INPUTSIZE);
            script.set_gAllocInB(inB);
            script.forEach_testFdimFloatFloatFloat(inA, out);
            verifyResultsFdimFloatFloatFloat(inA, inB, out, false);
        } catch (Exception e) {
            throw new RSRuntimeException("RenderScript. Can't invoke forEach_testFdimFloatFloatFloat: " + e.toString());
        }
        try {
            Allocation out = Allocation.createSized(mRS, getElement(mRS, Element.DataType.FLOAT_32, 1), INPUTSIZE);
            scriptRelaxed.set_gAllocInB(inB);
            scriptRelaxed.forEach_testFdimFloatFloatFloat(inA, out);
            verifyResultsFdimFloatFloatFloat(inA, inB, out, true);
        } catch (Exception e) {
            throw new RSRuntimeException("RenderScript. Can't invoke forEach_testFdimFloatFloatFloat: " + e.toString());
        }
    }

    private void verifyResultsFdimFloatFloatFloat(Allocation inA, Allocation inB, Allocation out, boolean relaxed) {
        float[] arrayInA = new float[INPUTSIZE * 1];
        inA.copyTo(arrayInA);
        float[] arrayInB = new float[INPUTSIZE * 1];
        inB.copyTo(arrayInB);
        float[] arrayOut = new float[INPUTSIZE * 1];
        out.copyTo(arrayOut);
        for (int i = 0; i < INPUTSIZE; i++) {
            for (int j = 0; j < 1 ; j++) {
                // Extract the inputs.
                ArgumentsFloatFloatFloat args = new ArgumentsFloatFloatFloat();
                args.inA = arrayInA[i];
                args.inB = arrayInB[i];
                // Figure out what the outputs should have been.
                Target target = new Target(relaxed);
                CoreMathVerifier.computeFdim(args, target);
                // Validate the outputs.
                boolean valid = true;
                if (!args.out.couldBe(arrayOut[i * 1 + j])) {
                    valid = false;
                }
                if (!valid) {
                    StringBuilder message = new StringBuilder();
                    message.append("Input inA: ");
                    message.append(String.format("%14.8g {%8x} %15a",
                            args.inA, Float.floatToRawIntBits(args.inA), args.inA));
                    message.append("\n");
                    message.append("Input inB: ");
                    message.append(String.format("%14.8g {%8x} %15a",
                            args.inB, Float.floatToRawIntBits(args.inB), args.inB));
                    message.append("\n");
                    message.append("Expected output out: ");
                    message.append(args.out.toString());
                    message.append("\n");
                    message.append("Actual   output out: ");
                    message.append(String.format("%14.8g {%8x} %15a",
                            arrayOut[i * 1 + j], Float.floatToRawIntBits(arrayOut[i * 1 + j]), arrayOut[i * 1 + j]));
                    if (!args.out.couldBe(arrayOut[i * 1 + j])) {
                        message.append(" FAIL");
                    }
                    message.append("\n");
                    assertTrue("Incorrect output for checkFdimFloatFloatFloat" +
                            (relaxed ? "_relaxed" : "") + ":\n" + message.toString(), valid);
                }
            }
        }
    }

    private void checkFdimFloat2Float2Float2() {
        Allocation inA = createRandomAllocation(mRS, Element.DataType.FLOAT_32, 2, 0xca6a96c16f167f4cl, false);
        Allocation inB = createRandomAllocation(mRS, Element.DataType.FLOAT_32, 2, 0xca6a96c16f167f4dl, false);
        try {
            Allocation out = Allocation.createSized(mRS, getElement(mRS, Element.DataType.FLOAT_32, 2), INPUTSIZE);
            script.set_gAllocInB(inB);
            script.forEach_testFdimFloat2Float2Float2(inA, out);
            verifyResultsFdimFloat2Float2Float2(inA, inB, out, false);
        } catch (Exception e) {
            throw new RSRuntimeException("RenderScript. Can't invoke forEach_testFdimFloat2Float2Float2: " + e.toString());
        }
        try {
            Allocation out = Allocation.createSized(mRS, getElement(mRS, Element.DataType.FLOAT_32, 2), INPUTSIZE);
            scriptRelaxed.set_gAllocInB(inB);
            scriptRelaxed.forEach_testFdimFloat2Float2Float2(inA, out);
            verifyResultsFdimFloat2Float2Float2(inA, inB, out, true);
        } catch (Exception e) {
            throw new RSRuntimeException("RenderScript. Can't invoke forEach_testFdimFloat2Float2Float2: " + e.toString());
        }
    }

    private void verifyResultsFdimFloat2Float2Float2(Allocation inA, Allocation inB, Allocation out, boolean relaxed) {
        float[] arrayInA = new float[INPUTSIZE * 2];
        inA.copyTo(arrayInA);
        float[] arrayInB = new float[INPUTSIZE * 2];
        inB.copyTo(arrayInB);
        float[] arrayOut = new float[INPUTSIZE * 2];
        out.copyTo(arrayOut);
        for (int i = 0; i < INPUTSIZE; i++) {
            for (int j = 0; j < 2 ; j++) {
                // Extract the inputs.
                ArgumentsFloatFloatFloat args = new ArgumentsFloatFloatFloat();
                args.inA = arrayInA[i * 2 + j];
                args.inB = arrayInB[i * 2 + j];
                // Figure out what the outputs should have been.
                Target target = new Target(relaxed);
                CoreMathVerifier.computeFdim(args, target);
                // Validate the outputs.
                boolean valid = true;
                if (!args.out.couldBe(arrayOut[i * 2 + j])) {
                    valid = false;
                }
                if (!valid) {
                    StringBuilder message = new StringBuilder();
                    message.append("Input inA: ");
                    message.append(String.format("%14.8g {%8x} %15a",
                            args.inA, Float.floatToRawIntBits(args.inA), args.inA));
                    message.append("\n");
                    message.append("Input inB: ");
                    message.append(String.format("%14.8g {%8x} %15a",
                            args.inB, Float.floatToRawIntBits(args.inB), args.inB));
                    message.append("\n");
                    message.append("Expected output out: ");
                    message.append(args.out.toString());
                    message.append("\n");
                    message.append("Actual   output out: ");
                    message.append(String.format("%14.8g {%8x} %15a",
                            arrayOut[i * 2 + j], Float.floatToRawIntBits(arrayOut[i * 2 + j]), arrayOut[i * 2 + j]));
                    if (!args.out.couldBe(arrayOut[i * 2 + j])) {
                        message.append(" FAIL");
                    }
                    message.append("\n");
                    assertTrue("Incorrect output for checkFdimFloat2Float2Float2" +
                            (relaxed ? "_relaxed" : "") + ":\n" + message.toString(), valid);
                }
            }
        }
    }

    private void checkFdimFloat3Float3Float3() {
        Allocation inA = createRandomAllocation(mRS, Element.DataType.FLOAT_32, 3, 0x1ecf74e170f480edl, false);
        Allocation inB = createRandomAllocation(mRS, Element.DataType.FLOAT_32, 3, 0x1ecf74e170f480eel, false);
        try {
            Allocation out = Allocation.createSized(mRS, getElement(mRS, Element.DataType.FLOAT_32, 3), INPUTSIZE);
            script.set_gAllocInB(inB);
            script.forEach_testFdimFloat3Float3Float3(inA, out);
            verifyResultsFdimFloat3Float3Float3(inA, inB, out, false);
        } catch (Exception e) {
            throw new RSRuntimeException("RenderScript. Can't invoke forEach_testFdimFloat3Float3Float3: " + e.toString());
        }
        try {
            Allocation out = Allocation.createSized(mRS, getElement(mRS, Element.DataType.FLOAT_32, 3), INPUTSIZE);
            scriptRelaxed.set_gAllocInB(inB);
            scriptRelaxed.forEach_testFdimFloat3Float3Float3(inA, out);
            verifyResultsFdimFloat3Float3Float3(inA, inB, out, true);
        } catch (Exception e) {
            throw new RSRuntimeException("RenderScript. Can't invoke forEach_testFdimFloat3Float3Float3: " + e.toString());
        }
    }

    private void verifyResultsFdimFloat3Float3Float3(Allocation inA, Allocation inB, Allocation out, boolean relaxed) {
        float[] arrayInA = new float[INPUTSIZE * 4];
        inA.copyTo(arrayInA);
        float[] arrayInB = new float[INPUTSIZE * 4];
        inB.copyTo(arrayInB);
        float[] arrayOut = new float[INPUTSIZE * 4];
        out.copyTo(arrayOut);
        for (int i = 0; i < INPUTSIZE; i++) {
            for (int j = 0; j < 3 ; j++) {
                // Extract the inputs.
                ArgumentsFloatFloatFloat args = new ArgumentsFloatFloatFloat();
                args.inA = arrayInA[i * 4 + j];
                args.inB = arrayInB[i * 4 + j];
                // Figure out what the outputs should have been.
                Target target = new Target(relaxed);
                CoreMathVerifier.computeFdim(args, target);
                // Validate the outputs.
                boolean valid = true;
                if (!args.out.couldBe(arrayOut[i * 4 + j])) {
                    valid = false;
                }
                if (!valid) {
                    StringBuilder message = new StringBuilder();
                    message.append("Input inA: ");
                    message.append(String.format("%14.8g {%8x} %15a",
                            args.inA, Float.floatToRawIntBits(args.inA), args.inA));
                    message.append("\n");
                    message.append("Input inB: ");
                    message.append(String.format("%14.8g {%8x} %15a",
                            args.inB, Float.floatToRawIntBits(args.inB), args.inB));
                    message.append("\n");
                    message.append("Expected output out: ");
                    message.append(args.out.toString());
                    message.append("\n");
                    message.append("Actual   output out: ");
                    message.append(String.format("%14.8g {%8x} %15a",
                            arrayOut[i * 4 + j], Float.floatToRawIntBits(arrayOut[i * 4 + j]), arrayOut[i * 4 + j]));
                    if (!args.out.couldBe(arrayOut[i * 4 + j])) {
                        message.append(" FAIL");
                    }
                    message.append("\n");
                    assertTrue("Incorrect output for checkFdimFloat3Float3Float3" +
                            (relaxed ? "_relaxed" : "") + ":\n" + message.toString(), valid);
                }
            }
        }
    }

    private void checkFdimFloat4Float4Float4() {
        Allocation inA = createRandomAllocation(mRS, Element.DataType.FLOAT_32, 4, 0x7334530172d2828el, false);
        Allocation inB = createRandomAllocation(mRS, Element.DataType.FLOAT_32, 4, 0x7334530172d2828fl, false);
        try {
            Allocation out = Allocation.createSized(mRS, getElement(mRS, Element.DataType.FLOAT_32, 4), INPUTSIZE);
            script.set_gAllocInB(inB);
            script.forEach_testFdimFloat4Float4Float4(inA, out);
            verifyResultsFdimFloat4Float4Float4(inA, inB, out, false);
        } catch (Exception e) {
            throw new RSRuntimeException("RenderScript. Can't invoke forEach_testFdimFloat4Float4Float4: " + e.toString());
        }
        try {
            Allocation out = Allocation.createSized(mRS, getElement(mRS, Element.DataType.FLOAT_32, 4), INPUTSIZE);
            scriptRelaxed.set_gAllocInB(inB);
            scriptRelaxed.forEach_testFdimFloat4Float4Float4(inA, out);
            verifyResultsFdimFloat4Float4Float4(inA, inB, out, true);
        } catch (Exception e) {
            throw new RSRuntimeException("RenderScript. Can't invoke forEach_testFdimFloat4Float4Float4: " + e.toString());
        }
    }

    private void verifyResultsFdimFloat4Float4Float4(Allocation inA, Allocation inB, Allocation out, boolean relaxed) {
        float[] arrayInA = new float[INPUTSIZE * 4];
        inA.copyTo(arrayInA);
        float[] arrayInB = new float[INPUTSIZE * 4];
        inB.copyTo(arrayInB);
        float[] arrayOut = new float[INPUTSIZE * 4];
        out.copyTo(arrayOut);
        for (int i = 0; i < INPUTSIZE; i++) {
            for (int j = 0; j < 4 ; j++) {
                // Extract the inputs.
                ArgumentsFloatFloatFloat args = new ArgumentsFloatFloatFloat();
                args.inA = arrayInA[i * 4 + j];
                args.inB = arrayInB[i * 4 + j];
                // Figure out what the outputs should have been.
                Target target = new Target(relaxed);
                CoreMathVerifier.computeFdim(args, target);
                // Validate the outputs.
                boolean valid = true;
                if (!args.out.couldBe(arrayOut[i * 4 + j])) {
                    valid = false;
                }
                if (!valid) {
                    StringBuilder message = new StringBuilder();
                    message.append("Input inA: ");
                    message.append(String.format("%14.8g {%8x} %15a",
                            args.inA, Float.floatToRawIntBits(args.inA), args.inA));
                    message.append("\n");
                    message.append("Input inB: ");
                    message.append(String.format("%14.8g {%8x} %15a",
                            args.inB, Float.floatToRawIntBits(args.inB), args.inB));
                    message.append("\n");
                    message.append("Expected output out: ");
                    message.append(args.out.toString());
                    message.append("\n");
                    message.append("Actual   output out: ");
                    message.append(String.format("%14.8g {%8x} %15a",
                            arrayOut[i * 4 + j], Float.floatToRawIntBits(arrayOut[i * 4 + j]), arrayOut[i * 4 + j]));
                    if (!args.out.couldBe(arrayOut[i * 4 + j])) {
                        message.append(" FAIL");
                    }
                    message.append("\n");
                    assertTrue("Incorrect output for checkFdimFloat4Float4Float4" +
                            (relaxed ? "_relaxed" : "") + ":\n" + message.toString(), valid);
                }
            }
        }
    }

    public void testFdim() {
        checkFdimFloatFloatFloat();
        checkFdimFloat2Float2Float2();
        checkFdimFloat3Float3Float3();
        checkFdimFloat4Float4Float4();
    }
}