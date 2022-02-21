package com.stason.testing.model.dao.implement;

import com.stason.testing.model.dao.DaoFactory;
import com.stason.testing.model.dao.TestDao;
import com.stason.testing.model.dao.UserDao;


import java.sql.*;

public class JDBCDaoFactory extends DaoFactory {
    private  Connection connection;
    public JDBCDaoFactory() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/onlinetesting","root","root");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public UserDao createUserDao() {
        return new JDBCUserDao(this.getConnection());
    }
    public TestDao createTestDao() {
        return new JDBCTestDao(this.getConnection());
    }
    private Connection getConnection(){
        return connection;
    }
}
