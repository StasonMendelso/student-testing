package com.stason.testing.controller.services;

import com.stason.testing.model.dao.DaoFactory;
import com.stason.testing.model.dao.QuestionDao;
import com.stason.testing.model.entity.Question;

import java.util.List;

public class QuestionService {
    private final DaoFactory factory = DaoFactory.getInstance();
    private final QuestionDao questionDao = factory.createQuestionDao();
    public List<Question> findAllByTestId(int testId){
        return questionDao.findAllByTestId(testId);
    }

    public void create(Question question) {
        questionDao.create(question);
    }

    public int findId(Question question) {
        return questionDao.findId(question);
    }

    public void delete(int questionId) {
        questionDao.delete(questionId);
    }
}
