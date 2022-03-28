package com.stason.testing.model.dao;


import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionPool {

    private static ConnectionPool instance;

    public static synchronized ConnectionPool getInstance(){
        if(instance==null){
            instance = new ConnectionPool();
        }
        return instance;
    }

    private DataSource dataSource;

    private ConnectionPool(){
        Context initContext = null;
        try {
            initContext = new InitialContext();

            dataSource = (DataSource)initContext.lookup("java:comp/env/jdbc/MyPool");
        } catch (NamingException e) {
            e.printStackTrace();
        }

    }
    public Connection getConnection(){
        Connection con = null;
        try {
            con = dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con;
    }
}
