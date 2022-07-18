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
        assertTrue(ValidatorService.validateSurname("Username"));
        assertTrue(ValidatorService.validateSurname("Nick"));
        assertTrue(ValidatorService.validateSurname("Ник"));
        assertTrue(ValidatorService.validateSurname("Вася"));
        assertTrue(ValidatorService.validateSurname("Ірина"));
        assertTrue(ValidatorService.validateSurname("Таїсія"));
        assertTrue(ValidatorService.validateSurname("Пётр"));

        assertFalse(ValidatorService.validateSurname("1username"));
        assertFalse(ValidatorService.validateSurname(".ssdfsd"));
        assertFalse(ValidatorService.validateSurname("uSERNAME"));
        assertFalse(ValidatorService.validateSurname("iVann123"));
        assertFalse(ValidatorService.validateSurname("sdfdFSFE"));
        assertFalse(ValidatorService.validateSurname("$#@14"));
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


    @Test
    void validateActivationCode() {
        assertTrue(ValidatorService.validateActivationCode("12345678"));
        assertTrue(ValidatorService.validateActivationCode("32443241"));
        assertFalse(ValidatorService.validateActivationCode("3244"));
        assertFalse(ValidatorService.validateActivationCode("3244"));
        assertFalse(ValidatorService.validateActivationCode("sdfsd"));
        assertFalse(ValidatorService.validateActivationCode(" "));
        assertFalse(ValidatorService.validateActivationCode(""));
    }

    @Test
    void validateTestName() {
        assertTrue(ValidatorService.validateTestName("Test 1"));
        assertTrue(ValidatorService.validateTestName("Тест 1"));
        assertTrue(ValidatorService.validateTestName("Biological test №1"));
        assertTrue(ValidatorService.validateTestName("New test"));
        assertTrue(ValidatorService.validateTestName("Гриби"));
        assertTrue(ValidatorService.validateTestName("Їжаки"));
        assertTrue(ValidatorService.validateTestName("Євристика"));
        assertTrue(ValidatorService.validateTestName("DORf"));

        assertFalse(ValidatorService.validateTestName("1Test"));
        assertFalse(ValidatorService.validateTestName(" DSF"));
        assertFalse(ValidatorService.validateTestName("        "));
        assertFalse(ValidatorService.validateTestName("asdasd"));
        assertFalse(ValidatorService.validateTestName("авпукпу"));
    }

    @Test
    void validateTestDisciplineName() {
        assertTrue(ValidatorService.validateTestDisciplineName("Алгоритмізація програмування"));
        assertTrue(ValidatorService.validateTestDisciplineName("Радіоелектричні прилади"));
        assertTrue(ValidatorService.validateTestDisciplineName("Система керування БПЛ"));
        assertTrue(ValidatorService.validateTestDisciplineName("Біологія"));
        assertTrue(ValidatorService.validateTestDisciplineName("Фізика"));

        assertFalse(ValidatorService.validateTestDisciplineName(""));
        assertFalse(ValidatorService.validateTestDisciplineName("12Discipline"));
        assertFalse(ValidatorService.validateTestDisciplineName("discipline"));
        assertFalse(ValidatorService.validateTestDisciplineName("#%"));
        assertFalse(ValidatorService.validateTestDisciplineName("!"));
        assertFalse(ValidatorService.validateTestDisciplineName("?????"));

    }

    @Test
    void validateTestDifficulty() {
        assertTrue(ValidatorService.validateTestDifficulty(1));
        assertTrue(ValidatorService.validateTestDifficulty(2));
        assertTrue(ValidatorService.validateTestDifficulty(3));
        assertFalse(ValidatorService.validateTestDifficulty(-1));
        assertFalse(ValidatorService.validateTestDifficulty(0));
        assertFalse(ValidatorService.validateTestDifficulty(Integer.MAX_VALUE));
        assertFalse(ValidatorService.validateTestDifficulty(Integer.MIN_VALUE));
    }

    @Test
    void validateTestTime() {
        assertTrue(ValidatorService.validateTestTime(1));
        assertTrue(ValidatorService.validateTestTime(10));
        assertTrue(ValidatorService.validateTestTime(100));
        assertTrue(ValidatorService.validateTestTime(1000));
        assertTrue(ValidatorService.validateTestTime(10000));
        assertTrue(ValidatorService.validateTestTime(Integer.MAX_VALUE));
        assertFalse(ValidatorService.validateTestTime(0));
        assertFalse(ValidatorService.validateTestTime(-1));
        assertFalse(ValidatorService.validateTestTime(-20));
        assertFalse(ValidatorService.validateTestTime(Integer.MIN_VALUE));
    }

    @Test
    void validateQuestionText() {
        assertTrue(ValidatorService.validateQuestionText("У вас может возникнуть необходимость обозначить набор, в который входят буквы,"));
        assertTrue(ValidatorService.validateQuestionText("Обратите внимание, что квантификатор применяется только к символу, который стоит перед ним."));
        assertTrue(ValidatorService.validateQuestionText("Иногда для увеличения скорости поиска (особенно в тех случаях, когда строка не соответствует регулярному выражению)"));
        assertTrue(ValidatorService.validateQuestionText("Задача 5.8. Однорідну балку масою m = 500 кг і довжиною l = 5 м підвішено горизонтально на двох паралельних тросах так, що один розташовано скраю, а інший – на відстані a = 1 м від протилежного кінця балки  Визначити сили натягу тросів T1 і T2."));
        assertTrue(ValidatorService.validateQuestionText("Вивчаючи умови та розв'язуючи задачі кінематики, слід пам'ятати, що в текстах за умовчанням прийнято такі умови:"));
        assertTrue(ValidatorService.validateQuestionText("Study the grammar lessons that are typically included in each level: A1, A2, B1, B1+, B2. There are three or more exercises and an explanation in each lesson. And you will find feedback for every question!"));
        assertTrue(ValidatorService.validateQuestionText("Improve your listening skills by practicing with audio and video tests. There are tests for each level"));

        assertFalse(ValidatorService.validateQuestionText("    "));
        assertFalse(ValidatorService.validateQuestionText(""));
        assertFalse(ValidatorService.validateQuestionText("asdsa"));
        assertFalse(ValidatorService.validateQuestionText("фівфі"));
        assertFalse(ValidatorService.validateQuestionText("№іфвЖ:;"));
    }

    @Test
    void ValidateAnswerText() {
        assertTrue(ValidatorService.validateAnswerText("У вас может возникнуть необходимость обозначить набор, в который входят буквы,"));
        assertTrue(ValidatorService.validateAnswerText("Обратите внимание, что квантификатор применяется только к символу, "));
        assertTrue(ValidatorService.validateAnswerText("Иногда для увеличения скорости поиска (особенно в тех случаях,"));
        assertTrue(ValidatorService.validateAnswerText("Задача 5.8. Однорідну балку масою m = 500 кг і довжиною l = 5 м  – на відстані a = 1 м вT1 і T2."));
        assertTrue(ValidatorService.validateAnswerText("Вивчаючи умови та розв'язуючи задачі кінематики, слід пам'ятати, що в текстах за умовчанням прийнято такі умови:"));
        assertTrue(ValidatorService.validateAnswerText("Study the grammar lessons that are typically included in each level: A1, A2, B1, B1+, B2. "));
        assertTrue(ValidatorService.validateAnswerText("Improve your listening skills by practicing with audio and video tests. There are tests for each level"));

        assertFalse(ValidatorService.validateAnswerText("    "));
        assertFalse(ValidatorService.validateAnswerText(""));
        assertFalse(ValidatorService.validateAnswerText("asdsa"));
        assertFalse(ValidatorService.validateAnswerText("фівфі"));
        assertFalse(ValidatorService.validateAnswerText("№іфвЖ:;"));
    }
}