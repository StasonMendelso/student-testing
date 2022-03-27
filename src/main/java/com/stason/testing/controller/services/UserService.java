package com.stason.testing.controller.services;

import com.stason.testing.model.dao.DaoFactory;
import com.stason.testing.model.dao.UserDao;
import com.stason.testing.model.entity.User;

import java.util.List;

public class UserService {
    private final DaoFactory factory = DaoFactory.getInstance();
    private final UserDao userDao = factory.createUserDao();
    public boolean checkLogin(User user){
        return userDao.checkLogin(user);
    }
    public User findByLogin(String login){
        return userDao.findByLogin(login);
    }
    public List<Integer> findIdPassedTestsByUserId(int userId){
        return  userDao.findIdPassedTestsByUserId(userId);
    }
    public boolean checkUser(User user){
        return userDao.checkUser(user);
    }
    public void createNewUser(User user){
        userDao.create(user);
    }
    public void updatePassword(String login, String hashedPassword,String salt){
        userDao.updatePassword(login,hashedPassword,salt);
    }

    public void block(int userId) {
        userDao.block(userId);
    }

    public void deletePassedTestsByUserId(int userId) {
        userDao.deletePassedTestsByUserId(userId);
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
}
