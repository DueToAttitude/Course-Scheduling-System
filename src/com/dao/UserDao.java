package com.dao;

import com.bean.UserBean;
import com.core.ConnDB;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by recycle on 12/15 0015.
 */
public class UserDao {
    ConnDB connDB = null;
    public UserDao() {
        connDB = new ConnDB();
    }

    //登录时对一行数据的查询
    public boolean queryOne(UserBean userBean) {
        if(userBean.getUserName() == null || userBean.getUserPassword() == null)
            return false;
        String sql = "select * from user where userName='" + userBean.getUserName() +
                "' and userPassword='" + userBean.getUserPassword() + "'";
        ResultSet rs = connDB.executeQuery(sql);
        try {
            boolean flag = rs.next();
            connDB.close();
            return flag;
        } catch (SQLException e) {
            e.printStackTrace();
            connDB.close();
            return false;
        }
    }

    //注册时insert一行数据
    public int insert(UserBean userBean) {
        String sql = "INSERT INTO user (`userName`,`userPassword`,`userType`) VALUES ('" +
                userBean.getUserName() + "','" + userBean.getUserPassword() + "'," + userBean.getUserType() + ")";

        int result = connDB.executeUpdate(sql);
        connDB.close();
        return result;
    }

    //查询类型
    public int userType(String userName) throws SQLException {
        int userType = 0;
        ResultSet rs = connDB.executeQuery("select `userType` from `user` where `userName`='" + userName + "'");
        if(rs.next()) {
            userType = rs.getInt(1);
        }
        return userType;
    }
}
