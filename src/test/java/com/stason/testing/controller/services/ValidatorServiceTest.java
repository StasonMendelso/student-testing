package com.stason.testing.controller.services;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidatorServiceTest {

    @Test
    void validateEmail() {
        assertTrue(ValidatorService.validateEmail("test@domain.com"));
        assertTrue(ValidatorService.validateEmail("lastname@domain.com"));
       // assertTrue(ValidatorService.validateEmail("test.email.with+symbol@domain.com"));
        assertTrue(ValidatorService.validateEmail("id-with-dash@domain.com"));
        assertTrue(ValidatorService.validateEmail("a@domain.com"));
        assertTrue(ValidatorService.validateEmail("example-abc@abc-domain.com"));
    //    assertTrue(ValidatorService.validateEmail("admin@mailserver1"));


        assertFalse(ValidatorService.validateEmail("example.com"));
        assertFalse(ValidatorService.validateEmail("A@b@c@domain.com"));
        assertFalse(ValidatorService.validateEmail("..test@domain.com"));
        assertFalse(ValidatorService.validateEmail("test@domain..com"));

    }

    @Test
    void validateUsername() {
        assertTrue(ValidatorService.validateUsername("Username"));
        assertTrue(ValidatorService.validateUsername("Nick"));
        assertTrue(ValidatorService.validateUsername("Ник"));
        assertTrue(ValidatorService.validateUsername("Вася"));
        assertTrue(ValidatorService.validateUsername("Ірина"));
        assertTrue(ValidatorService.validateUsername("Таїсія"));
        assertTrue(ValidatorService.validateUsername("Пётр"));

        assertFalse(ValidatorService.validateUsername("1username"));
        assertFalse(ValidatorService.validateUsername(".ssdfsd"));
        assertFalse(ValidatorService.validateUsername("uSERNAME"));
        assertFalse(ValidatorService.validateUsername("iVann123"));
        assertFalse(ValidatorService.validateUsername("sdfdFSFE"));
        assertFalse(ValidatorService.validateUsername("$#@14"));
    }

    @Test
    void validateSurname() {
        assertTrue(ValidatorService.validateUsername("Username"));
        assertTrue(ValidatorService.validateUsername("Nick"));
        assertTrue(ValidatorService.validateUsername("Ник"));
        assertTrue(ValidatorService.validateUsername("Вася"));
        assertTrue(ValidatorService.validateUsername("Ірина"));
        assertTrue(ValidatorService.validateUsername("Таїсія"));
        assertTrue(ValidatorService.validateUsername("Пётр"));

        assertFalse(ValidatorService.validateUsername("1username"));
        assertFalse(ValidatorService.validateUsername(".ssdfsd"));
        assertFalse(ValidatorService.validateUsername("uSERNAME"));
        assertFalse(ValidatorService.validateUsername("iVann123"));
        assertFalse(ValidatorService.validateUsername("sdfdFSFE"));
        assertFalse(ValidatorService.validateUsername("$#@14"));
    }

    @Test
    void validatePassword() {
        assertTrue(ValidatorService.validatePassword("11AAaa!!"));
        assertTrue(ValidatorService.validatePassword("2131AAasdasd@@"));
        assertTrue(ValidatorService.validatePassword("username343NH$$"));
        assertFalse(ValidatorService.validatePassword("11AAaagg"));
        assertFalse(ValidatorService.validatePassword("11AA"));
        assertFalse(ValidatorService.validatePassword("1$#%^#$mDSN  SD"));
        assertFalse(ValidatorService.validatePassword("11AAaagg"));

    }

    @Test
    void isPasswordRepeated() {
        assertTrue(ValidatorService.isPasswordRepeated("1","1"));
        assertTrue(ValidatorService.isPasswordRepeated("11AAaa!!","11AAaa!!"));
    }
}