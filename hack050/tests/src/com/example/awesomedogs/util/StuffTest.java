package com.example.awesomedogs.util;

import junit.framework.TestCase;

public class StuffTest extends TestCase {

    // This test case will fail in locales which use the
    // decimal comma rather than the decimal point
    public static void testFormatCents() {
        assertEquals("0.00", Stuff.formatCents(0));
        assertEquals("0.01", Stuff.formatCents(1));
        assertEquals("0.99", Stuff.formatCents(99));
        assertEquals("1.23", Stuff.formatCents(123));
        assertEquals("10.00", Stuff.formatCents(1000));
    }

}
