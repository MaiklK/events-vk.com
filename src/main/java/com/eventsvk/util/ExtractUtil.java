package com.eventsvk.util;

import lombok.experimental.UtilityClass;

import java.util.Map;

@UtilityClass
public class ExtractUtil {
    public static String extractRequiredString(Map<String, Object> body) {
        Object value = body.get("access_token");
        if (value == null) {
            throw new IllegalStateException("VK token response does not contain required field '" + "access_token" + "': " + body);
        }
        return value.toString();
    }

    public static long extractLong(Object value) {
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
