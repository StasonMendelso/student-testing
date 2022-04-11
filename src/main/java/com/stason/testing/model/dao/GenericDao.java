package com.stason.testing.model.dao;

/**
 * Interface for realization CRUD-operations
 * @author Stanislav Hlova
 * @version 1.0
 * */
public interface GenericDao<T> extends  AutoCloseable{
    /**
     * Create operation
     * @param entity  an object 
     * @return If the object created return true,else - false
     */
    boolean create (T entity);
    /**
     * Read operation
     * @param id  an identification of object  in database
     * @return return an object  from database
     */
    T findById(int id);

    /**
     * Update operation
     * @param entity an object
     * @return If the object updated return true,else - false
     */
    boolean update(T entity);

    /**
     * Delete operation
     * @param id an identification of object in database
     * @return If the object deleted return true,else - false
     */
    boolean delete(int id);
}
