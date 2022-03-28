package com.stason.testing.controller.services;

import com.stason.testing.model.dao.AnswerDao;

import com.stason.testing.model.dao.implement.JDBCAnswerDao;
import com.stason.testing.model.entity.Answer;

import java.util.List;

public class AnswerService {

    private final AnswerDao answerDao = new JDBCAnswerDao();

    public List<Answer> findAllByQuestionId(int questionId){
        return answerDao.findAllByQuestionId(questionId);
    }

    public void create(Answer answer) {
        answerDao.create(answer);
    }

    public void delete(int id) {
        answerDao.delete(id);
    }
}
