package com.stason.testing.model.dao;

import java.util.List;

public interface GenericDao<T> extends  AutoCloseable{
    boolean create (T entity);
    T findById(int id);
    List<T> findAll();
    void update(T entity);
    void delete(int id);
}
