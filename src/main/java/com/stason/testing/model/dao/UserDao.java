package com.stason.testing.model.dao;

import com.stason.testing.model.entity.User;

import java.util.List;

/**
 * Interface for interaction in the database with the User.
 * @author Stanislav Hlova
 * @version 1.0
 */
public interface UserDao extends GenericDao<User> {
    /**
     * Finds a user by his login.
     * @param login a user's login.
     * @return if a user is exists in database, then return a User object, else - null.
     */
    User findByLogin(String login);

    /**
     * Blocks a user in database.
     * @param id a user's id in database.
     * @return if a user was blocked, then return true, else - false.
     */
    boolean block(int id);
    /**
     * Unblocks a user in database.
     * @param id a user's id in database.
     * @return if a user was unblocked, then return true, else - false.
     */
    boolean unblock(int id);

    /**
     *Finds users and paginates them
     * @param index this number indicates which element you need to start reading records in the database.
     * @param paginationParameter this number indicates how many records will be read from the database.
     * @return return a List of User for pagination.
     */
    List<User> findAndPaginateAllUsers(int index, int paginationParameter);

    /**
     * Counts how many users are in the database .
     * @return a number of users in the database.
     */
    int countAllUsers();

    /**
     * Finds all id of passed tests by user.
     * @param id a user id in the database.
     * @return return a List of id of passed tests by user id.
     */
    List<Integer> findIdPassedTestsByUserId(int id);

    /**
     * Finds all id of blocked users
     * @return return a List of id of blocked users in the database
     */
    List<Integer> findIdBlockedUsers();

    /**
     * Updates user's password
     * @param login a user's login
     * @param password a new user's password
     * @param salt a salt for user
     * @return if a password was updated, the return true, else - false
     */
    boolean updatePassword(String login, String password, String salt);
}
