package com.DAO;
/*
Asset抽象DAO接口
编写者：汪晓成  时间：2020.8.24
功能：提供asset表可能会用到的数据库增删改查
*/
import com.entity.Asset;

import java.sql.SQLException;
import java.util.List;

public interface AssetDAO {
    public List<Asset> getAllAssets() throws SQLException;

    public Asset getAsset(int id) throws SQLException;

    public boolean addAsset(Asset asset) throws SQLException;

    public boolean updateAsset(Asset asset) throws SQLException;

    public boolean deleteAsset(Asset asset) throws SQLException;
}
