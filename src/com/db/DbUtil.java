package com.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
/*
数据库关闭支持
编写者：汪晓成  时间：2020.8.24
功能：关闭连接
*/
public class DbUtil {
	/*public static void resultSetToTableModel(){
		
	}*/
    public static void close(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                /*Ignore*/
            }
        }
    }
 
    public static void close(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                /*Ignore*/
            }
        }
    }
 
    public static void close(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                /*Ignore*/
            }
        }
    }
}