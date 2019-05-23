package com.lxf.db;

import java.sql.*;

/**
  数据库操类
  */
public class BaseDao {
    private static final String url = "jdbc:mysql://localhost:3306/study";
    private static final String name = "com.mysql.cj.jdbc.Driver";
    private static final String user = "root";
    private static final String password = "crystal1024";

    /**
     * 连接数据库
     */
    public Connection getConn(){
        Connection conn=null;
        try {
            //加载驱动
            Class.forName(name);
            conn= DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    /**
     * 关闭连接
     */
    public void closeConn(ResultSet rs, Statement stat, Connection conn){
        try {
            // 关闭 ctrl+shift+F format
            if (rs != null) {
                rs.close();
            }
            if (stat != null) {
                stat.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void closeConn(Statement stat,Connection conn){
        try {
            if (stat != null) {
                stat.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}