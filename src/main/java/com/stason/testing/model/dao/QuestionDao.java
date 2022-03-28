package com.stason.testing.model.dao;

import com.stason.testing.model.entity.Question;

import java.util.List;

public interface QuestionDao extends GenericDao<Question>{
    List<Question> findAllByTestId(int id);
    int findId(Question question);
}
