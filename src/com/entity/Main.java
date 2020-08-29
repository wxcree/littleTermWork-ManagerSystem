package com.entity;
/*
主函数类
编写者：朴槿麟  时间：2020.8.27
功能：生成operator对象，调用其work函数完成系统功能
 */
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] a) throws SQLException {
        Operator operator=new Operator();
        operator.work();
    }
}

