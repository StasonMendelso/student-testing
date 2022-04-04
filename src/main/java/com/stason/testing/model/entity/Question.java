package com.stason.testing.model.entity;

import java.util.*;

public class Question implements Cloneable{
    private int id;
    private int testId;
    private int questionNumber;
    private String textQuestion;
    private List<Answer> answers = new LinkedList<>();
    private List<Boolean> userOptions = new LinkedList<>();
    public void addAnswer(Answer answer){
        this.answers.add(answer);
    }

    public Question() {
    }

    public Question(int testId, int questionNumber, String textQuestion) {
        this.testId = testId;
        this.questionNumber = questionNumber;
        this.textQuestion = textQuestion;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", testId=" + testId +
                ", nomerQuestion=" + questionNumber +
                ", textQuestion='" + textQuestion + '\'' +
                ", answers=\n\t" + answers +
                ", userOptions=" + userOptions +
                '}';
    }

    public List<Boolean> getUserOptions() {
        return userOptions;
    }

    public void setUserOptions(List<Boolean> userOptions) {
        this.userOptions = userOptions;
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

    public int getQuestionNumber() {
        return questionNumber;
    }

    public void setQuestionNumber(int questionNumber) {
        this.questionNumber = questionNumber;
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
    public Answer getLastAnswer(){
        return answers.get(answers.size()-1);
    }
    public void deleteAnswerById(int id){
        Iterator<Answer> iterator = answers.iterator();
        while(iterator.hasNext()){
            Answer answer =iterator.next();
            if(answer.getId()==id){
                iterator.remove();
                break;
            }
        }
    }

    @Override
    public Question clone() throws CloneNotSupportedException {
        Question question = new Question();
        question.setQuestionNumber(this.getQuestionNumber());
        question.setId(this.getId());
        question.setTextQuestion(this.textQuestion);
        question.setTestId(this.testId);
        ArrayList<Answer> list = new ArrayList<>();
        for(Answer answer:this.answers){
            list.add(answer);
        }
        question.setAnswers(list);
        return question;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Question question = (Question) o;
        return id == question.id && testId == question.testId && questionNumber == question.questionNumber && textQuestion.equals(question.textQuestion) && Objects.equals(answers, question.answers) && Objects.equals(userOptions, question.userOptions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, testId, questionNumber, textQuestion, answers, userOptions);
    }
}
