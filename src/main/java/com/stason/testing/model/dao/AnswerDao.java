package com.stason.testing.model.dao;

import com.stason.testing.model.entity.Answer;

import java.util.List;

public interface AnswerDao extends GenericDao<Answer>{
    List<Answer> findAllByQuestionId(int id);
}
