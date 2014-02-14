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

#pragma version(1)
#pragma rs java_package_name(android.renderscript.cts)

// Don't edit this file!  It is auto-generated by frameworks/rs/api/gen_runtime.

rs_allocation gAllocOutCosptr;

float __attribute__((kernel)) testSincosFloatFloatFloat(float inV, unsigned int x) {
    float outCosptr = 0;
    float out = sincos(inV, &outCosptr);
    rsSetElementAt_float(gAllocOutCosptr, outCosptr, x);
    return out;
}

float2 __attribute__((kernel)) testSincosFloat2Float2Float2(float2 inV, unsigned int x) {
    float2 outCosptr = 0;
    float2 out = sincos(inV, &outCosptr);
    rsSetElementAt_float2(gAllocOutCosptr, outCosptr, x);
    return out;
}

float3 __attribute__((kernel)) testSincosFloat3Float3Float3(float3 inV, unsigned int x) {
    float3 outCosptr = 0;
    float3 out = sincos(inV, &outCosptr);
    rsSetElementAt_float3(gAllocOutCosptr, outCosptr, x);
    return out;
}

float4 __attribute__((kernel)) testSincosFloat4Float4Float4(float4 inV, unsigned int x) {
    float4 outCosptr = 0;
    float4 out = sincos(inV, &outCosptr);
    rsSetElementAt_float4(gAllocOutCosptr, outCosptr, x);
    return out;
}
