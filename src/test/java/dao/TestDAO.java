package dao;

import com.stason.testing.model.dao.ConnectionPool;
import com.stason.testing.model.dao.UserDao;
import com.stason.testing.model.dao.implement.JDBCUserDao;
import com.stason.testing.model.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
public class TestDAO {
    @Mock
    ConnectionPool connectionPool;
    @Mock
    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/onlinetestingfortest","root","root");

    PreparedStatement stmt = connection.prepareStatement("INSERT INTO onlinetestingfortest.users (login,password,salt,name,surname,id_role,blocked)values(?,?,?,?,?,?,?);");
    PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM onlinetestingfortest.users WHERE login=?");
    public TestDAO() throws SQLException {
    }


    @Test
    void testCreateUser() throws SQLException {
        try( MockedStatic<ConnectionPool> cp = Mockito.mockStatic(ConnectionPool.class)){
            cp.when(ConnectionPool::getInstance).thenReturn(connectionPool);
            Mockito.when(connectionPool.getConnection()).thenReturn(connection);

            Connection testConnection = ConnectionPool.getInstance().getConnection();
            Assertions.assertNotNull(testConnection);
            Mockito.when(connection.prepareStatement(anyString())).thenReturn(stmt);

            UserDao userDao = new JDBCUserDao();
            User user = new User();
            user.setIdRole(1);
            user.setLogin("testlogin");
            user.setPassword("1234");
            user.setSalt("salt");
            user.setBlocked(false);
            user.setSurname("Surname");
            user.setName("Name");

            userDao.create(user);

            Mockito.when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
            user = userDao.findByLogin("testlogin");

        }

    }
}
