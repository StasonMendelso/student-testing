package com.stason.testing.controller.services;

import com.stason.testing.model.dao.AnswerDao;

import com.stason.testing.model.dao.implement.JDBCAnswerDao;
import com.stason.testing.model.entity.Answer;

import java.util.List;
/**
 * It is a answer-service class
 * @author Stanislav Hlova
 * @version 1.0
 */
public class AnswerService {

    private final AnswerDao answerDao = new JDBCAnswerDao();

    public List<Answer> findAllByQuestionId(int questionId){
        return answerDao.findAllByQuestionId(questionId);
    }

    public boolean create(Answer answer) {
        return answerDao.create(answer);
    }

    public boolean delete(int id) {
        return answerDao.delete(id);
    }
}
