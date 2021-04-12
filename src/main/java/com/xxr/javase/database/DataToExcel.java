package com.xxr.javase.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @author xxr
 * @Description
 * @date 2021/3/24 11:07
 */
public class DataToExcel {

    public static void main(String[] args) {

    }

    public static void getConn(String database, String user,String password,String sql){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://"+database+"?useSSL=false&serverTimezone=UTC";
            Connection conn= DriverManager.getConnection(url,user,password);
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(sql);




        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
