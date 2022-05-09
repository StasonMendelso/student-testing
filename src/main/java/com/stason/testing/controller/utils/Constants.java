package com.stason.testing.controller.utils;
/**
 * It is a class with constants
 * @author Stanislav Hlova
 * @version 1.0
 */
public class Constants {
    private Constants() {
    }

    //reCaptcha
    public static final String SITE_KEY = "6Le_IQ8fAAAAABiknw7zw-Gtx5op-lygF1rL5v8N";
    public static final String SECRET_KEY = "6Le_IQ8fAAAAAF7HezTZXH56DvTNmVAWGOHnG8D9";
    public static final String ACTIVATION_LINK = "http://localhost:8080/web-application/testing/role/changePassword";
    //Passwords
    //For user's actions
    public static final String PASSWORD_DELETE_USER = "delete";
    public static final String PASSWORD_BLOCK_USER = "block";
    public static final String PASSWORD_UNBLOCK_USER = "unblock";
    public static final String PASSWORD_SAVE_CHANGES_FOR_USER = "save";
    //For test's actions
    public static final String PASSWORD_DELETE_TEST = "delete";
    public static final String PASSWORD_SAVE_EDITED_TEST = "save";
}
