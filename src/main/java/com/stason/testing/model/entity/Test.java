package com.stason.testing.model.entity;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
/**
 * It is a class-model from database table TEST
 * @author Stanislav Hlova
 * @version 1.0
 */
public class Test implements Serializable {
    private int id;
    private String name;
    private String nameOfDiscipline;
    private int difficulty;
    private int timeMinutes;
    private int timeSeconds;
    private int countOfQuestions;
    private double mark;
    private List<Question> questions = new ArrayList<>();

    public Test() {
    }

    public Test(String name, String nameOfDiscipline, int difficulty, int timeMinutes) {
        this.name = name;
        this.nameOfDiscipline = nameOfDiscipline;
        this.difficulty = difficulty;
        this.timeMinutes = timeMinutes;
        this.timeSeconds = timeMinutes * 60;
    }

    public Test(int id, String name, String nameOfDiscipline, int difficulty, int timeMinutes, int countOfQuestions) {
        this(name,nameOfDiscipline,difficulty,timeMinutes);
        this.id = id;
        this.countOfQuestions = countOfQuestions;
    }

    public Question getQuestion(int questionNumber) {
        for (Question question : questions) {
            if (question.getQuestionNumber() == questionNumber) return question;
        }
        return null;
    }

    /**
     * Sets replace old question on new one
     * @param question a new Question
     * @param questionNumber a question number
     * @see Question
     */
    public void setQuestion(Question question, int questionNumber) {
        Iterator<Question> iterator = questions.iterator();
        while (iterator.hasNext()) {
            Question oldQuestion = iterator.next();
            if (oldQuestion.getQuestionNumber() == questionNumber) {
                oldQuestion = question;
                return;
            }
        }
    }

    public void addQuestion(Question question) {
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

    public int getTimeSeconds() {
        return timeSeconds;
    }

    public void setTimeSeconds(int timeSeconds) {
        this.timeSeconds = timeSeconds;
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

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public int getTimeMinutes() {
        return timeMinutes;
    }

    public void setTimeMinutes(int timeMinutes) {
        this.timeMinutes = timeMinutes;
        this.timeSeconds = timeMinutes * 60;
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

    public double getMark() {
        return mark;
    }

    public void setMark(double mark) {
        this.mark = mark;
    }

    public void deleteQuestion(int id) {

        boolean flag = false;

        for (int i = 0; i < questions.size(); i++) {
            if (flag) {
                Question question = questions.get(i);
                question.setQuestionNumber(i + 1);
                questions.set(i, question);

            }
            if (questions.get(i).getId() == id) {
                questions.remove(i);
                countOfQuestions--;
                flag = true;
                i--;
            }
        }
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
        this.countOfQuestions = questions.size();
    }

    public Question getQuestionById(int id) {
        for (Question question : questions) {
            if (question.getId() == id) return question;
        }
        return null;
    }

    /**
     * Replaces old question on new by question's id
     * @param newQuestion a new Question
     * @param id a old question's id
     * @see Question
     */
    public void setQuestionById(Question newQuestion, int id) {
        Iterator<Question> iterator = questions.iterator();
        int i = 0;
        while (iterator.hasNext()) {
            Question question = iterator.next();
            if (question.getId() == id) {
                questions.remove(i);
                questions.add(i, newQuestion);
                return;
            }
            i++;
        }
        throw new RuntimeException("There is not question with id=" + id);

    }

    public void deleteLastQuestion() {
        questions.remove(questions.size() - 1);
        countOfQuestions--;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Test test = (Test) o;
        return id == test.id && difficulty == test.difficulty && timeMinutes == test.timeMinutes && timeSeconds == test.timeSeconds && countOfQuestions == test.countOfQuestions && Double.compare(test.mark, mark) == 0 && name.equals(test.name) && Objects.equals(nameOfDiscipline, test.nameOfDiscipline) && Objects.equals(questions, test.questions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, nameOfDiscipline, difficulty, timeMinutes, timeSeconds, countOfQuestions, mark, questions);
    }
}
