package com.stason.testing.model.entity;

import java.util.LinkedList;
import java.util.List;

public class Question {
    private int id;
    private int testId;
    private int nomerQuestion;
    private String textQuestion;
    private List<Answer> answers = new LinkedList<>();

    public void addAnswer(Answer answer){
        answers.add(answer);
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", testId=" + testId +
                ", nomerQuestion=" + nomerQuestion +
                ", textQuestion='" + textQuestion + '\'' +
                ", answers=\n\t" + answers +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTestId() {
        return testId;
    }

    public void setTestId(int testId) {
        this.testId = testId;
    }

    public int getNomerQuestion() {
        return nomerQuestion;
    }

    public void setNomerQuestion(int nomerQuestion) {
        this.nomerQuestion = nomerQuestion;
    }

    public String getTextQuestion() {
        return textQuestion;
    }

    public void setTextQuestion(String textQuestion) {
        this.textQuestion = textQuestion;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }
}
