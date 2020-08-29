package com.DAO.Impl;
/*
UManagerDAO接口
编写者：汪晓成  时间：2020.8.24
功能：实现管理员可能会用到的数据库增删改查 和 用户的登陆
*/
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.DAO.MangeAsset;
import com.DAO.UserDAO;
import com.db.ConnectionFactory;
import com.db.DbUtil;
import com.entity.Asset;
import com.entity.Factory;
import com.entity.User;


public class managerDAOImpl implements UserDAO, MangeAsset {
	private  Statement statement;
	private Connection connection;
	List<User> users;
	public managerDAOImpl(){
		 /*
       初始化接口类
        */
		users=new ArrayList<User>();
		connection=ConnectionFactory.getConnection();
	}
	@Override
	public List<User> getAllUsers() throws SQLException {
		/*
		获取所有的用户
		 */
		// TODO Auto-generated method stub
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
		根据ID查找用户信息
		 */
		// TODO Auto-generated method stub
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
			更新user信息
		 */
		// TODO Auto-generated method stub
		connection = ConnectionFactory.getConnection();
        statement = connection.createStatement();
		if(user.getId()>=0){
			String query = "UPDATE User SET User.password='"+user.getPassword()+"',User.usrname='"+user.getName()+"' WHERE id=" + user.getId();
			try {
				statement.executeUpdate(query);
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
		删除用户信息
		 */
		connection = ConnectionFactory.getConnection();
        statement = connection.createStatement();
		if(user.getId()>=0){
			String query = "DELETE FROM User WHERE id=" + user.getId();
			try {
				statement.executeUpdate(query);
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
		/*
		实现登陆逻辑
		 */
		String query = "SELECT * FROM User WHERE username='" + userName+"'AND password='"+password+"'";
        ResultSet rs = null;
        Factory factory = new Factory();
        User user = null;
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
            int rowcount = 0;
            if (rs.last()) {
              rowcount = rs.getRow();
              rs.beforeFirst(); // not rs.first() because the rs.next() below will move on, missing the first element
            }
            if(rowcount>0)
            {
            	while(rs.next()){
					user = factory.createUser(
							rs.getInt("id"),
							rs.getInt("type"),
							rs.getString("name"),
							rs.getString("password")
					);
    			}
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
		/*
		增加新用户
		 */
		String query = "INSERT INTO `User`(`id`,  `name`, `password`,`type`)VALUES("+user.getId()+",'"+user.getName()+"','"+user.getPassword()+"',"+user.getType()+")";
        ResultSet rs = null;
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.createStatement();
            statement.executeUpdate(query);
            
        } catch(Exception e)
        {
        	e.printStackTrace();
        }finally {
            DbUtil.close(rs);
            DbUtil.close(statement);
            DbUtil.close(connection);
        }
	}


	@Override
	public List<Asset> getUsersAssets(User user) throws SQLException {
		/*
		获得用户的所有资产
		 */
		String query = "SELECT * FROM ASSET a join USER_ASSET u on a.id=u.assetid  where userid=" + user.getId();
		ResultSet rs = null;
		List<Asset> assets = new ArrayList<Asset>();
		Asset asset = new Asset();
		try {
			connection = ConnectionFactory.getConnection();
			statement = connection.createStatement();
			rs = statement.executeQuery(query);
			while(rs.next()){
				asset.setId(rs.getInt("id"));
				asset.setName(rs.getString("name"));
				asset.setTotal(rs.getInt("total"));
				assets.add(asset);
			}
		} catch (Exception e){
			e.printStackTrace();
			return null;
		}finally {
			DbUtil.close(rs);
			DbUtil.close(statement);
			DbUtil.close(connection);
		}
		return assets;
	}

	@Override
	public boolean addUsersAsset(User user, Asset asset) throws SQLException {
		/*
		给用户申请资产
		 */
		if(asset.getId()>0&&asset.getId()>0){
			String query = "INSERT INTO `USER_ASSET`(`userid`,`assetid`,`total`)VALUE("+user.getId()+","+asset.getId()+","+asset.getTotal()+")";
			try{
				connection = ConnectionFactory.getConnection();
				statement = connection.createStatement();
				statement.executeUpdate(query);
				return true;
			} catch (Exception e){
				e.printStackTrace();
				return false;
			}finally {
				DbUtil.close(statement);
				DbUtil.close(connection);
			}
		}
		else
			return false;
	}

	@Override
	public boolean updateUsersAsset(User user, Asset asset) throws SQLException {
		/*
		更新用户的资产信息
		 */
		if(asset.getId()>0&&asset.getId()>0){
			String query = "UPDATE USER_ASSET set total="+asset.getTotal();
			try{
				connection = ConnectionFactory.getConnection();
				statement = connection.createStatement();
				statement.executeUpdate(query);
				return true;
			} catch (Exception e){
				e.printStackTrace();
				return false;
			}finally {
				DbUtil.close(statement);
				DbUtil.close(connection);
			}
		}
		else
			return false;
	}

	@Override
	public boolean deleteUsersAsset(User user, Asset asset) throws SQLException {
		/*
		删除用户的资产信息
		 */
		if(asset.getId()>0&&asset.getId()>0){
			String query = "delete from USER_ASSET where userid="+user.getId()+"and assetid="+asset.getId();
			try{
				connection = ConnectionFactory.getConnection();
				statement = connection.createStatement();
				statement.executeUpdate(query);
				return true;
			} catch (Exception e){
				e.printStackTrace();
				return false;
			}finally {
				DbUtil.close(statement);
				DbUtil.close(connection);
			}
		}
		else
			return false;
	}

}
