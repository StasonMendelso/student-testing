package com.stason.testing.controller.services;

import com.stason.testing.model.dao.AnswerDao;
import com.stason.testing.model.dao.DaoFactory;
import com.stason.testing.model.entity.Answer;

import java.util.List;

public class AnswerService {
    private final DaoFactory factory = DaoFactory.getInstance();
    private final AnswerDao answerDao = factory.createAnswerDao();
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
