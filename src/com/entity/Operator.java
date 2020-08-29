package com.entity;

import com.DAO.Impl.userDAOImpl;

import java.sql.SQLException;
import java.util.Scanner;
/*
编写者：朴槿麟  时间：2020.8.26
功能：logon负责用户的登录，work负责用户界面并根据用户的选择以调用user的功能函数operation
 */
public class Operator {
    public int getType(String name){
        int type=0;
       /*
       查询数据库获取对应name的user的type
        */
        return type;
    }
    public int getID(String name){
        int id=0;
       /*
       查询数据库获取对应name的user的id
        */
        return id;
    }
    public boolean login(String name, String password) throws SQLException {
        boolean islog=true;
    /*
    查找数据库，看是否有对应的name和password
     */
        userDAOImpl userDAO=new userDAOImpl();
        User user=userDAO.login(name,password);
        if(user==null)
            islog=false;
        return islog;
    }
    public User logon() throws SQLException {//登录并获取对应的user
        User user=null;
        Factory factory=new Factory();
        System.out.println("请输入用户名和密码:");
        Scanner scan=new Scanner(System.in);
        String name=scan.next();
        String password=scan.next();
        userDAOImpl userDAO=new userDAOImpl();
        user=userDAO.login(name,password);
        return user;
    }
    public void work() throws SQLException {
        System.out.println("欢迎使用'固定资产管理系统'");
        Scanner scan=new Scanner(System.in);
        boolean isExit=false;
        do{
            System.out.println("1、用户登录");
            System.out.println("2、退出");
            int choose=scan.nextInt();
            switch(choose){
                case 1:
                    User user=null;
                    int yn=1;
                    do{
                        user= logon();   //为null则登录失败
                        if(user==null){
                            System.out.println("登录失败，重新登录则输入任何数字，退出0");
                            yn=scan.nextInt();
                        }else{
                            System.out.println("登录成功！");
                            user.operation();
                            isExit=true;
                            yn=0;
                        }
                    }while(yn!=0);
                    break;
                case 2:
                    isExit=true;
                    break;
                default:
                    System.out.println("输入了错误的数字！");
            }
        }while(!isExit);
    }
}
