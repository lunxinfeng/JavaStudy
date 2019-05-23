package com.lxf.db;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class RegisterDao extends BaseDao {
    public int insertUser(String account,String password ) {
        Connection conn=null;
        PreparedStatement stat=null;
        int rowCount=0;
        try {
            conn=getConn();
            String sql="insert into users(account, password) value(?,?)";
            stat=conn.prepareStatement(sql);
            //设置值
            stat.setString(1, account);
            stat.setString(2, password);
            System.out.println(stat);
            //执行
            rowCount=stat.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            closeConn(stat, conn);
        }
        return rowCount;
    }
}
