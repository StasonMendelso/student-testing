package com.stason.testing.model.dao.implement;

import com.stason.testing.controller.exceptions.DataBaseException;
import com.stason.testing.model.dao.ConnectionPool;
import com.stason.testing.model.dao.UserDao;
import com.stason.testing.model.entity.User;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCUserDao implements UserDao {
    private static final Logger logger = Logger.getLogger(JDBCUserDao.class.getName());

    private static class Query{
        static final String FIND_ID_BLOCKED_USERS = "SELECT id FROM onlinetesting.users WHERE blocked=true";
        static final String DELETE_PASSED_TESTS_BY_USER_ID = "DELETE FROM onlinetesting.passedtests WHERE user_id=?";
        static final String FIND_ID_PASSED_TESTS_BY_USER_ID = "SELECT onlinetesting.passedtests.test_id FROM onlinetesting.passedtests WHERE user_id=?;";
        static final String FIND_AND_PAGINATE_ALL_USERS = "SELECT * FROM onlinetesting.users limit ?,?";
        static final String COUNT_ALL_USERS = "SELECT COUNT(1) FROM onlinetesting.users";
        static final String FIND_BY_LOGIN = "SELECT * FROM onlinetesting.users WHERE login=?;";
        static final String CREATE = "INSERT INTO onlinetesting.users (login,password,salt,name,surname,id_role,blocked)values(?,?,?,?,?,?,?);";
        static final String BLOCK = "UPDATE onlinetesting.users set blocked=1 WHERE id=?";
        static final String UNBLOCK = "UPDATE onlinetesting.users set blocked=0 WHERE id=?";
        static final String FIND_BY_ID = "SELECT * FROM onlinetesting.users WHERE id=?";
        static final String UPDATE = "UPDATE onlinetesting.users SET name=?, surname=? WHERE id=?";
        static final String UPDATE_PASSWORD = "UPDATE onlinetesting.users SET password=?, salt=? WHERE login=?";
        static final String DELETE = "DELETE FROM onlinetesting.users WHERE id=?";

    }
    @Override
    public List<Integer> findIdBlockedUsers() {
        List<Integer> list = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(Query.FIND_ID_BLOCKED_USERS)){

            while(resultSet.next()){
                list.add(resultSet.getInt("id"));
            }
        } catch (SQLException e) {
            logger.error("Can't find id blocked users, because", e);
            throw new DataBaseException("Can't find id blocked users");
        }
        return list;
    }


    @Override
    public List<Integer> findIdPassedTestsByUserId(int id) {
        List<Integer> list = new ArrayList<>();

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(Query.FIND_ID_PASSED_TESTS_BY_USER_ID)){
            preparedStatement.setInt(1,id);
            try( ResultSet resultSet = preparedStatement.executeQuery()){
                while(resultSet.next()){
                    list.add(resultSet.getInt("test_id"));
                }
            }

        } catch (SQLException e) {
            logger.error("Can't find id of passed tests by user ="+id+", because", e);
            throw new DataBaseException("Can't find id of passed tests by user ="+id);
        }
        return list;
    }

    @Override
    public List<User> findAndPaginateAllUsers(int index, int paginationParameter) {
        List<User> list = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(Query.FIND_AND_PAGINATE_ALL_USERS)){

            preparedStatement.setInt(1,index);
            preparedStatement.setInt(2,paginationParameter);
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                while(resultSet.next()){
                    User user = new User(resultSet.getInt("id"),
                            resultSet.getString("login"),
                            resultSet.getString("name"),
                            resultSet.getString("surname"),
                            resultSet.getInt("id_role"),
                            resultSet.getBoolean("blocked"));
                    list.add(user);
                }
            }
        } catch (SQLException e) {
            logger.error("Can't find and paginate all users, because", e);
            throw new DataBaseException("Can't find and paginate all users");
        }
        return list;
    }

    @Override
    public int countAllUsers() {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(Query.COUNT_ALL_USERS)){

            if(resultSet.next()){
                return resultSet.getInt("COUNT(1)");
            }
        } catch (SQLException e) {
            logger.error("Can't count all users, because", e);
            throw new DataBaseException("Can't count All Users");
        }
        return 0;
    }



    @Override
    public User findByLogin(String login) {

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(Query.FIND_BY_LOGIN)){
            preparedStatement.setString(1, login);
            try(  ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {

                    return new User(resultSet.getInt("id"),
                            resultSet.getString("login"),
                            resultSet.getString("password"),
                            resultSet.getString("salt"),
                            resultSet.getString("name"),
                            resultSet.getString("surname"),
                            resultSet.getInt("id_role"),
                            resultSet.getBoolean("blocked"));
                }
            }

        } catch (SQLException e) {
            logger.error("Can't find by login "+login+", because", e);
            throw new DataBaseException("Can't find by login "+login);

        }
        return null;
    }

    @Override
    public boolean create(User user) {

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(Query.CREATE)){
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getSalt());
            preparedStatement.setString(4, user.getName());
            preparedStatement.setString(5, user.getSurname());
            preparedStatement.setInt(6, user.getIdRole());
            preparedStatement.setString(7, user.getStringIntBlocked());
            return preparedStatement.executeUpdate()!=0;

        } catch (SQLException e) {
            logger.error("Can't create new user"+user.getLogin()+", because", e);
            throw new DataBaseException("Can't create new user");
        }
    }

    @Override
    public boolean block(int id) {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(Query.BLOCK)){
            preparedStatement.setInt(1,id);
            return preparedStatement.execute();
        } catch (SQLException e) {
            logger.error("Can't block user id="+id+", because", e);
            throw new DataBaseException("Can't block user id="+id);
        }
    }
    @Override
    public boolean unblock(int id) {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(Query.UNBLOCK)){
            preparedStatement.setInt(1,id);
            return preparedStatement.execute();
        } catch (SQLException e) {
            logger.error("Can't unblock user id="+id+", because", e);
            throw new DataBaseException("Can't unblock user id="+id);
        }
    }


    @Override
    public User findById(int id) {

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(Query.FIND_BY_ID)){
            preparedStatement.setInt(1,id);
            try(ResultSet resultSet = preparedStatement.executeQuery()){

                if(resultSet.next()){
                    return new User(resultSet.getInt("id"),
                            resultSet.getString("login"),
                            resultSet.getString("name"),
                            resultSet.getString("surname"),
                            resultSet.getInt("id_role"),
                            resultSet.getBoolean("blocked"));

                }
            }
        } catch (SQLException e) {
            logger.error("Can't find user by id="+id+", because", e);
            throw new DataBaseException("Can't find user by id="+id);
        }
        return null;
    }


    @Override
    public boolean update(User user) {

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement  preparedStatement = connection.prepareStatement(Query.UPDATE)){
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getSurname());
            preparedStatement.setInt(3, user.getId());
            return preparedStatement.executeUpdate()!=0;
        } catch (SQLException e) {
            logger.error("Can't update user, because", e);
            throw new DataBaseException("Can't update user "+user.getLogin());

        }
    }

    @Override
    public boolean updatePassword(String login, String password, String salt) {

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(Query.UPDATE_PASSWORD)){
            preparedStatement.setString(1,password);
            preparedStatement.setString(2, salt);
            preparedStatement.setString(3, login);
            return preparedStatement.executeUpdate()!=0;
        } catch (SQLException e) {
            logger.error("Can't update password, user's login="+login+", because", e);
            throw new DataBaseException("Can't update password for"+login);
        }

    }

    @Override
    public boolean delete(int id) {
        Connection connection = ConnectionPool.getInstance().getConnection();
        try {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(Query.DELETE_PASSED_TESTS_BY_USER_ID);
            preparedStatement.setInt(1,id);
            preparedStatement.executeUpdate();
            preparedStatement = connection.prepareStatement(Query.DELETE);
            preparedStatement.setInt(1,id);
            preparedStatement.executeUpdate();
            connection.commit();
            connection.close();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                logger.error("Can't rollback in delete()", e);
            }
            logger.error("Can't delete, user's id="+id+", because", e);
            throw new DataBaseException("Can't delete, user's id="+id);
        }finally {
            if(connection!=null){
                try {
                    connection.close();
                } catch (SQLException e) {
                    logger.error("Can't close connection", e);
                }
            }
        }
        return false;
    }

    @Override
    public void close() throws Exception {
    }
}
