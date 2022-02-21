package com.stason.testing.model.dao;

import com.stason.testing.model.entity.Test;

public interface TestDao extends GenericDao<Test>{
    public int findIdByName(String testName);
}
