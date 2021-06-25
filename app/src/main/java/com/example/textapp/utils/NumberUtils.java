package com.example.textapp.utils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;

public class NumberUtils {

    private static final String FORMAT_PATTERN_BR = "0.###";

    private NumberUtils() {

    }

    public static Boolean isNumber(Object value) {
        if (value instanceof Integer || value instanceof Long || value instanceof Double || value instanceof Float || value instanceof BigDecimal || value instanceof BigInteger)
            return Boolean.TRUE;

        return Boolean.FALSE;
    }

    public static String formatNumberBR(Object value) {
        if (Boolean.FALSE.equals(NumberUtils.isNumber(value)))
            throw new RuntimeException("O valor deve ser um numero");

        BigDecimal bigDecimal = new BigDecimal(value.toString());

        DecimalFormat df = new DecimalFormat(FORMAT_PATTERN_BR);
        df.setMinimumFractionDigits(0);
        df.setMaximumFractionDigits(24);
        return df.format(bigDecimal);
    }

}
