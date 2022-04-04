package com.stason.testing.controller.services;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EncryptionPasswordTest {

    @Test
    void hash() {
        List<String> list1 = new ArrayList<>();
        List<String> list2 = new ArrayList<>();
        for(int i =1;i<=100;i++){
            list1.add(EncryptionPassword.hash(String.valueOf(i),EncryptionPassword.generateSalt()));
            list2.add(EncryptionPassword.hash(String.valueOf(i),EncryptionPassword.generateSalt()));
        }
        for(String s1 :list1){
            assertFalse(list2.contains(s1));
        }

    }
    @Test
    void equalsHash(){
        String password = "password";
        String salt = EncryptionPassword.generateSalt();

        assertEquals(EncryptionPassword.hash(password,salt),EncryptionPassword.hash(password,salt));
    }

    @Test
    void generateSalt() {
        List<String> list1 = new ArrayList<>();
        List<String> list2 = new ArrayList<>();
        for(int i =1;i<=10000;i++){
            list1.add(EncryptionPassword.generateSalt());
            list2.add(EncryptionPassword.generateSalt());
        }
        for(String s1 :list1){
            assertFalse(list2.contains(s1));
        }
    }
}