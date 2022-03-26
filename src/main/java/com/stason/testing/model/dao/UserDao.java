package com.stason.testing.model.dao;

import com.stason.testing.model.entity.User;

import java.util.List;

public interface UserDao extends GenericDao<User> {
    boolean checkUser(User user);
    boolean checkLogin(User user);
    User findByLogin(String login);
    boolean block(int id);
    boolean unblock(int id);

    List<User> findAndPaginateAllUsers(int index, int paginationParameter);

    int countAllUsers();

    List<Integer> findIdPassedTestsByUserId(int id);

    List<Integer> findIdBlockedUsers();
    void deletePassedTestsByUserId(int id);
    void updatePassword(String login,String password, String salt);
}
