package com.stason.testing.model.dao;

import com.stason.testing.model.entity.Answer;

import java.util.List;

/**
 * Interface for interaction in the database with the Answer.
 * @author Stanislav Hlova
 * @version 1.0
 */
public interface AnswerDao extends GenericDao<Answer>{
    /**
     * Finds all answers in the database which are contained in question
     * @param id a question's id
     * @return return a List of answers for this question
     */
    List<Answer> findAllByQuestionId(int id);
}
