package com.stason.testing.controller.services;

import com.stason.testing.model.dao.UserDao;
import com.stason.testing.model.dao.implement.JDBCUserDao;
import com.stason.testing.model.entity.User;

import java.util.List;
/**
 * It is a user-service class
 * @author Stanislav Hlova
 * @version 1.0
 */
public class UserService {
    private final UserDao userDao = new JDBCUserDao();

    public boolean checkLogin(String login) {
        return userDao.findByLogin(login) != null;
    }

    public User findByLogin(String login) {
        return userDao.findByLogin(login);
    }

    public List<Integer> findIdPassedTestsByUserId(int userId) {
        return userDao.findIdPassedTestsByUserId(userId);
    }

    public boolean createNewUser(User user) {
        return userDao.create(user);
    }

    public void updatePassword(String login, String password) {
        String salt = EncryptionPassword.generateSalt();
        userDao.updatePassword(login, EncryptionPassword.hash(password, salt), salt);
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
        return userDao.findIdBlockedUsers();
    }
}
