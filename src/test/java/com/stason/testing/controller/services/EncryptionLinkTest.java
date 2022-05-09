package com.stason.testing.controller.services;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EncryptionLinkTest {

    @Test
    void hash() {
        String login = "login";
        assertNotEquals(EncryptionLink.hash(login, "salt"), EncryptionLink.hash(login, "salt2"));
    }

    @Test
    void generateSalt() {
        List<String> list1 = new ArrayList<>();
        List<String> list2 = new ArrayList<>();
        for (int i = 1; i <= 10000; i++) {
            list1.add(EncryptionLink.generateSalt());
            list2.add(EncryptionLink.generateSalt());
        }
        for (String s1 : list1) {
            assertFalse(list2.contains(s1));
        }
    }
}