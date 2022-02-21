package com.stason.testing.controller.services;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class ValidatorService {
    public static boolean validateEmail(String email){
        String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(regexPattern);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    public static boolean validateUsername(String username){
        String regexPattern = "^[A-ZА-ЯЁ][a-zа-яіїёъ']{1,29}$";
        Pattern pattern = Pattern.compile(regexPattern);
        Matcher matcher = pattern.matcher(username);
        return matcher.matches();
    }
    public static boolean validateSurname(String surname){
        String regexPattern = "^[A-ZА-ЯЁ][a-zа-яіїёъ']{1,29}$";
        Pattern pattern = Pattern.compile(regexPattern);
        Matcher matcher = pattern.matcher(surname);
        return matcher.matches();
    }



    /**
     * ^ represents starting character of the string.
     * (?=.*[0-9]) represents a digit must occur at least once.
     * (?=.*[a-z]) represents a lower case alphabet must occur at least once.
     * (?=.*[A-Z]) represents an upper case alphabet that must occur at least once.
     * (?=.*[@#$%^&-+=()] represents a special character that must occur at least once.
     * (?=\\S+$) white spaces don’t allowed in the entire string.
     * .{8, 20} represents at least 8 characters and at most 20 characters.
     * $ represents the end of the string.
     * **/
    public static boolean validatePassword(String password){
        String regexPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!\\-+=%*?&])[A-Za-z\\d@\\-$!%*?=+&]{8,20}$";
        Pattern pattern = Pattern.compile(regexPattern);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }
    public static boolean isPasswordRepeated(String password1,String password2){
        return password1.equals(password2);
    }
}
