package com.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/*
数据库连接工厂
编写者：汪晓成  时间：2020.8.24
功能：建立数据库连接
*/
public class ConnectionFactory {
    //static reference to itself
    private static ConnectionFactory instance = new ConnectionFactory();
    public static final String URL = "jdbc:mysql://localhost/ManageSystem";
    public static final String USER = "root";
    public static final String PASSWORD = "123456";
    public static final String DRIVER_CLASS = "com.mysql.cj.jdbc.Driver";
     
    //private constructor
    private ConnectionFactory() {
        try {
            Class.forName(DRIVER_CLASS);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
     
    private Connection createConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.out.println("ERROR: Unable to Connect to Database.");
        }
        return connection;
    }   
     
    public static Connection getConnection() {
        return instance.createConnection();
    }
}