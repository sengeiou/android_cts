/*
 * Copyright 2014 The Android Open Source Project
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

package android.hardware.camera2.cts.helpers;

import static junit.framework.Assert.*;

import java.util.Arrays;

/**
 * Helper set of methods to add extra useful assert functionality missing in junit.
 */
public class AssertHelpers {
    /**
     * Assert that at least one of the elements in data is non-zero.
     *
     * <p>An empty or a null array always fails.</p>
     */
    public static void assertArrayNotAllZeroes(String message, byte[] data) {
        int size = data.length;

        int i = 0;
        for (i = 0; i < size; ++i) {
            if (data[i] != 0) {
                break;
            }
        }

        assertTrue(message, i < size);
    }

    /**
     * Assert that every element in left is less than or equals to the corresponding element in
     * right.
     *
     * <p>Array sizes must match.</p>
     *
     * @param message Message to use in case the assertion fails
     * @param left Left array
     * @param right Right array
     */
    public static void assertArrayNotGreater(String message, float[] left, float[] right) {
        assertEquals("Array lengths did not match", left.length, right.length);

        String leftString = Arrays.toString(left);
        String rightString = Arrays.toString(right);

        for (int i = 0; i < left.length; ++i) {
            String msg = String.format(
                    "%s: (%s should be less than or equals than %s; item index %d; left = %s; " +
                    "right = %s)",
                    message, left[i], right[i], i, leftString, rightString);

            assertTrue(msg, left[i] <= right[i]);
        }
    }

    /**
     * Assert that every element in the value array is greater than the lower bound (exclusive).
     *
     * @param value an array of items
     * @param lowerBound the exclusive lower bound
     */
    public static void assertArrayWithinLowerBound(String message, float[] value, float lowerBound)
    {
        for (int i = 0; i < value.length; ++i) {
            assertTrue(
                    String.format("%s: (%s should be greater than than %s; item index %d in %s)",
                            message, value[i], lowerBound, i, Arrays.toString(value)),
                    value[i] > lowerBound);
        }
    }

    /**
     * Assert that every element in the value array is less than the upper bound (exclusive).
     *
     * @param value an array of items
     * @param upperBound the exclusive upper bound
     */
    public static void assertArrayWithinUpperBound(String message, float[] value, float upperBound)
    {
        for (int i = 0; i < value.length; ++i) {
            assertTrue(
                    String.format("%s: (%s should be less than than %s; item index %d in %s)",
                            message, value[i], upperBound, i, Arrays.toString(value)),
                    value[i] < upperBound);
        }
    }

    /**
     * Assert that {@code low <= value <= high}
     */
    public static void assertInRange(float value, float low, float high) {
        assertTrue(
                String.format("Value %s must be greater or equal to %s, but was lower", value, low),
                value >= low);
        assertTrue(
                String.format("Value %s must be less than or equal to %s, but was higher",
                        value, high),
                value <= high);

        // TODO: generic by using comparators
    }

    // Suppress default constructor for noninstantiability
    private AssertHelpers() { throw new AssertionError(); }
}
