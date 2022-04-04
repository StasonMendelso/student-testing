package com.stason.testing.controller.utils;

public enum ErrorForUser {
    //VALIDATIONS
    INVALID_LOGIN,
    INVALID_PASSWORD,
    INVALID_CAPTCHA,
    //ACCOUNT
    ACCOUNT_IS_LOGGED,
    ACCOUNT_IS_BLOCKED,
    ACCOUNT_NOT_FOUND,
    //TEST VALIDATION
    INVALID_TEST_NAME,
    INVALID_DISCIPLINE_NAME,
    INVALID_TEST_DIFFICULTY,
    INVALID_TEST_DURATION,
    SUCH_TEST_NAME_ALREADY_EXISTS,
    INVALID_QUESTION_NAME,
    INVALID_ANSWER_NAME,
    EMPTY_ANSWER_OPTION,
    EMPTY_RIGHT_ANSWER_OPTION,

    SECRET_CODE_NOT_MATCH,
    INVALID_SURNAME,
    INVALID_NAME,
    IDENTIFICATION_LINK_WAS_SENT,
    PASSWORD_NOT_MATCH,
    ACCOUNT_IS_BUSY,
    INVALID_ACTIVATION_CODE,
    INCORRECT_ACTIVATION_CODE,
}
