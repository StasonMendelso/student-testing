package com.stason.testing.model.dao.implement;

import com.stason.testing.model.dao.ConnectionPool;
import com.stason.testing.model.dao.UserDao;
import com.stason.testing.model.entity.Role;
import com.stason.testing.model.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCUserDao implements UserDao {
    private static class Query{
        static final String findIdBlockedUsers = "SELECT id FROM onlinetesting.users WHERE blocked=true";
        static final String deletePassedTestsByUserId = "DELETE FROM onlinetesting.passedtests WHERE user_id=?";
        static final String findIdPassedTestsByUserId = "SELECT onlinetesting.passedtests.test_id FROM onlinetesting.passedtests WHERE user_id=?;";
        static final String findAndPaginateAllUsers = "SELECT * FROM onlinetesting.users limit ?,?";
        static final String countAllUsers = "SELECT COUNT(1) FROM onlinetesting.users";
        static final String checkLogin = "SELECT * FROM onlinetesting.users WHERE login=?;";
        static final String findByLogin = "SELECT * FROM onlinetesting.users WHERE login=?;";
        static final String create = "INSERT INTO onlinetesting.users (login,password,salt,name,surname,role,blocked)values(?,?,?,?,?,?,?);";
        static final String block = "UPDATE onlinetesting.users set blocked=1 WHERE id=?";
        static final String unblock = "UPDATE onlinetesting.users set blocked=0 WHERE id=?";
        static final String findById = "SELECT * FROM onlinetesting.users WHERE id=?";
        static final String findAll = "SELECT * FROM onlinetesting.users";
        static final String update = "UPDATE onlinetesting.users SET name=?, surname=? WHERE id=?";
        static final String updatePassword = "UPDATE onlinetesting.users SET password=?, salt=? WHERE login=?";
        static final String delete = "DELETE FROM onlinetesting.users WHERE id=?";

    }
    @Override
    public List<Integer> findIdBlockedUsers() {
        List<Integer> list = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(Query.findIdBlockedUsers)){

            while(resultSet.next()){
                list.add(resultSet.getInt("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void deletePassedTestsByUserId(int id) {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(Query.deletePassedTestsByUserId)){
            preparedStatement.setInt(1,id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<Integer> findIdPassedTestsByUserId(int id) {
        List<Integer> list = new ArrayList<>();

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(Query.findIdPassedTestsByUserId)){
            preparedStatement.setInt(1,id);
            try( ResultSet resultSet = preparedStatement.executeQuery()){
                while(resultSet.next()){
                    list.add(resultSet.getInt("test_id"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<User> findAndPaginateAllUsers(int index, int paginationParameter) {
        List<User> list = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(Query.findAndPaginateAllUsers)){

            preparedStatement.setInt(1,index);
            preparedStatement.setInt(2,paginationParameter);
            try(ResultSet resultSet = preparedStatement.executeQuery()){
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
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public int countAllUsers() {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(Query.countAllUsers)){

            if(resultSet.next()){
                return resultSet.getInt("COUNT(1)");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }


    @Override
    public boolean checkLogin(String login){

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(Query.checkLogin)){
            preparedStatement.setString(1, login);
            try(  ResultSet resultSet = preparedStatement.executeQuery()){
                if(resultSet.next()) return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public User findByLogin(String login) {

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(Query.findByLogin)){
            preparedStatement.setString(1, login);
            try(  ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    User user = new User();
                    user.setLogin(resultSet.getString("login"));
                    user.setName(resultSet.getString("name"));
                    user.setSurname(resultSet.getString("surname"));
                    user.setRole(Role.valueOf(resultSet.getString("role")));
                    user.setId(resultSet.getInt("id"));
                    user.setBlocked(resultSet.getBoolean("blocked"));
                    user.setPassword(resultSet.getString("password"));
                    user.setSalt(resultSet.getString("salt"));
                    return user;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void create(User user) {

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(Query.create)){
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getSalt());
            preparedStatement.setString(4, user.getName());
            preparedStatement.setString(5, user.getSurname());
            preparedStatement.setString(6, user.getRole());
            preparedStatement.setString(7, user.getStringIntBlocked());
            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean block(int id) {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(Query.block)){
            preparedStatement.setInt(1,id);
            return preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    @Override
    public boolean unblock(int id) {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(Query.unblock)){
            preparedStatement.setInt(1,id);
            return preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    @Override
    public User findById(int id) {

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(Query.findById)){
            preparedStatement.setInt(1,id);
            try(ResultSet resultSet = preparedStatement.executeQuery()){

                if(resultSet.next()){
                    User user = new User();
                    user.setLogin(resultSet.getString("login"));
                    user.setName(resultSet.getString("name"));
                    user.setSurname(resultSet.getString("surname"));
                    user.setRole(Role.valueOf(resultSet.getString("role")));
                    user.setId(resultSet.getInt("id"));
                    user.setBlocked(resultSet.getBoolean("blocked"));
                    connection.close();
                    return user;

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<User> findAll() {
        List<User> list = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement()){
            try(ResultSet resultSet = statement.executeQuery(Query.findAll)){
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
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void update(User user) {

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement  preparedStatement = connection.prepareStatement(Query.update)){
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getSurname());
            preparedStatement.setInt(3, user.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void updatePassword(String login, String password, String salt) {

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(Query.updatePassword)){
            preparedStatement.setString(1,password);
            preparedStatement.setString(2, salt);
            preparedStatement.setString(3, login);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(Query.delete)){
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
