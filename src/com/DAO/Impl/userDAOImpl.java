package com.DAO.Impl;
/*
UserDAO接口
编写者：汪晓成  时间：2020.8.24
功能：实现基本用户可能会用到的数据库增删改查
*/
import com.DAO.UserDAO;
import com.db.ConnectionFactory;
import com.db.DbUtil;
import com.entity.Factory;
import com.entity.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class userDAOImpl implements UserDAO {
    private Statement statement;
    private Connection connection;
    List<User> users;//缓存
    public userDAOImpl(){
        /*
        初始化类
         */
        users=new ArrayList<User>();
        connection= ConnectionFactory.getConnection();
    }
    @Override
    public List<User> getAllUsers() throws SQLException {
        /*
        获得所有用户
         */
        if(!users.isEmpty())
            return users;
        String query = "SELECT * FROM User";
        ResultSet rs = null;
        Factory factory = new Factory();
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
            while(rs.next()){
                User user = factory.createUser(
                        rs.getInt("id"),
                        rs.getInt("type"),
                        rs.getString("Name"),
                        rs.getString("Password")
                );
                users.add(user);
            }

        }catch (Exception e){
            e.printStackTrace();
            return null;
        } finally {
            DbUtil.close(rs);
            DbUtil.close(statement);
            DbUtil.close(connection);
        }
        return users;

    }

    @Override
    public User getUser(int id) throws SQLException {
        /*
           获得用户
           */
        String query = "SELECT * FROM User WHERE id=" + id;
        ResultSet rs = null;
        Factory factory = new Factory();
        User user = null;
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
            while(rs.next()){
                user = factory.createUser(
                        rs.getInt("id"),
                        rs.getInt("type"),
                        rs.getString("Name"),
                        rs.getString("Password")
                );
            }
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }finally {
            DbUtil.close(rs);
            DbUtil.close(statement);
            DbUtil.close(connection);
        }
        return user;
    }

    @Override
    public boolean updateUser(User user) throws SQLException {
        /*
        更新用户
         */
        connection = ConnectionFactory.getConnection();
        statement = connection.createStatement();
        if(user.getId()>=0){
            String query = "UPDATE User SET User.password='"+user.getPassword()+"',User.name='"+user.getName()+"' WHERE id=" + user.getId();
            try {
                statement.executeUpdate(query);
                users.clear();
                return true;
            }catch (Exception e){
                e.printStackTrace();
                return false;
            } finally {
                DbUtil.close(statement);
                DbUtil.close(connection);
            }
        }else if(user.getName()!=null){
            String query = "UPDATE User SET password='"+user.getPassword()+"',User.name='"+user.getName()+"' WHERE username='" + user.getName()+"'";
            try {
                statement.executeUpdate(query);
                users.clear();
                return true;
            } catch (Exception e){
                e.printStackTrace();
                return false;
            }finally {
                DbUtil.close(statement);
                DbUtil.close(connection);
            }
        }else{
            return false;
        }

    }

    @Override
    public boolean deleteUser(User user) throws SQLException {
        /*
        删除用户
         */
        connection = ConnectionFactory.getConnection();
        statement = connection.createStatement();
        if(user.getId()>=0){
            String query = "DELETE FROM User WHERE id=" + user.getId();
            try {
                statement.executeUpdate(query);
                users.clear();
                return true;
            } catch (Exception e){
                e.printStackTrace();
                return false;
            }finally {
                DbUtil.close(statement);
                DbUtil.close(connection);
            }
        }else if(user.getName()!=null){
            String query = "DELETE FROM User WHERE username='" + user.getName()+"'";
            try {
                statement.executeUpdate(query);
                users.clear();
                return true;
            } catch (Exception e){
                e.printStackTrace();
                return false;
            }finally {
                DbUtil.close(statement);
                DbUtil.close(connection);
            }
        }else{
            return false;
        }



    }

    public User login(String userName,String password) throws SQLException {
        String query = "SELECT * FROM User WHERE name='" + userName+"'AND password='"+password+"'";
        //String query = "SELECT * FROM User ";
        ResultSet rs = null;
        Factory factory = new Factory();
        User user = null;
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
            int rowcount = 0;
            rs.next();
            if (rs.isLast()) {
                rowcount = 1;
            }
            if(rowcount>0)
            {
                user = factory.createUser(
                        rs.getInt("id"),
                        rs.getInt("type"),
                        rs.getString("Name"),
                        rs.getString("Password")
                );
                return user;
            }else{
                return null;
            }

        } catch (Exception e){
            e.printStackTrace();
            return null;
        }finally {
            DbUtil.close(rs);
            DbUtil.close(statement);
            DbUtil.close(connection);
        }


    }
    @Override
    public void addUser(User user) {
        // TODO Auto-generated method stub
        String query = "INSERT INTO `User`(`id`,  `name`, `password`,`type`)VALUES("+user.getId()+",'"+user.getName()+"','"+user.getPassword()+"',"+user.getType()+")";
        ResultSet rs = null;
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.createStatement();
            statement.executeUpdate(query);
            users.clear();
        } catch(Exception e)
        {
            e.printStackTrace();
        }finally {
            DbUtil.close(rs);
            DbUtil.close(statement);
            DbUtil.close(connection);
        }
    }

}
