package com.stason.testing.model.dao;


import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * It is a simple connection pool, which created with help of Tomcat JDBC Connection Pool. The class is a singleton
 * @author Stanislav Hlova
 * @version 1.0
 * @see <a href ="https://tomcat.apache.org/tomcat-7.0-doc/jdbc-pool.html">Tomcat JDBC Connection Pool</a>
 */
public class ConnectionPool{
    /* Instance of ConnectionPool */
    private static ConnectionPool instance;
    private DataSource dataSource;
    /**
     * @return return a single instance of ConnectionPool
     */
    public static ConnectionPool getInstance(){
        // DCL
        ConnectionPool result = instance;
        if(result!=null){
            return result;
        }
        synchronized (ConnectionPool.class) {
            if (instance == null) {
                instance = new ConnectionPool();
            }
            return instance;
        }
    }

    /**
     * A constructor of singleton class Connection Pool
     */
    private ConnectionPool(){
        Context initContext = null;
        try {
            initContext = new InitialContext();
            dataSource = (DataSource) initContext.lookup("java:comp/env/jdbc/MyPool");
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get a connection from pool
     * @return return a connection from pool
     */
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
