package com.stason.testing.model.dao.implement;

import com.stason.testing.model.dao.ConnectionPool;
import com.stason.testing.model.dao.UserDao;
import com.stason.testing.model.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
class JDBCUserDaoTest {

        @Mock
        ConnectionPool connectionPool;
        @Mock
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/onlinetestingfortest","root","root");

        PreparedStatement create = connection.prepareStatement("INSERT INTO onlinetestingfortest.users (login,password,salt,name,surname,id_role,blocked)values(?,?,?,?,?,?,?);");
        Statement statement = connection.createStatement();
        PreparedStatement deletePassedTestsByUserId = connection.prepareStatement("DELETE FROM onlinetestingfortest.passedtests WHERE user_id=?");
        PreparedStatement findIdPassedTestsByUserId = connection.prepareStatement("SELECT onlinetestingfortest.passedtests.test_id FROM onlinetesting.passedtests WHERE user_id=?;");
        PreparedStatement findAndPaginateAllUsers = connection.prepareStatement("SELECT * FROM onlinetestingfortest.users limit ?,?");
        PreparedStatement countAllUsers = connection.prepareStatement("SELECT COUNT(1) FROM onlinetestingfortest.users");
        PreparedStatement findByLogin = connection.prepareStatement("SELECT * FROM onlinetestingfortest.users WHERE login=?;");
        PreparedStatement block = connection.prepareStatement("UPDATE onlinetestingfortest.users set blocked=1 WHERE id=?");
        PreparedStatement unblock = connection.prepareStatement("UPDATE onlinetestingfortest.users set blocked=0 WHERE id=?");
        PreparedStatement findById = connection.prepareStatement("SELECT * FROM onlinetestingfortest.users WHERE id=?");
        PreparedStatement update = connection.prepareStatement("UPDATE onlinetestingfortest.users SET name=?, surname=? WHERE id=?");
        PreparedStatement updatePassword = connection.prepareStatement("UPDATE onlinetestingfortest.users SET password=?, salt=? WHERE login=?");
        PreparedStatement delete = connection.prepareStatement("DELETE FROM onlinetestingfortest.users WHERE id=?");
        PreparedStatement select = connection.prepareStatement("SELECT * FROM onlinetestingfortest.users where login=?");

        UserDao userDao = new JDBCUserDao();

    JDBCUserDaoTest() throws SQLException {
    }
    @Test
    void create() throws SQLException {
        try( MockedStatic<ConnectionPool> cp = Mockito.mockStatic(ConnectionPool.class)){
            cp.when(ConnectionPool::getInstance).thenReturn(connectionPool);
            Mockito.when(connectionPool.getConnection()).thenReturn(connection);
            Connection testConnection = ConnectionPool.getInstance().getConnection();
            Assertions.assertNotNull(testConnection);

            User user = new User("login","password","salt","name","surname");
            user.setBlocked(false);
            user.setIdRole(1);

            Mockito.when(connection.prepareStatement(anyString())).thenReturn(create);
            userDao.create(user);
            Mockito.when(connection.prepareStatement(anyString())).thenReturn(select);
            PreparedStatement preparedStatement = connection.prepareStatement("");
            preparedStatement.setString(1,"login");
            ResultSet resultSet = preparedStatement.executeQuery();
            User userFromDB = null;
            if(resultSet.next()) {
                userFromDB = new User(resultSet.getString("login"),
                        resultSet.getString("password"),
                        resultSet.getString("salt"),
                        resultSet.getString("name"),
                        resultSet.getString("surname"));
                userFromDB.setIdRole(resultSet.getInt("id_role"));
                userFromDB.setBlocked(resultSet.getBoolean("blocked"));
            }else{
                fail();
            }
           assertEquals(user,userFromDB);
        }
    }

    @Test
    void delete() throws SQLException {
        try( MockedStatic<ConnectionPool> cp = Mockito.mockStatic(ConnectionPool.class)){
            cp.when(ConnectionPool::getInstance).thenReturn(connectionPool);
            Mockito.when(connectionPool.getConnection()).thenReturn(connection);
            Connection testConnection = ConnectionPool.getInstance().getConnection();
            Assertions.assertNotNull(testConnection);

            Mockito.when(connection.prepareStatement(anyString())).thenReturn(select);
            PreparedStatement preparedStatement = connection.prepareStatement("");
            preparedStatement.setString(1,"login");
            ResultSet resultSet = preparedStatement.executeQuery();
            User userFromDB = null;
            if(resultSet.next()) {
                userFromDB = new User(resultSet.getString("login"),
                        resultSet.getString("password"),
                        resultSet.getString("salt"),
                        resultSet.getString("name"),
                        resultSet.getString("surname"));
                userFromDB.setIdRole(resultSet.getInt("id_role"));
                userFromDB.setBlocked(resultSet.getBoolean("blocked"));
                userFromDB.setId(resultSet.getInt("id"));
            }else{
                fail();
            }

            Mockito.when(connection.prepareStatement(anyString())).thenReturn(delete);
            userDao.delete(userFromDB.getId());
            Mockito.when(connection.prepareStatement(anyString())).thenReturn(select);
            preparedStatement = connection.prepareStatement("");
            preparedStatement.setString(1,"login");
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) fail();
        }
    }
    @Test
    void findIdBlockedUsers() throws SQLException {

        try( MockedStatic<ConnectionPool> cp = Mockito.mockStatic(ConnectionPool.class)) {
            cp.when(ConnectionPool::getInstance).thenReturn(connectionPool);
            Mockito.when(connectionPool.getConnection()).thenReturn(connection);
            Connection testConnection = ConnectionPool.getInstance().getConnection();
            Assertions.assertNotNull(testConnection);


            ResultSet resultSet = Mockito.mock(ResultSet.class);
            Mockito.when(resultSet.next()).thenReturn(false);

            Statement statement = Mockito.mock(Statement.class);
            Mockito.when(statement.executeQuery("SELECT id FROM onlinetesting.users WHERE blocked=true")).thenReturn(resultSet);
            Mockito.when(connection.createStatement()).thenReturn(statement);
            List<Integer> list = userDao.findIdBlockedUsers();
            if(!list.isEmpty())fail();
        }
    }

    @Test
    void findIdPassedTestsByUserId() {
    }

    @Test
    void findAndPaginateAllUsers() {
    }

    @Test
    void countAllUsers() throws SQLException {
        try( MockedStatic<ConnectionPool> cp = Mockito.mockStatic(ConnectionPool.class)) {
            cp.when(ConnectionPool::getInstance).thenReturn(connectionPool);
            Mockito.when(connectionPool.getConnection()).thenReturn(connection);
            Connection testConnection = ConnectionPool.getInstance().getConnection();
            Assertions.assertNotNull(testConnection);


            ResultSet resultSet = Mockito.mock(ResultSet.class);
            Mockito.when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
            Mockito.when(resultSet.getInt("СOUNT(1)")).thenReturn(1);

            Statement statement = Mockito.mock(Statement.class);
            Mockito.when(statement.executeQuery("SELECT COUNT(1) FROM onlinetesting.users")).thenReturn(resultSet);
            Mockito.when(connection.createStatement()).thenReturn(statement);
            int i = userDao.countAllUsers();
            assertEquals(1,i);
        }
    }

    @Test
    void findByLogin() {
    }



    @Test
    void block() {
    }

    @Test
    void unblock() {
    }

    @Test
    void findById() {
    }

    @Test
    void findAll() {
    }

    @Test
    void update() {
    }

    @Test
    void updatePassword() {
    }



    @Test
    void close() {
    }
}