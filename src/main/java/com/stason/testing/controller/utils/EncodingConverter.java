package com.stason.testing.controller.utils;
import java.nio.charset.StandardCharsets;

/**
 * It is an util class for converting texts.
 * @author Stanislav Hlova
 * @version 1.0
 */
public class EncodingConverter {
    private EncodingConverter() {
    }

    public static String convertFromISOtoUTF8(String text){
        return new String(text.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
    }
}
