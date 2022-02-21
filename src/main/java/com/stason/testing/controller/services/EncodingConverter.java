package com.stason.testing.controller.services;

import java.nio.charset.StandardCharsets;

public abstract class EncodingConverter {
    public static String convertFromISOtoUTF8(String text){
        return new String(text.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
    }
}
