package com.entity;

import com.DAO.Impl.*;
import com.DAO.UserDAO;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
/*
管理员类
编写者：欧阳建鹏  时间：2020.8.26
功能：实现管理员的功能，search实现
 */
public class Manager extends User {
    managerDAOImpl userDAOManager;
    List<User> userList;
    Factory factory;
    Manager(){
        userDAOManager = new managerDAOImpl();
        factory = new Factory();
    }
    public void search(String name)throws SQLException{
       /*
       查看对应name的所有借用情况
        */
       User user = new OrdinaryUser();
       user.setName(name);
       List<Asset>assetList = userDAOManager.getUsersAssets(user);
        int i = 0;
        System.out.print("No\tid\tname\n");
        while (i<assetList.size()){
            Asset asset = assetList.get(i);
            System.out.print(i+"\t");
            System.out.print(asset.getId()+"\t");
            System.out.print(asset.getName()+"\t");
        }
    }
    public void Searchu_a()throws SQLException{
        Scanner scanner=new Scanner(System.in);
        System.out.println("请输入用户的name：");
        String name=scanner.next();
        search(name);
    }
    public void SearchAllu_a() throws SQLException {
       /*
       查看所有的借用情况，利用search函数
        */
        if(userList==null)
            userList = userDAOManager.getAllUsers();
        int i = 0;
        while (i<userList.size()){
            search(userList.get(i).getName());
        }
    }

    public void listUser() throws SQLException {
        if(userList==null)
            userList = userDAOManager.getAllUsers();
        int i = 0;
        System.out.print("No\tid\tname\ttype\n");
        while (i<userList.size()){
            User user = userList.get(i);
            System.out.print(i+"\t");
            System.out.print(user.getId()+"\t");
            System.out.print(user.getName()+"\t");
            System.out.print(user.getType()+"\n");
            i++;
        }
    }
    public void addUser() throws SQLException {
        Scanner scanner=new Scanner(System.in);
        System.out.println("请输入用户的id,name和type\n初始密码默认123456");
        int id=scanner.nextInt();
        String name = scanner.next();
        int type=scanner.nextInt();
        userDAOManager.addUser(factory.createUser(id,type,name,"123456"));
        userList = userDAOManager.getAllUsers();
    }
    public void deleteUser(){
        System.out.println("输入想删除的用户id");
        Scanner scanner=new Scanner(System.in);
        User user = new OrdinaryUser();
        int id=scanner.nextInt();
        user.setId(id);
        try {
            userDAOManager.deleteUser(user);
        } catch (Exception e){
            System.out.println("删除失败");
        }
    }

    public void returnAsset(String name,String password,int assetid) throws SQLException {
        User user= userDAOManager.login(name,password);
        if(user!=null){
            List<Asset> LA=userDAOManager.getUsersAssets(user);
            int i=0;
            Asset tmpAsset;
            boolean isFound=false;
            while (i<LA.size()){
                Asset asset = LA.get(i);//借用关系表中的asset，里面的total记录的是申请的数量
                if(asset.getId()==assetid){
                    isFound=true;
                    storemanDAOImpl storemanDAO=new storemanDAOImpl();
                    tmpAsset=storemanDAO.getAsset(assetid);//获得资产表中的对象
                    asset.setTotal(tmpAsset.getTotal()+asset.getTotal());
                    storemanDAO.updateAsset(asset);
                    userDAOManager.deleteUsersAsset(user,tmpAsset);
                    break;
                }
            }
            if(!isFound){
                System.out.println("资产id错误");
            }
        }
        else{
            System.out.println("用户名或密码错误!");
        }
    }

    public void returnAssets() throws SQLException {
        Scanner scanner=new Scanner(System.in);
        System.out.println("请输入用户名，密码和资产id：");
        String name=scanner.next();
        String password=scanner.next();
        int assetid=scanner.nextInt();
        returnAsset(name,password,assetid);
    }

    public void applyAsset(String name,String password,int assetid) throws SQLException {
        User user= userDAOManager.login(name,password);
        storemanDAOImpl storemanDAO=new storemanDAOImpl();
        Asset asset=storemanDAO.getAsset(assetid);
        if(user!=null&&asset!=null){
            System.out.println("资产信息：");
            System.out.println("id:"+asset.getId()+"name:"+asset.getName()+"数量:"+asset.getTotal());
            Scanner scanner=new Scanner(System.in);
            System.out.println("请输入申请的数量:");
            int num=scanner.nextInt();
            if(num>asset.getTotal()){
                System.out.println("数量错误");
            }else{
                Asset tmpasset=new Asset();
                tmpasset.setTotal(num);
                tmpasset.setId(assetid);
                tmpasset.setName(asset.getName());
                asset.setTotal(asset.getTotal()-num);
                storemanDAO.updateAsset(asset);
                userDAOManager.addUsersAsset(user,tmpasset);
            }
        }
        else{
            System.out.println("用户名、密码或资产id错误!");
        }
    }

    public void applyAssets() throws SQLException {
        Scanner scanner=new Scanner(System.in);
        System.out.println("请输入申请者的用户名、密码和申请的资源编号：");
        String name=scanner.next();
        String password=scanner.next();
        int assetid=scanner.nextInt();
        applyAsset(name,password,assetid);
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
            System.out.println("3、查看某个用户的借用情况");
            System.out.println("4、查看所有用户的借用情况");
            System.out.println("5、展示用户");
            System.out.println("6、添加用户");
            System.out.println("7、删除用户");
            System.out.println("8、归还资产");
            System.out.println("9、申请资产");
            System.out.println("10、退出");
            flag=scanner.nextInt();
            switch (flag){
                case 1:
                    changePw();
                    break;
                case 2:
                    lookAssets();
                    break;
                case 3:
                    Searchu_a();
                    break;
                case 4:
                    SearchAllu_a();
                    break;
                case 5:
                    listUser();
                    break;
                case 6:
                    addUser();
                    break;
                case 7:
                    deleteUser();
                    break;
                case 8:
                    returnAssets();
                    break;
                case 9:
                    applyAssets();
                    break;
                case 10:
                    isexit=true;
                    break;
                default:
                    System.out.println("输入错误！");
            }
        }while(!isexit);
    }
}