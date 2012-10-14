package com.example.awesomedogs.util;

import java.text.NumberFormat;

/** Class which isn't used, but which can illustrate some potential test failures. */
public class Stuff {

    /**
     * Converts some currency cents to a more readable format.
     * 
     * @param cents Amount in cents, e.g. 123.
     * @return Formatted value, e.g. "1.23".
     */
    public static String formatCents(int cents) {
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMinimumFractionDigits(2);
        nf.setMaximumFractionDigits(2);
        return nf.format(((double) cents / 100));
    }

}
