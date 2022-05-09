package com.stason.testing.controller.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.*;

class ErrorForUserTest {

    @ParameterizedTest
    @EnumSource(ErrorForUser.class)
    void test(ErrorForUser e){

        assertNotNull(e);
    }
}