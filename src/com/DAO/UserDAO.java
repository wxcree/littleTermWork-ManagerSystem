package com.DAO;
/*
User抽象DAO接口
编写者：汪晓成  时间：2020.8.24
功能：提供User表可能会用到的数据库增删改查
*/

import java.sql.SQLException;
import java.util.List;
import com.entity.User;

public interface UserDAO {
	public List<User> getAllUsers() throws SQLException;

	public User getUser(int id) throws SQLException;
	
	public void addUser(User user) throws SQLException;

	public boolean updateUser(User user) throws SQLException;

	
	public User login(String userName, String password) throws SQLException;
	

	boolean deleteUser(User user) throws SQLException;

}
