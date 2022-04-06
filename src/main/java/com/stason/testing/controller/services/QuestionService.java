package com.stason.testing.controller.services;


import com.stason.testing.model.dao.QuestionDao;
import com.stason.testing.model.dao.implement.JDBCQuestionDao;
import com.stason.testing.model.entity.Question;

import java.util.List;

public class QuestionService {
    private final QuestionDao questionDao = new JDBCQuestionDao();

    public List<Question> findAllByTestId(int testId) {
        return questionDao.findAllByTestId(testId);
    }

    public void create(Question question) {
        questionDao.create(question);
    }

    public void delete(int questionId) {
        questionDao.delete(questionId);
    }
}
