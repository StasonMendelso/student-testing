package com.stason.testing.controller.services;
/**
 * The class is for validation using Regex and if statements
 * @author Stanislav Hlova
 * @version 1.0
 */
public abstract class ValidatorService {
    private static final String emailPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
    private static final String usernamePattern = "^[A-ZА-ЯІЁЄ][a-zа-яіїёъє']{1,29}$";
    private static final String surnamePattern = "^[A-ZА-ЯІЁЄ][a-zа-яіїёъє']{1,29}$";
    /**
     * ^ represents starting character of the string.
     * (?=.*[0-9]) represents a digit must occur at least once.
     * (?=.*[a-z]) represents a lower case alphabet must occur at least once.
     * (?=.*[A-Z]) represents an upper case alphabet that must occur at least once.
     * (?=.*[@#$%^&-+=()] represents a special character that must occur at least once.
     * (?=\\S+$) white spaces don’t allowed in the entire string.
     * .{8, 20} represents at least 8 characters and at most 20 characters.
     * $ represents the end of the string.
     **/
    private static final String passwordPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!\\-+=%*?&])[A-Za-z\\d@\\-$!%*?=+&]{8,20}$";
    private static final String activationCodePattern = "^[0-9]{8}$";
    private static final String testNamePattern = "^[A-ZА-ЯІЇЄ][A-zА-яїіІЇЄє0-9.,;:?!\\-+=–/*\"'|&<>\\[\\]@№%^() {}]{1,99}";
    private static final String testDisciplineNamePattern = "^[A-ZА-ЯІЇЄ][A-zА-яїіІЇЄє0-9.,;:?!\\-+=–/*\"'|&<>\\[\\]@№%^() {}]{1,99}";
    private static final String questionTextPattern = "^[A-ZА-ЯІЇЄ0-9.-][A-zА-яїіІЇЄє0-9.,;:?!\\-+=–/*\"'|&<>\\[\\]@№%^() {}]{0,399}";
    private static final String answerTextPattern = "^[A-ZА-ЯІЇЄ0-9.-][A-zА-яїіІЇЄє0-9.,;:?!\\-+=–/*\"'|&<>\\[\\]@№%^() {}]{0,299}";

    private ValidatorService() {
    }

    public static boolean validateEmail(String email) {
        return email.matches(emailPattern);
    }

    public static boolean validateUsername(String username) {
        return username.matches(usernamePattern);
    }

    public static boolean validateSurname(String surname) {
        return surname.matches(surnamePattern);
    }

    public static boolean validatePassword(String password) {
        return password.matches(passwordPattern);
    }

    public static boolean isPasswordRepeated(String password1, String password2) {
        return password1.equals(password2);
    }

    public static boolean validateActivationCode(String activationCodeFromUser) {
        return activationCodeFromUser.matches(activationCodePattern);
    }

    //For tests
    public static boolean validateTestName(String testName) {
        return testName.matches(testNamePattern);
    }

    public static boolean validateTestDisciplineName(String testName) {
        return testName.matches(testDisciplineNamePattern);
    }

    public static boolean validateTestDifficulty(int difficulty) {
        return difficulty > 0 && difficulty < 4;
    }

    public static boolean validateTestTime(int minutes) {
        return minutes > 0;
    }

    public static boolean validateQuestionText(String question) {
        return question.matches(questionTextPattern);
    }

    public static boolean validateAnswerText(String answer) {
        return answer.matches(answerTextPattern);
    }
}
