package com.stason.testing.model.entity;

import java.util.Objects;

public class Answer{
    private int id;
    private String answer;
    private boolean isRightAnswer;
    private int questionId;

    public Answer() {
    }

    public Answer(int id, String answer, boolean isRightAnswer, int questionId) {
        this.id = id;
        this.answer = answer;
        this.isRightAnswer = isRightAnswer;
        this.questionId = questionId;
    }

    public Answer(String answer, boolean isRightAnswer, int questionId) {
        this.answer = answer;
        this.isRightAnswer = isRightAnswer;
        this.questionId = questionId;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "id=" + id +
                ", answer='" + answer + '\'' +
                ", isRightAnswer=" + isRightAnswer +
                ", questionId=" + questionId +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public boolean isRightAnswer() {
        return isRightAnswer;
    }

    public void setRightAnswer(boolean rightAnswer) {
        isRightAnswer = rightAnswer;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Answer answer1 = (Answer) o;
        return id == answer1.id && isRightAnswer == answer1.isRightAnswer && questionId == answer1.questionId && answer.equals(answer1.answer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, answer, isRightAnswer, questionId);
    }
}
