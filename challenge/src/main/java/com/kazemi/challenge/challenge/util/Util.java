package com.kazemi.challenge.challenge.util;

import java.math.BigDecimal;

public class Util {

    public static Double roundUpToTwoDecimalPlaces(Double value) {
        return Math.round(value * 10.0) / 10.0;
    }

    public static BigDecimal convertFormattedStringToAmount(String value) {
        try {
            return BigDecimal.valueOf(
                    Double.valueOf(value.replace(",", "").replace("$", "")));
        } catch (Exception ex) {
            return null;
        }
    }

}
