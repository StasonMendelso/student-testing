package com.stason.testing.model.dao.implement;

import com.stason.testing.controller.exceptions.DataBaseException;
import com.stason.testing.model.dao.ConnectionPool;
import com.stason.testing.model.dao.UserDao;
import com.stason.testing.model.entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JDBCUserDaoTest {
    private final JDBCUserDao userDao = new JDBCUserDao();
    MockedStatic<ConnectionPool> cp;
    @Mock
    ConnectionPool connectionPool;
    Connection mockConnection = mock(Connection.class);
    PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);
    Statement mockStatement = mock(Statement.class);
    ResultSet mockResultSet = mock(ResultSet.class);

    @BeforeEach
    public void setUp() throws SQLException {
        cp = mockStatic(ConnectionPool.class);
        cp.when(ConnectionPool::getInstance).thenReturn(connectionPool);
        when(connectionPool.getConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockConnection.createStatement()).thenReturn(mockStatement);
        Connection testConnection = ConnectionPool.getInstance().getConnection();
        Assertions.assertNotNull(testConnection);
    }

    @AfterEach
    public void tearDown() {
        cp.close();
    }

    private static class Query {
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

    @Test
    void testFindIdBlockedUsersError() throws SQLException {
        when(mockConnection.createStatement()).thenThrow(SQLException.class);
        DataBaseException thrown = assertThrows(DataBaseException.class, userDao::findIdBlockedUsers);
        assertEquals("Can't find id blocked users", thrown.getMessage());
    }

    @Test
    void testFindIdBlockedUsersEmpty() throws SQLException {
        when(mockStatement.executeQuery(Query.FIND_ID_BLOCKED_USERS)).thenReturn(mockResultSet);
        assertEquals(Collections.EMPTY_LIST, userDao.findIdBlockedUsers());

    }

    @Test
    void testFindIdBlockedUsersNotEmpty() throws SQLException {
        int id = 1;
        when(mockResultSet.next()).thenReturn(true).thenReturn(false);
        when(mockResultSet.getInt("id")).thenReturn(id);
        when(mockStatement.executeQuery(Query.FIND_ID_BLOCKED_USERS)).thenReturn(mockResultSet);
        assertEquals(Collections.singletonList(1), userDao.findIdBlockedUsers());

    }

    @Test
    void testFindIdPassedTestsByUserIdError() throws SQLException {
        when(mockConnection.prepareStatement(Query.FIND_ID_PASSED_TESTS_BY_USER_ID)).thenThrow(SQLException.class);
        DataBaseException thrown = assertThrows(DataBaseException.class, () -> userDao.findIdPassedTestsByUserId(1));
        assertEquals("Can't find id of passed tests by user =1", thrown.getMessage());
    }

    @Test
    void testFindIdPassedTestsByUserIdEmpty() throws SQLException {
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        assertEquals(Collections.EMPTY_LIST, userDao.findIdPassedTestsByUserId(1));
    }

    @Test
    void testFindIdPassedTestsByUserIdNotEmpty() throws SQLException {
        when(mockResultSet.next()).thenReturn(true).thenReturn(false);
        when(mockResultSet.getInt("test_id")).thenReturn(12);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        assertEquals(Collections.singletonList(12), userDao.findIdPassedTestsByUserId(1));
    }

    @Test
    void testFindAndPaginateAllUsersError() throws SQLException {
        when(mockConnection.prepareStatement(Query.FIND_AND_PAGINATE_ALL_USERS)).thenThrow(SQLException.class);
        DataBaseException thrown = assertThrows(DataBaseException.class, () -> userDao.findAndPaginateAllUsers(1, 1));
        assertEquals("Can't find and paginate all users", thrown.getMessage());
    }

    @Test
    void testFindAndPaginateAllUsersEmpty() throws SQLException {
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        assertEquals(Collections.EMPTY_LIST, userDao.findAndPaginateAllUsers(1, 1));
    }

    @Test
    void testFindAndPaginateAllUsersNotEmpty() throws SQLException {
        User user = createUser();
        when(mockResultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(mockResultSet.getInt("id")).thenReturn(user.getId());
        when(mockResultSet.getString("login")).thenReturn(user.getLogin());
        when(mockResultSet.getString("name")).thenReturn(user.getName());
        when(mockResultSet.getString("surname")).thenReturn(user.getSurname());
        when(mockResultSet.getInt("id_role")).thenReturn(user.getIdRole());
        when(mockResultSet.getBoolean("blocked")).thenReturn(user.isBlocked());
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        assertEquals(Arrays.asList(user, user), userDao.findAndPaginateAllUsers(1, 2));

        verify(mockConnection, times(1)).prepareStatement(Query.FIND_AND_PAGINATE_ALL_USERS);
        verify(mockPreparedStatement, times(1)).setInt(1, user.getId());
        verify(mockPreparedStatement, times(1)).executeQuery();
        verify(mockPreparedStatement, never()).execute();
        verify(mockPreparedStatement, never()).executeUpdate();
        verify(mockResultSet, times(3)).next();
        verify(mockResultSet, times(4)).getInt(anyString());
        verify(mockResultSet, times(6)).getString(anyString());
        verify(mockResultSet, times(2)).getBoolean(anyString());
    }

    @Test
    void testCountAllUsersError() throws SQLException {
        when(mockConnection.createStatement()).thenThrow(SQLException.class);
        DataBaseException thrown = assertThrows(DataBaseException.class, userDao::countAllUsers);
        assertEquals("Can't count All Users", thrown.getMessage());
    }

    @Test
    void testCountAllUsersZero() throws SQLException {
        when(mockStatement.executeQuery(Query.COUNT_ALL_USERS)).thenReturn(mockResultSet);
        assertEquals(0, userDao.countAllUsers());
    }

    @Test
    void testCountAllUsersNotZero() throws SQLException {
        when(mockResultSet.next()).thenReturn(true).thenReturn(false);
        when(mockResultSet.getInt("COUNT(1)")).thenReturn(3);
        when(mockStatement.executeQuery(Query.COUNT_ALL_USERS)).thenReturn(mockResultSet);
        assertEquals(3, userDao.countAllUsers());
    }

    @Test
    void testFindByLoginError() throws SQLException {
        when(mockConnection.prepareStatement(Query.FIND_BY_LOGIN)).thenThrow(SQLException.class);
        DataBaseException thrown = assertThrows(DataBaseException.class, () -> userDao.findByLogin("login@"));
        assertEquals("Can't find by login login@", thrown.getMessage());
    }

    @Test
    void testFindByLoginEmpty() throws SQLException {
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        assertNull(userDao.findByLogin("login@"));
    }

    @Test
    void testFindByLoginNotEmpty() throws SQLException {
        User user = createUser();
        user.setPassword("pass");
        user.setSalt("salt");
        when(mockResultSet.next()).thenReturn(true).thenReturn(false);
        when(mockResultSet.getInt("id")).thenReturn(user.getId());
        when(mockResultSet.getString("login")).thenReturn(user.getLogin());
        when(mockResultSet.getString("password")).thenReturn(user.getPassword());
        when(mockResultSet.getString("salt")).thenReturn(user.getSalt());
        when(mockResultSet.getString("name")).thenReturn(user.getName());
        when(mockResultSet.getString("surname")).thenReturn(user.getSurname());
        when(mockResultSet.getInt("id_role")).thenReturn(user.getIdRole());
        when(mockResultSet.getBoolean("blocked")).thenReturn(user.isBlocked());
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

        assertEquals(user, userDao.findByLogin(user.getLogin()));

        verify(mockConnection, times(1)).prepareStatement(Query.FIND_BY_LOGIN);
        verify(mockPreparedStatement, times(1)).setString(1, user.getLogin());
        verify(mockPreparedStatement, times(1)).executeQuery();
        verify(mockPreparedStatement, never()).execute();
        verify(mockPreparedStatement, never()).executeUpdate();
        verify(mockResultSet, times(2)).getInt(anyString());
        verify(mockResultSet, times(5)).getString(anyString());
        verify(mockResultSet, times(1)).getBoolean(anyString());
    }

    @Test
    void testCreateError() throws SQLException {
        when(mockConnection.prepareStatement(Query.CREATE)).thenThrow(SQLException.class);
        DataBaseException thrown = assertThrows(DataBaseException.class, () -> userDao.create(new User()));
        assertEquals("Can't create new user", thrown.getMessage());
    }

    @Test
    void testCreate() throws SQLException {
        User user = createUser();
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);
        assertTrue(userDao.create(user));
        verify(mockConnection, times(1)).prepareStatement(Query.CREATE);
        verify(mockPreparedStatement, times(1)).setString(1, user.getLogin());
        verify(mockPreparedStatement, times(1)).setString(2, user.getPassword());
        verify(mockPreparedStatement, times(1)).setString(3, user.getSalt());
        verify(mockPreparedStatement, times(1)).setString(4, user.getName());
        verify(mockPreparedStatement, times(1)).setString(5, user.getSurname());
        verify(mockPreparedStatement, times(1)).setInt(6, user.getIdRole());
        verify(mockPreparedStatement, times(1)).setBoolean(7, user.isBlocked());
        verify(mockPreparedStatement, times(1)).executeUpdate();
        verify(mockPreparedStatement, never()).execute();
        verify(mockPreparedStatement, never()).executeQuery();
    }

    @Test
    void testBlockError() throws SQLException {
        when(mockConnection.prepareStatement(Query.BLOCK)).thenThrow(SQLException.class);
        DataBaseException thrown = assertThrows(DataBaseException.class, () -> userDao.block(1));
        assertEquals("Can't block user id=1", thrown.getMessage());
    }

    @Test
    void testBlock() throws SQLException {
        when(mockPreparedStatement.execute()).thenReturn(true);
        assertTrue(userDao.block(1));
        verify(mockConnection, times(1)).prepareStatement(Query.BLOCK);
        verify(mockPreparedStatement, times(1)).setInt(1, 1);
        verify(mockPreparedStatement, times(1)).execute();
        verify(mockPreparedStatement, never()).executeUpdate();
        verify(mockPreparedStatement, never()).executeQuery();

    }

    @Test
    void testUnblockError() throws SQLException {
        when(mockConnection.prepareStatement(Query.UNBLOCK)).thenThrow(SQLException.class);
        DataBaseException thrown = assertThrows(DataBaseException.class, () -> userDao.unblock(1));
        assertEquals("Can't unblock user id=1", thrown.getMessage());
    }

    @Test
    void testUnblock() throws SQLException {
        when(mockPreparedStatement.execute()).thenReturn(true);
        assertTrue(userDao.unblock(1));
        verify(mockConnection, times(1)).prepareStatement(Query.UNBLOCK);
        verify(mockPreparedStatement, times(1)).setInt(1, 1);
        verify(mockPreparedStatement, times(1)).execute();
        verify(mockPreparedStatement, never()).executeUpdate();
        verify(mockPreparedStatement, never()).executeQuery();

    }

    @Test
    void testFindByIdError() throws SQLException {
        when(mockConnection.prepareStatement(Query.FIND_BY_ID)).thenThrow(SQLException.class);
        DataBaseException thrown = assertThrows(DataBaseException.class, () -> userDao.findById(1));
        assertEquals("Can't find user by id=1", thrown.getMessage());
    }

    @Test
    void testFindByIdEmpty() throws SQLException {
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        assertNull(userDao.findById(1));
    }

    @Test
    void testFindByIdNotEmpty() throws SQLException {
        User user = createUser();
        when(mockResultSet.next()).thenReturn(true).thenReturn(false);
        when(mockResultSet.getInt("id")).thenReturn(user.getId());
        when(mockResultSet.getString("login")).thenReturn(user.getLogin());
        when(mockResultSet.getString("name")).thenReturn(user.getName());
        when(mockResultSet.getString("surname")).thenReturn(user.getSurname());
        when(mockResultSet.getInt("id_role")).thenReturn(user.getIdRole());
        when(mockResultSet.getBoolean("blocked")).thenReturn(user.isBlocked());
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        assertEquals(user, userDao.findById(user.getId()));

        verify(mockConnection, times(1)).prepareStatement(Query.FIND_BY_ID);
        verify(mockPreparedStatement, times(1)).setInt(1, user.getId());
        verify(mockPreparedStatement, times(1)).executeQuery();
        verify(mockPreparedStatement, never()).execute();
        verify(mockPreparedStatement, never()).executeUpdate();
        verify(mockResultSet, times(2)).getInt(anyString());
        verify(mockResultSet, times(3)).getString(anyString());
        verify(mockResultSet, times(1)).getBoolean(anyString());
    }


    @Test
    void testUpdateError() throws SQLException {
        when(mockConnection.prepareStatement(Query.UPDATE)).thenThrow(SQLException.class);
        DataBaseException thrown = assertThrows(DataBaseException.class, () -> userDao.update(new User()));
        assertEquals("Can't update user null", thrown.getMessage());
    }

    @Test
    void testUpdate() throws SQLException {
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);
        User user = createUser();
        assertTrue(userDao.update(user));
        verify(mockConnection, times(1)).prepareStatement(Query.UPDATE);
        verify(mockPreparedStatement, times(1)).setString(1, user.getName());
        verify(mockPreparedStatement, times(1)).setString(2, user.getSurname());
        verify(mockPreparedStatement, times(1)).setInt(3, user.getId());
        verify(mockPreparedStatement, times(1)).executeUpdate();
        verify(mockPreparedStatement, never()).execute();
        verify(mockPreparedStatement, never()).executeQuery();
    }

    @Test
    void testUpdatePasswordError() throws SQLException {
        when(mockConnection.prepareStatement(Query.UPDATE_PASSWORD)).thenThrow(SQLException.class);
        DataBaseException thrown = assertThrows(DataBaseException.class, () -> userDao.updatePassword("login@", "password", "salt"));
        assertEquals("Can't update password for login@", thrown.getMessage());
    }

    @Test
    void testUpdatePassword() throws SQLException {
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);
        String login = "login@", password = "password", salt = "salt";
        assertTrue(userDao.updatePassword(login, password, salt));
        verify(mockConnection, times(1)).prepareStatement(Query.UPDATE_PASSWORD);
        verify(mockPreparedStatement, times(1)).setString(1, password);
        verify(mockPreparedStatement, times(1)).setString(2, salt);
        verify(mockPreparedStatement, times(1)).setString(3, login);
        verify(mockPreparedStatement, times(1)).executeUpdate();
        verify(mockPreparedStatement, never()).execute();
        verify(mockPreparedStatement, never()).executeQuery();
    }

    @Test
    void testDeleteError() throws SQLException {
        when(mockConnection.prepareStatement(Query.DELETE_PASSED_TESTS_BY_USER_ID)).thenThrow(SQLException.class);
        DataBaseException thrown = assertThrows(DataBaseException.class, () -> userDao.delete(1));
        assertEquals("Can't delete, user's id=1", thrown.getMessage());
        verify(mockConnection, times(1)).rollback();
    }
    @Test
    void testDelete() throws SQLException {
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);
        assertTrue(userDao.delete(1));
        verify(mockConnection, times(1)).setAutoCommit(false);
        verify(mockConnection, times(1)).prepareStatement(Query.DELETE_PASSED_TESTS_BY_USER_ID);
        verify(mockConnection, times(1)).prepareStatement(Query.DELETE);
        verify(mockPreparedStatement,times(2)).setInt(1,1);
        verify(mockPreparedStatement,times(2)).executeUpdate();
        verify(mockConnection, times(1)).commit();

    }
    private User createUser() {
        return new User(1, "login@", "name", "surname", 1, true);
    }

}