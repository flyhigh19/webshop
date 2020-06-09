package com.bianyiit.util;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

//Druid连接池的工具类
/*
 * 1.获取连接池对象
 * 2.获取连接对象
 * 3.归还连接对象的方法
 * 4.静态代码块(因为在创建连接池对象之前就需要加载好配置文件中的所有信息)
 * */
public class DruidUtils {
    private static DataSource ds;
    //用来加载配置文件并创建好一个连接池对象
    static {
        Properties ps=new Properties();
        try {
            ps.load(DruidUtils.class.getClassLoader().getResourceAsStream("druid.properties"));
            ds = DruidDataSourceFactory.createDataSource(ps);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //获取连接对象的方法
    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
    //有没有必要提供一个获取连接池对象的方法？？---框架中可能只需要连接池对象，不需要连接对象
    public static DataSource getDataSource(){
        return ds;
    }
    //归还连接对象的方法
    //有一种是有结果集的连接（查询），
    public static void close(PreparedStatement psmt, Connection con, ResultSet rs){
        try {
            if(psmt!=null){
                psmt.close();
                psmt=null;
            }
            if(con!=null){
                con.close();
                con=null;
            }
            if(rs!=null){
                rs.close();
                rs=null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //有一种没有结果集的连接（增删改）
    public static void close(PreparedStatement psmt, Connection con){
        try {
            if(psmt!=null){
                psmt.close();
                psmt=null;
            }
            if(con!=null){
                con.close();
                con=null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
