package com.stason.testing.model.dao;

import com.stason.testing.model.entity.User;

public interface UserDao extends GenericDao<User> {
    public boolean checkUser(User user);
    public boolean checkLogin(User user);
    public User findByLogin(String login);
}
