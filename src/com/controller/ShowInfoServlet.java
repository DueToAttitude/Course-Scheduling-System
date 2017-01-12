package com.controller;

import com.bean.*;
import com.dao.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by recycle on 12/22 0022.
 */
@WebServlet(name = "ShowInfoServlet", urlPatterns = "/showInfo")
public class ShowInfoServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String page = request.getParameter("page");
        if(action != null && "delete".equals(action)) {;
            switch (page) {
                case "groupInfo":
                    GroupBean groupBean = new GroupBean();
                    groupBean.setIdGroup(Integer.parseInt(request.getParameter("row")));
                    try {
                        new GroupDao().deleteRow(groupBean);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    response.sendRedirect("groupInfo.jsp");
                    break;
                case "teacherInfo":
                    TeacherBean teacherBean = new TeacherBean();
                    teacherBean.setIdTeacher(Integer.parseInt(request.getParameter("row")));
                    try {
                        new TeacherDao().deleteRow(teacherBean);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    response.sendRedirect("teacherInfo.jsp");
                    break;
                case "roomInfo":
                    RoomBean roomBean = new RoomBean();
                    roomBean.setIdRoom(Integer.parseInt(request.getParameter("row")));
                    try {
                        new RoomDao().deleteRow(roomBean);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    response.sendRedirect("roomInfo.jsp");
                    break;
                case "courseInfo":
                    CourseBean courseBean = new CourseBean();
                    courseBean.setIdCourse(Integer.parseInt(request.getParameter("row")));
                    try {
                        new CourseDao().deleteRow(courseBean);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    response.sendRedirect("courseInfo.jsp");
                    break;
                case "workInfo":
                    WorkBean workBean = new WorkBean();
                    workBean.setIdWork(Integer.parseInt(request.getParameter("row")));
                    try {
                        new WorkDao().deleteRow(workBean);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    response.sendRedirect("workInfo.jsp");
                    break;
                default:
                    response.sendRedirect("errorPage.jsp");
                    break;
            }
        }
        if(action != null && "insert".equals(action)) {
            switch (page) {
                case "groupInfo":
                    GroupBean groupBean = new GroupBean();
                    groupBean.setGroupName(request.getParameter("name"));
                    new GroupDao().insertRow(groupBean);
                    response.sendRedirect("groupInfo.jsp");
                    break;
                case "roomInfo":
                    RoomBean roomBean = new RoomBean();
                    roomBean.setRoomName(request.getParameter("name"));
                    new RoomDao().insertRow(roomBean);
                    response.sendRedirect("roomInfo.jsp");
                    break;
                case "courseInfo":
                    CourseBean courseBean = new CourseBean();
                    courseBean.setCourseName(request.getParameter("name"));
                    new CourseDao().insertRow(courseBean);
                    response.sendRedirect("courseInfo.jsp");
                    break;
                case "teacherInfo":
                    TeacherBean teacherBean = new TeacherBean();
                    teacherBean.setTeacherName(request.getParameter("name"));
                    teacherBean.setIdCourse(Integer.parseInt(request.getParameter("job")));
                    new TeacherDao().insertRow(teacherBean);
                    response.sendRedirect("teacherInfo.jsp");
                    break;
                case "workInfo":
                    WorkBean workBean = new WorkBean();
                    workBean.setIdCourse(Integer.parseInt(request.getParameter("courseName")));
                    workBean.setIdGroup(Integer.parseInt(request.getParameter("groupName")));
                    workBean.setSize(Integer.parseInt(request.getParameter("size")));
                    try {
                        new WorkDao().insertRow(workBean);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    response.sendRedirect("workInfo.jsp");
                    break;
                default:
                    response.sendRedirect("errorPage.jsp");
                    break;
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
