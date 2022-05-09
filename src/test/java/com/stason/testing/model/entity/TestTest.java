package com.stason.testing.model.entity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class TestTest {
    static com.stason.testing.model.entity.Test test1 = new com.stason.testing.model.entity.Test();
    static com.stason.testing.model.entity.Test test2 = new com.stason.testing.model.entity.Test();
    static Question question1 = new Question();
    static Question question2 = new Question();

    @BeforeAll
    static void setUp() {
        test1.setName("Test 1");
        test1.setNameOfDiscipline("Discipline 1");
        test1.setId(46);
        test1.setDifficulty(2);
        test1.setTimeMinutes(5);

        test2.setName("Test 1");
        test2.setNameOfDiscipline("Discipline 1");
        test2.setId(46);
        test2.setDifficulty(2);
        test2.setTimeMinutes(5);

        question1.setId(10);
        question1.setTestId(46);
        question1.setTextQuestion("question 1");
        question2.setId(11);
        question2.setTestId(46);
        question2.setTextQuestion("question 2");

        test1.setQuestions(new ArrayList<>(Arrays.asList(question1,question2)));
        test2.setQuestions(new ArrayList<>(Arrays.asList(question1,question2)));

    }



    @Test
    void getQuestionById() {
        setUp();

        test1.setQuestions(new ArrayList<>(Arrays.asList(question1,question2)));
        test2.setQuestions(new ArrayList<>(Arrays.asList(question1,question2)));

        assertEquals(question1, test1.getQuestionById(10));
        System.out.println(question2);
        System.out.println(test1.getQuestionById(11));
        assertEquals(question2, test1.getQuestionById(11));
        assertNotEquals(question1, test1.getQuestionById(11));
    }

    @Test
    void setQuestionById() throws CloneNotSupportedException {
        Question newQuestion = question1.clone();
        newQuestion.setId(50);

        test2.setQuestionById(newQuestion,11);
        assertNotEquals(test1, test2);
    }

    @Test
    void setQuestionByIdException() throws CloneNotSupportedException {
        Question newQuestion = question1.clone();
        newQuestion.setId(50);
        Throwable thrown = assertThrows(RuntimeException.class, () -> test2.setQuestionById(newQuestion,11000) );
        assertNotNull(thrown.getMessage());

    }

    @Test
    void deleteLastQuestion() {
        setUp();
        test1.deleteLastQuestion();
        test2.deleteLastQuestion();
        assertEquals(test1,test2);
    }
    @Test
    void deleteQuestion() {
        setUp();
        test1.deleteQuestion(10);
        test2.deleteQuestion(10);
        assertEquals(test1,test2);
    }
    @Test
    void setTimeMinutes() {
        test1.setTimeMinutes(10);
        assertEquals(10, test1.getTimeMinutes());
        assertEquals(600, test1.getTimeSeconds());
    }
}