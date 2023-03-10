package com.stason.testing.controller.services;


import com.stason.testing.model.dao.TestDao;
import com.stason.testing.model.dao.implement.JDBCTestDao;
import com.stason.testing.model.entity.Answer;
import com.stason.testing.model.entity.Question;
import com.stason.testing.model.entity.Test;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
/**
 * It is a test-service class
 * @author Stanislav Hlova
 * @version 1.0
 */
public class TestService {
    private final TestDao testDao = new JDBCTestDao();

    public void addPassedTest(int userId, int testId, double mark) {
        testDao.addPassedTest(userId, testId, mark);
    }

    public Test findById(int testId) {
        return testDao.findById(testId);
    }

    public void updatePassedTest(int userId, int testId, double mark) {
        testDao.updatePassedTest(userId, testId, mark);
    }

    public List<String> findAllDisciplines() {
        return testDao.findAllDisciplines();
    }

    public void create(Test test) {
        testDao.create(test);
    }

    public void deletePassedTestByUser(int testId, int userId) {
        testDao.deletePassedTestForUser(testId,userId);
    }

    public void delete(int id) {
        testDao.delete(id);
    }

    public void update(Test test) {
        testDao.update(test);
    }

    public void deletePassedTestForUser(int testId, int userId) {
        testDao.deletePassedTestForUser(testId, userId);
    }

    /**
     * Finds test with question and answers
     * @param testId a test's id
     * @return return a test with correct answers
     */
    public Test findTestWithQuestionsAndAnswers(int testId) {
        Test test = testDao.findById(testId);
        QuestionService questionService = new QuestionService();
        AnswerService answerService = new AnswerService();

        List<Question> questionList = questionService.findAllByTestId(testId);
        Iterator<Question> iterator = questionList.iterator();
        while (iterator.hasNext()) {
            Question question = iterator.next();
            List<Answer> answerList = answerService.findAllByQuestionId(question.getId());
            List<Boolean> userOptions = new LinkedList<>();
            for (int i = 1; i <= answerList.size(); i++) {
                userOptions.add(Boolean.FALSE);
            }
            question.setUserOptions(userOptions);
            question.setAnswers(answerList);
        }
        test.setQuestions(questionList);
        return test;
    }

    public boolean checkTestName(String testName) {
        return testDao.findIdByName(testName) != 0;
    }
}
