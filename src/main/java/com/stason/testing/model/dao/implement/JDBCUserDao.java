package com.stason.testing.model.dao.implement;

import com.stason.testing.model.dao.UserDao;
import com.stason.testing.model.entity.Role;
import com.stason.testing.model.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCUserDao implements UserDao {
    private final Connection connection;
    private String SQLfindAllUser = "SELECT * FROM onlinetesting.users";


    public JDBCUserDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Integer> findIdBlockedUsers() {
        List<Integer> list = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT id FROM onlinetesting.users WHERE blocked=true");
            while(resultSet.next()){
                list.add(resultSet.getInt("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void deletePassedTestByUserId(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM onlinetesting.passedtests WHERE user_id=?");
            preparedStatement.setInt(1,id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<Integer> findIdPassedTestsByUserId(int id) {
        List<Integer> list = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("SELECT onlinetesting.passedtests.test_id FROM onlinetesting.passedtests WHERE user_id=?;");
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                list.add(resultSet.getInt("test_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<User> findAndPaginateAllUsers(int index, int paginationParameter) {
        List<User> list = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM onlinetesting.users limit ?,?");
            preparedStatement.setInt(1,index);
            preparedStatement.setInt(2,paginationParameter);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                User user = new User();
                user.setLogin(resultSet.getString("login"));
                user.setName(resultSet.getString("name"));
                user.setSurname(resultSet.getString("surname"));
                user.setRole(Role.valueOf(resultSet.getString("role")));
                user.setId(resultSet.getInt("id"));
                user.setBlocked(resultSet.getBoolean("blocked"));
                list.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public int countAllUsers() {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(1) FROM onlinetesting.users");
            if(resultSet.next()){
                return resultSet.getInt("COUNT(1)");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public boolean checkUser(User user){
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM onlinetesting.users WHERE login=?;");
            preparedStatement.setString(1, user.getLogin());
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
    public boolean checkLogin(User user){
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM onlinetesting.users WHERE login=? and password=?;");
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public User findByLogin(String login) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM onlinetesting.users WHERE login=?;");
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                User user = new User();
                user.setLogin(resultSet.getString("login"));
                user.setName(resultSet.getString("name"));
                user.setSurname(resultSet.getString("surname"));
                user.setRole(Role.valueOf(resultSet.getString("role")));
                user.setId(resultSet.getInt("id"));
                user.setBlocked(resultSet.getBoolean("blocked"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void create(User user) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("INSERT INTO onlinetesting.users (login,password,name,surname,role,blocked)values(?,?,?,?,?,?);");
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getName());
            preparedStatement.setString(4, user.getSurname());
            preparedStatement.setString(5, user.getRole());
            preparedStatement.setString(6, user.getStringIntBlocked());
            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean block(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE onlinetesting.users set blocked=1 WHERE id=?");
            preparedStatement.setInt(1,id);
            return preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    @Override
    public boolean unblock(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE onlinetesting.users set blocked=0 WHERE id=?");
            preparedStatement.setInt(1,id);
            return preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    @Override
    public User findById(int id) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM onlinetesting.users WHERE id=?");
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                User user = new User();
                user.setLogin(resultSet.getString("login"));
                user.setName(resultSet.getString("name"));
                user.setSurname(resultSet.getString("surname"));
                user.setRole(Role.valueOf(resultSet.getString("role")));
                user.setId(resultSet.getInt("id"));
                user.setBlocked(resultSet.getBoolean("blocked"));
                return user;

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<User> findAll() {
        List<User> list = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQLfindAllUser);
            while(resultSet.next()){
                User user = new User();
                user.setLogin(resultSet.getString("login"));
                user.setName(resultSet.getString("name"));
                user.setSurname(resultSet.getString("surname"));
                user.setRole(Role.valueOf(resultSet.getString("role")));
                user.setId(resultSet.getInt("id"));
                user.setBlocked(resultSet.getBoolean("blocked"));
                list.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void update(User user) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("UPDATE onlinetesting.users SET name=?, surname=? WHERE id=?");
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getSurname());
            preparedStatement.setInt(3, user.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void delete(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM onlinetesting.users WHERE id=?");
            preparedStatement.setInt(1,id);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() throws Exception {
        System.out.println("Closing!!");
    }
}
