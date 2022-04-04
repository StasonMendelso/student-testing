package com.stason.testing.model.dao;


public interface GenericDao<T> extends  AutoCloseable{
    boolean create (T entity);
    T findById(int id);
    boolean update(T entity);
    boolean delete(int id);
}
