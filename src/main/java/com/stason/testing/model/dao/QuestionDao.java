package com.stason.testing.model.dao;

import com.stason.testing.model.entity.Question;

public interface QuestionDao extends GenericDao<Question>{
    public int findId(Question question);
}
