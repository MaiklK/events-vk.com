package com.eventsvk.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ExtractUtil {

    public static Long extractLong(Object value) {
        if (value == null) {
            return 0L;
        }
        if (value instanceof Number number) {
            return number.longValue();
        }
        try {
            return Long.parseLong(value.toString());
        } catch (NumberFormatException ex) {
            return 0L;
        }
    }
}
