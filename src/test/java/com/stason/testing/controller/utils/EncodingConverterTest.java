package com.stason.testing.controller.utils;

import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class EncodingConverterTest {
    @Test
    public void testEncodingConverter(){
        String word = "Слово";
        String ISO = new String(word.getBytes(), StandardCharsets.ISO_8859_1);
        assertEquals(word, EncodingConverter.convertFromISOtoUTF8(ISO));

        List<String> array =Arrays.asList("Перший","Другий","Абракадабра","234Инглши-Ukraine","!№;.%:?*()_+-");
        List<String> isoArray = array.stream().map(s -> new String(s.getBytes(),StandardCharsets.ISO_8859_1)).collect(Collectors.toList());
        assertEquals(array,isoArray.stream().map(EncodingConverter::convertFromISOtoUTF8).collect(Collectors.toList()));
    }
}