package com.stason.testing.controller.utils;


import java.nio.charset.StandardCharsets;

public class EncodingConverter {
    private EncodingConverter() {
    }

    public static String convertFromISOtoUTF8(String text){
        return new String(text.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
    }
}
