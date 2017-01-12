package com.dao;

import com.bean.CourseBean;
import com.bean.TeacherBean;
import com.bean.WorkBean;
import com.core.ConnDB;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by recycle on 12/16 0016.
 */
public class CourseDao {
    public ArrayList<CourseBean> queryAll() throws SQLException {
        ConnDB connDB = new ConnDB();
        String sql = "select * from course";
        ResultSet rs = connDB.executeQuery(sql);
        ArrayList<CourseBean> courseBeanArrayList = new ArrayList<>();
        while (rs.next()) {
            CourseBean courseBean = new CourseBean();
            courseBean.setIdCourse(rs.getInt(1));
            courseBean.setCourseName(rs.getString(2));
            courseBeanArrayList.add(courseBean);
        }
        connDB.close();
        return courseBeanArrayList;
    }

    public int insertRow(CourseBean courseBean) {
        ConnDB connDB = new ConnDB();
        String sql = "insert into `course` (`courseName`) values ('" + courseBean.getCourseName() + "')";
        int result = connDB.executeUpdate(sql);
        connDB.close();
        return result;
    }

    // 删除 table course 中的一行，删除 table teacher 和 table work 中的相关行
    public void deleteRow(CourseBean courseBean) throws SQLException {
        ConnDB connDB = new ConnDB();
        //删除 table course 中的一行
        connDB.executeUpdate("delete from `course` where `idcourse`=" + courseBean.getIdCourse());
        connDB.close();

        //删除 table teacher 中的相关行
        ResultSet rs = connDB.executeQuery("select `idteacher` from `teacher` where `idCourse`=" + courseBean.getIdCourse());
        while (rs.next()) {
            TeacherBean teacherBean = new TeacherBean();
            teacherBean.setIdTeacher(rs.getInt(1));
            new TeacherDao().deleteRow(teacherBean);
        }
        connDB.close();

        //删除 table work 中的相关行
        rs = connDB.executeQuery("select `idwork` from `work` where `idCourse`=" + courseBean.getIdCourse());
        while (rs.next()) {
            WorkBean workBean = new WorkBean();
            workBean.setIdWork(rs.getInt(1));
            new WorkDao().deleteRow(workBean);
        }
        connDB.close();
    }

    public String courseName(int idCourse) throws SQLException {
        ConnDB connDB = new ConnDB();
        String courseName = null;
        ResultSet rs = connDB.executeQuery("select * from `course`");
        while (rs.next()) {
            if(idCourse == rs.getInt(1)) {
                courseName = rs.getString(2);
            }
        }
        connDB.close();
        return courseName;
    }

}
