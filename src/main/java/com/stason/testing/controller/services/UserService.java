package com.stason.testing.controller.services;

import com.stason.testing.model.dao.UserDao;
import com.stason.testing.model.dao.implement.JDBCUserDao;
import com.stason.testing.model.entity.User;

import java.util.List;

public class UserService {

    private final UserDao userDao = new JDBCUserDao();

    public boolean checkLogin(String login){
        return userDao.checkLogin(login);
    }
    public User findByLogin(String login){
        return userDao.findByLogin(login);
    }
    public List<Integer> findIdPassedTestsByUserId(int userId){
        return  userDao.findIdPassedTestsByUserId(userId);
    }
    public boolean createNewUser(User user){
        return userDao.create(user);
    }
    public void updatePassword(String login, String hashedPassword,String salt){
        userDao.updatePassword(login,hashedPassword,salt);
    }

    public void block(int userId) {
        userDao.block(userId);
    }

    public void delete(int userId) {
        userDao.delete(userId);
    }

    public User findById(int id) {
        return userDao.findById(id);
    }

    public void update(User user) {
        userDao.update(user);
    }

    public void unblock(int id) {
        userDao.unblock(id);
    }

    public List<Integer> findIdBlockedUsers() {
        return  userDao.findIdBlockedUsers();
    }
}
