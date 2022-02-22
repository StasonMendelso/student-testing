package com.stason.testing.model.dao;

import com.stason.testing.model.entity.User;

public interface UserDao extends GenericDao<User> {
    boolean checkUser(User user);
    boolean checkLogin(User user);
    User findByLogin(String login);
    boolean block(int id);
    boolean unblock(int id);
}
