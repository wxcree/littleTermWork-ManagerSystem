package com.entity;
/*
工厂类
编写者：欧阳建鹏  时间：2020.8.26
功能：工厂模式，负责对输入的不同type生成不同user子类的对象
 */
public class Factory {
    public User createUser(int id ,int type,String name,String password){
        User u=null;
            switch (type){
                case 0:
                    u=new OrdinaryUser();
                    break;
                case 1:
                    u=new Manager();
                    break;
                case 2:
                    u=new Storeman();
                    break;
            }
        u.setId(id);
        u.setName(name);
        u.setPassword(password);
        u.setType(type);
        return u;
    }

}
