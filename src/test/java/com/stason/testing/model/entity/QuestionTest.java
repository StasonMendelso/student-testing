package com.stason.testing.model.entity;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class QuestionTest {

    static Question question = new Question();
    static Answer answer1 = new Answer();
    static Answer answer2 = new Answer();
    @BeforeAll
    static void setUp(){

        answer1.setId(11);
        answer1.setAnswer("Answer 1");
        answer1.setQuestionId(10);
        answer1.setRightAnswer(true);

        answer2.setId(12);
        answer2.setAnswer("Answer 2");
        answer2.setQuestionId(10);
        answer2.setRightAnswer(false);

        List<Answer> answerList = new ArrayList<>(Arrays.asList(answer1,answer2));
        question.setId(10);
        question.setQuestionNumber(1);
        question.setTextQuestion("question 1");
        question.setAnswers(answerList);
    }
    @Test
    void testClone() throws CloneNotSupportedException {
        Question question1 = question.clone();
        System.out.println(question);
        assertEquals(question,question1);
    }
    @Test
    void testDeleteAnswerById() throws CloneNotSupportedException {
        question.deleteAnswerById(11);
        Question question2 = new Question();
        question2 = question.clone();
        question2.setAnswers(Collections.singletonList(answer2));
        assertEquals(question,question2);
    }
}