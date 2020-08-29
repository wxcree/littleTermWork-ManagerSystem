package com.DAO.Impl;
/*
StoremanDAO接口
编写者：汪晓成  时间：2020.8.24
功能：实现库管用户可能会用到的数据库增删改查
*/
import com.DAO.AssetDAO;
import com.db.ConnectionFactory;
import com.db.DbUtil;
import com.entity.Asset;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class storemanDAOImpl implements AssetDAO {
    private Statement statement;
    private Connection connection;
    List<Asset> assetList;

    public storemanDAOImpl(){
        /*
        初始化类
         */
        List<Asset> assetList=new ArrayList<Asset>();
        connection=ConnectionFactory.getConnection();
    }
    @Override
    public List<Asset> getAllAssets() throws SQLException {
        /*
        获得所有资产
         */
        String query = "SELECT * FROM ASSET";
        ResultSet rs = null;
        Asset asset = new Asset();
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
            while(rs.next()){
                asset.setId(rs.getInt("id"));
                asset.setName(rs.getString("name"));
                asset.setTotal(rs.getInt("total"));
                assetList.add(asset);
            }
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }finally {
            DbUtil.close(rs);
            DbUtil.close(statement);
            DbUtil.close(connection);
        }
        return assetList;
    }

    @Override
    public Asset getAsset(int id) throws SQLException {
        /*
        根据id查找资产信息
         */
        String query = "SELECT * FROM ASSET where id=" + id;
        ResultSet rs = null;
        Asset asset = new Asset();
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
            while(rs.next()){
                asset.setId(rs.getInt("id"));
                asset.setName(rs.getString("name"));
                asset.setTotal(rs.getInt("total"));
            }
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }finally {
            DbUtil.close(rs);
            DbUtil.close(statement);
            DbUtil.close(connection);
        }
        return asset;
    }

    @Override
    public boolean addAsset(Asset asset) throws SQLException {
        /*
        添加资产
         */
        if(asset.getId()>0){
            String query = "INSERT INTO ASSET(id,name,total) VALUES("+asset.getId()+",'"+asset.getName()+"',"+asset.getTotal()+")";
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
    public boolean updateAsset(Asset asset) throws SQLException {
        /*
        增加资产
         */
        if(asset.getId()>0){
            String query = "UPADTE ASSET SET total="+asset.getTotal()+"where id="+asset.getId();
            try{
                connection = ConnectionFactory.getConnection();
                statement = connection.createStatement();
                statement.executeUpdate(query);
                return true;
            } catch (Exception e){
                e.printStackTrace();
                return false;
            } finally{
                DbUtil.close(statement);
                DbUtil.close(connection);
            }
        }
        else
            return false;
    }

    @Override
    public boolean deleteAsset(Asset asset) throws SQLException {
        /*
        删除资产
         */
        if(asset.getId()>0){
            String query = "DELETE FROM ASSET where id="+asset.getId();
            try{
                connection = ConnectionFactory.getConnection();
                statement = connection.createStatement();
                statement.executeUpdate(query);
                return true;
            } catch (Exception e){
                e.printStackTrace();
                return false;
            } finally{
                DbUtil.close(statement);
                DbUtil.close(connection);
            }
        }
        else
            return false;
    }


}
