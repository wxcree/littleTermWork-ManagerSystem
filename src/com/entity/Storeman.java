package com.entity;

import com.DAO.Impl.storemanDAOImpl;

import java.sql.SQLException;
import java.util.Scanner;

public class Storeman extends User{
    storemanDAOImpl storemanDAO;
    Storeman(){
        storemanDAO = new storemanDAOImpl();
    }
    public void addKind(int assetsid,int num) throws SQLException {
       /*
       增加某个资产的数量，若没有改资产则创建
        */
       Asset asset = storemanDAO.getAsset(assetsid);
        Asset tmpAsset = new Asset();
        tmpAsset.setId(assetsid);
       if(asset!=null){
           tmpAsset.setName(asset.getName());
           tmpAsset.setTotal(num+asset.getTotal());
            storemanDAO.updateAsset(tmpAsset);
       }else{
           Scanner scanner=new Scanner(System.in);
           System.out.println("请输入资产ID和名字：");
           int id=scanner.nextInt();
           String name=scanner.next();
           tmpAsset.setId(id);
           tmpAsset.setTotal(num);
           tmpAsset.setName(name);
           storemanDAO.addAsset(tmpAsset);
       }
    }
    public void addAssets() throws SQLException {
        Scanner scanner=new Scanner(System.in);
        System.out.println("请输入资产id和增加的数量：");
        int assestsid=scanner.nextInt();
        int num=scanner.nextInt();
        addKind(assestsid,num);
    }
    public void decreaseKind(int assestsid,int num) throws SQLException {
        /*
       删除某个资产的数量
        */
        Asset asset = storemanDAO.getAsset(assestsid);
        Asset tmpAsset = new Asset();
        tmpAsset.setId(assestsid);
        if(asset!=null){
            tmpAsset.setName(asset.getName());
            if(asset.getTotal()>=num)
             tmpAsset.setTotal(asset.getTotal()-num);
            else tmpAsset.setTotal(0);
            storemanDAO.updateAsset(tmpAsset);
        }
    }
    public void decreaseAssets() throws SQLException {
        Scanner scanner=new Scanner(System.in);
        System.out.println("请输入资产id和减少的数量：");
        int assestsid=scanner.nextInt();
        int num=scanner.nextInt();
        decreaseKind(assestsid,num);
    }
    @Override
    void operation() throws SQLException {
        Scanner scanner=new Scanner(System.in);
        boolean isexit=false;
        int flag;
        do{
            System.out.println("您是管理员，可以：");
            System.out.println("1、修改密码");
            System.out.println("2、查看剩余库存");
            System.out.println("3、增加资产");
            System.out.println("4、减少资产数量");
            System.out.println("5、退出");
            flag=scanner.nextInt();
            switch (flag){
                case 1:
                    changePw();
                    break;
                case 2:
                    lookAssets();
                    break;
                case 3:
                    addAssets();
                    break;
                case 4:
                    decreaseAssets();
                    break;
                case 5:
                    isexit=true;
                default:
                    System.out.println("输入错误！");
            }
        }while(!isexit);
    }
}