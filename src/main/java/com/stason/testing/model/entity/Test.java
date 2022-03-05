package com.stason.testing.model.entity;



import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Test {
    private int id;
    private String name;
    private String nameOfDiscipline;
    private String difficulty;
    private int timeMinutes;
    private int countOfQuestions;
    private List<Question> questions = new LinkedList<>();

    public void addQuestion(Question question){
        questions.add(question);
        this.setCountOfQuestions(questions.size());
    }
    @Override
    public String toString() {
        return "Test{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", nameOfDiscipline='" + nameOfDiscipline + '\'' +
                ", difficulty=" + difficulty +
                ", timeMinutes=" + timeMinutes +
                ", countOfQuestions=" + countOfQuestions +
                ", questions=" + questions +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameOfDiscipline() {
        return nameOfDiscipline;
    }

    public void setNameOfDiscipline(String nameOfDiscipline) {
        this.nameOfDiscipline = nameOfDiscipline;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public int getTimeMinutes() {
        return timeMinutes;
    }

    public void setTimeMinutes(int timeSeconds) {
        this.timeMinutes = timeSeconds;
    }

    public int getCountOfQuestions() {
        return countOfQuestions;
    }

    public void setCountOfQuestions(int countOfQuestions) {
        this.countOfQuestions = countOfQuestions;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void deleteQuestion(int id){
        int i =0;
        for(Question question:questions){
            if(question.getId()==id){
                questions.remove(i);
                countOfQuestions--;
            }
            i++;
        }

    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
    public Question getQuestionById(int id){
        for(Question question:questions){
            if(question.getId()==id) return question;
        }
        return null;
    }
    public void setQuestionById(Question newQuestion,int id){
        Iterator<Question> iterator = questions.iterator();
        int i=0;
        while(iterator.hasNext()){
            Question question = iterator.next();
            if(question.getId()==id){
                questions.remove(i);
                questions.add(i,newQuestion);
                break;
            }
            i++;
        }

    }
}
