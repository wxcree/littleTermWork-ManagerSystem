package com.DAO;
/*
Manage抽象DAO接口
编写者：汪晓成  时间：2020.8.24
功能：提供user_asset表可能会用到的数据库增删改查
*/
import com.entity.Asset;
import com.entity.User;

import java.sql.SQLException;
import java.util.List;

public interface MangeAsset {

    public List<Asset> getUsersAssets(User usr) throws SQLException;

    public boolean addUsersAsset(User user, Asset asset) throws SQLException;

    //public boolean addUsersAssets(User user,List<Asset> assetList) throws SQLException;

    public boolean updateUsersAsset(User user, Asset asset) throws SQLException;

    public boolean deleteUsersAsset(User user, Asset asset) throws SQLException;
}
