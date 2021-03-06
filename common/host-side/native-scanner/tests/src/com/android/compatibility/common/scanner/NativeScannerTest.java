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

package com.android.compatibility.common.scanner;

import com.android.compatibility.common.scanner.NativeScanner;

import junit.framework.TestCase;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;
import java.util.Iterator;

public class NativeScannerTest extends TestCase {

    public void testSingleTestNamesCase() throws Exception {
        StringReader singleTestString = new StringReader("FakeTestCase.\n  FakeTestName\n");
        BufferedReader reader = new BufferedReader(singleTestString);

        List<String> names = NativeScanner.getTestNames(reader, "TestSuite");
        Iterator<String> it = names.iterator();
        assertEquals("suite:TestSuite", it.next());
        assertEquals("case:FakeTestCase", it.next());
        assertEquals("test:FakeTestName", it.next());
        assertFalse(it.hasNext());
    }

    public void testMultipleTestNamesCase() throws Exception {
        StringReader singleTestString = new StringReader(
          "Case1.\n  Test1\n  Test2\nCase2.\n  Test3\n Test4\n");
        BufferedReader reader = new BufferedReader(singleTestString);

        List<String> names = NativeScanner.getTestNames(reader, "TestSuite");

        Iterator<String> it = names.iterator();
        assertEquals("suite:TestSuite", it.next());
        assertEquals("case:Case1", it.next());
        assertEquals("test:Test1", it.next());
        assertEquals("test:Test2", it.next());
        assertEquals("case:Case2", it.next());
        assertEquals("test:Test3", it.next());
        assertEquals("test:Test4", it.next());
        assertFalse(it.hasNext());
    }

    public void testMissingTestCaseNameCase() throws IOException {
        StringReader singleTestString = new StringReader("  Test1\n");
        BufferedReader reader = new BufferedReader(singleTestString);

        try {
            NativeScanner.getTestNames(reader, "TestSuite");
            fail("Expected RuntimeException");
        } catch (RuntimeException expected) {}
    }
}
