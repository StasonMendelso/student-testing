package com.stason.testing.model.dao;

import com.stason.testing.model.entity.Question;

import java.util.List;

/**
 * Interface for interaction in the database with the Questions.
 * @author Stanislav Hlova
 * @version 1.0
 */
public interface QuestionDao extends GenericDao<Question>{
    /**
     * Finds all questions in the database which are contained in test
     * @param id a test's id
     * @return return a List of questions for this test
     */
    List<Question> findAllByTestId(int id);
}
