package com.core;

import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * Created by recycle on 12/15 0015.
 */
public class ConnDB {
    private String driver = "com.mysql.jdbc.Driver";
    private String user = "root";
    private String password = "122474";
    private String database = "paike_v_5.0";
    private String url = "jdbc:mysql://localhost:3306/paike_v_5.0?user=root&password=122474&useSSL=false";
    private String propFileName = "/com/info.properties";
    private static Properties properties = new Properties();

    public Connection conn = null;
    public Statement stmt = null;
    public ResultSet rs = null;

    public ConnDB(){
        try {
            InputStream in=getClass().getResourceAsStream(propFileName);
            properties.load(in);									//通过输入流对象加载Properties文件
            user = properties.getProperty("user");
            password = properties.getProperty("password");
            database = properties.getProperty("database");
            url = "jdbc:mysql://localhost:3306/" + database + "?user=" + user + "&password=" + password + "&useSSL=false";
        }
        catch (Exception e) {
            e.printStackTrace();		//输出异常信息
        }
    }

    public Connection getConnection() {
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(conn == null) {
            System.out.println("错误报告：连接数据库失败");
        }
        return conn;
    }

    public ResultSet executeQuery(String sql) {
        try {
            conn = getConnection();
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            rs = stmt.executeQuery(sql);
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return rs;
    }

    public int executeUpdate(String sql) {
        int result = 0;
        try {
            conn = getConnection();					//调用getConnection()方法构造Connection对象的一个实例conn
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            result = stmt.executeUpdate(sql);		//执行更新操作
        } catch (SQLException ex) {
            result = 0;
        }
        return result;
    }

    public void close() {
        try {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }
}
