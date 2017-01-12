package com.dao;

import com.bean.GroupBean;
import com.bean.WorkBean;
import com.core.ConnDB;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by recycle on 12/17 0017.
 */
public class GroupDao {
    public ArrayList<GroupBean> queryAll() throws SQLException {
        ConnDB connDB = new ConnDB();
        String sql = "select * from `group`";
        ResultSet rs = connDB.executeQuery(sql);
        ArrayList<GroupBean> groupBeanArrayList = new ArrayList<>();

        while (rs.next()) {
            GroupBean groupBean =new GroupBean();
            groupBean.setIdGroup(rs.getInt(1));
            groupBean.setGroupName(rs.getString(2));
            groupBean.setGroupScheduled(rs.getInt(3));
            groupBeanArrayList.add(groupBean);
        }
        connDB.close();
        return groupBeanArrayList;
    }

    public int insertRow(GroupBean groupBean) {
        ConnDB connDB = new ConnDB();
        String sql = "insert into `group` (`groupName`,`groupScheduled`) values ('" + groupBean.getGroupName() + "',0)";
        int result = connDB.executeUpdate(sql);
        connDB.close();
        return result;
    }

    //删除table group 一行，删除 table work 中相关行
    public void deleteRow(GroupBean groupBean) throws SQLException {
        ConnDB connDB = new ConnDB();
        ResultSet rs;

        rs = connDB.executeQuery("select `idwork` from `work` where `idGroup`=" + groupBean.getIdGroup());
        while (rs.next()) {
            WorkBean workBean = new WorkBean();
            workBean.setIdWork(rs.getInt(1));
            new WorkDao().deleteRow(workBean);
        }
        connDB.close();

        connDB.executeUpdate("delete from `group` where `idgroup`=" + groupBean.getIdGroup());
        connDB.close();
    }

    public String groupName(int idGroup) throws SQLException {
        ConnDB connDB = new ConnDB();
        String groupName = null;
        ResultSet rs = connDB.executeQuery("select * from `group`");
        while (rs.next()) {
            if(idGroup == rs.getInt(1)) {
                groupName = rs.getString(2);
            }
        }
        connDB.close();
        return groupName;
    }
}
