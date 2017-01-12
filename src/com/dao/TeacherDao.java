package com.dao;


import com.bean.ScheduleBean;
import com.bean.TeacherBean;
import com.core.ConnDB;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by recycle on 12/16 0016.
 */
public class TeacherDao {
    public ArrayList<TeacherBean> queryAll() throws SQLException {
        ConnDB connDB = new ConnDB();
        String sql = "select * from `teacher`";
        ResultSet rs = connDB.executeQuery(sql);
        ArrayList<TeacherBean> teacherBeanArrayList = new ArrayList<>();

        while (rs.next()) {
            TeacherBean teacherBean = new TeacherBean();
            teacherBean.setIdTeacher(rs.getInt(1));
            teacherBean.setTeacherName(rs.getString(2));
            teacherBean.setIdCourse(rs.getInt(3));
            teacherBean.setTeacherScheduled(rs.getInt(4));
            teacherBeanArrayList.add(teacherBean);
        }
        connDB.close();
        return teacherBeanArrayList;
    }

    public ArrayList<TeacherBean> queryByCourse(int idCourse) throws SQLException {
        ConnDB connDB = new ConnDB();
        String sql = "select * from `teacher` where `idCourse`=" + idCourse;
        ResultSet rs = connDB.executeQuery(sql);
        ArrayList<TeacherBean> teacherBeanArrayList = new ArrayList<>();

        while (rs.next()) {
            TeacherBean teacherBean = new TeacherBean();
            teacherBean.setIdTeacher(rs.getInt(1));
            teacherBean.setTeacherName(rs.getString(2));
            teacherBean.setIdCourse(rs.getInt(3));
            teacherBean.setTeacherScheduled(rs.getInt(4));
            teacherBeanArrayList.add(teacherBean);
        }
        connDB.close();
        return teacherBeanArrayList;
    }

    public int insertRow(TeacherBean teacherBean) {
        if(teacherBean.getIdCourse() == 0)
            return 0;
        ConnDB connDB = new ConnDB();
        String sql = "insert into `teacher` (`teacherName`,`idCourse`,`teacherScheduled`) values ('" + teacherBean.getTeacherName() + "','" + teacherBean.getIdCourse() + "',0)";
        int result = connDB.executeUpdate(sql);
        connDB.close();
        return result;
    }

    public void deleteRow(TeacherBean teacherBean) throws SQLException {
        ConnDB connDB = new ConnDB();
        connDB.executeUpdate("delete from `teacher` where `idteacher`=" + teacherBean.getIdTeacher());
        connDB.close();

        connDB.executeUpdate("update `schedule` set `idTeacher`=0 where `idTeacher`=" + teacherBean.getIdTeacher());
        connDB.close();
    }

    public String teacherName(int idTeacher) throws SQLException {
        ConnDB connDB = new ConnDB();
        String teacherName = null;
        ResultSet rs = connDB.executeQuery("select * from `teacher`");
        while (rs.next()) {
            if(idTeacher == rs.getInt(1)) {
                teacherName = rs.getString(2);
            }
        }
        connDB.close();
        return teacherName;
    }
}


