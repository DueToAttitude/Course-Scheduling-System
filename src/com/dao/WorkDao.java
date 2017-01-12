package com.dao;
/*
更新其他表的schedule的时候可以判断，原来的schedule与size想作用是否大于0，否则不会变，因为unsigned
 */
import com.bean.ScheduleBean;
import com.bean.WorkBean;
import com.core.ConnDB;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by recycle on 12/16 0016.
 */
public class WorkDao {
    //查询全部
    public ArrayList<WorkBean> queryAll() throws SQLException {
        ArrayList<WorkBean> workBeanArrayList = new ArrayList<>();
        ConnDB connDB = new ConnDB();
        String sql = "select * from `work`";
        ResultSet rs = connDB.executeQuery(sql);

        while (rs.next()) {
            WorkBean workBean = new WorkBean();
            workBean.setIdWork(rs.getInt(1));
            workBean.setIdGroup(rs.getInt(2));
            workBean.setIdCourse(rs.getInt(3));
            workBean.setSize(rs.getInt(4));
            workBeanArrayList.add(workBean);
        }
        connDB.close();
        return workBeanArrayList;
    }

    //按班级查询
    public ArrayList<WorkBean> queryByGroup(WorkBean workBean1) throws SQLException {
        ArrayList<WorkBean> workBeanArrayList = new ArrayList<>();
        ConnDB connDB = new ConnDB();
        ResultSet rs = connDB.executeQuery("select * from `work` where `idGroup`=" + workBean1.getIdGroup());
        while (rs.next()) {
            WorkBean workBean = new WorkBean();
            workBean.setIdWork(rs.getInt(1));
            workBean.setIdGroup(rs.getInt(2));
            workBean.setIdCourse(rs.getInt(3));
            workBean.setSize(rs.getInt(4));
            workBeanArrayList.add(workBean);
        }
        connDB.close();
        return workBeanArrayList;
    }

    //输入班级、课程、课时，不能为0
    public void insertRow(WorkBean workBean) throws SQLException {
        if (workBean.getIdGroup() <= 0 || workBean.getIdCourse() <= 0 || workBean.getSize() <= 0) {
            System.out.println("WorkDao.insertRow------------失败：任务参数错误-------------");
            return ;
        }
        ConnDB connDB = new ConnDB();
        String sql = "insert into `work` (`idGroup`,`idCourse`,`size`) values (" +
                workBean.getIdGroup() + "," + workBean.getIdCourse()  + "," + workBean.getSize() + ")";
        int result = connDB.executeUpdate(sql);
        connDB.close();

        if(result > 0) {
            connDB.executeUpdate("update `group` set `groupScheduled`=`groupScheduled`+" + workBean.getSize() + " where `idgroup`=" + workBean.getIdGroup());
            connDB.close();

            ResultSet rs = connDB.executeQuery("select `idwork` from `work` order by `idwork` desc");
            if(rs.next()) {
                workBean.setIdWork(rs.getInt(1));
            }

            for (int i = 0; i < workBean.getSize(); i++) {
                connDB.executeUpdate("insert into `schedule` (`idWork`,`idGroup`,`idCourse`,`idTeacher`,`idRoom`,`time`)" +
                        " VALUES (" + workBean.getIdWork() + "," + workBean.getIdGroup() + "," + workBean.getIdCourse() + ",0,0,0)");
                connDB.close();
            }
        }
    }

    //删除 table schedule 相关行，修改 teacher.teacherScheduled 和 group.groupScheduled
    public int deleteRow(WorkBean workBean) throws SQLException {
        ConnDB connDB = new ConnDB();
        ResultSet rs;
        int result = 0;
        int idGroup = 0;
        int size = 0;

        //删除 table schedule 中相关行
        rs = connDB.executeQuery("select `idschedule` from `schedule` where `idWork`=" + workBean.getIdWork());
        while (rs.next()) {
            ScheduleBean scheduleBean = new ScheduleBean();
            scheduleBean.setIdSchedule(rs.getInt(1));
            new ScheduleDao().deleteRow(scheduleBean);
        }
        connDB.close();

        rs = connDB.executeQuery("select `size`,`idGroup` from `work` where `idwork`=" + workBean.getIdWork());
        if(rs.next()) {
            size = rs.getInt(1);
            idGroup = rs.getInt(2);
        }
        connDB.close();

        //修改 table group 中的 column groupScheduled
        if (idGroup > 0) {
            connDB.executeUpdate("update `group` set `groupScheduled`=if(`groupScheduled`<" + size + ",0,`groupScheduled`-" + size + ") where `idgroup`=" + idGroup);
            connDB.close();
        }
        String sql = "delete from `work` where `idwork`=" + workBean.getIdWork();
        result = connDB.executeUpdate(sql);
        connDB.close();
        return  result;
    }
}

