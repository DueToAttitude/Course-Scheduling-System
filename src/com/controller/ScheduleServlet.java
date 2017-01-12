package com.controller;

import com.bean.ScheduleBean;
import com.bean.WorkBean;
import com.dao.ScheduleDao;
import com.dao.WorkDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.sql.SQLException;

/**
 * Created by recycle on 12/23 0023.
 */
@WebServlet(name = "schedule", urlPatterns = "/schedule")
public class ScheduleServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page = URLDecoder.decode(request.getParameter("page"), "utf-8");
        ScheduleBean scheduleBean = new ScheduleBean();
        int state = 0;
        PrintWriter out = response.getWriter();
        switch (page) {
            case "fillTeacher" :
                String idWork = request.getParameter("idWork");
                String idTeacher = request.getParameter("idTeacher");
                idWork = URLDecoder.decode(idWork, "utf-8");
                idTeacher = URLDecoder.decode(idTeacher, "utf-8");

                scheduleBean = new ScheduleBean();
                scheduleBean.setIdWork(Integer.parseInt(idWork));
                scheduleBean.setIdTeacher(Integer.parseInt(idTeacher));
                scheduleBean.setTime(0);
                scheduleBean.setIdRoom(0);
                System.out.println("idTeacher = " + Integer.parseInt(idTeacher) + "  idWork = " + Integer.parseInt(idWork));
                if(Integer.parseInt(idTeacher) == 0) {
                    try {
                        scheduleBean.setTime(1);
                        scheduleBean.setIdRoom(1);
                        new ScheduleDao().unfillRow(scheduleBean);
                        out.print("{\"state\":\"4\"}");
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    try {
                        state = new ScheduleDao().fillRow(scheduleBean);
                        if(state == 0)
                            out.print("{\"state\":\"0\"}");
                        else if(state == 3)
                            out.print("{\"state\":\"3\"}");
                        else if(state == 4)
                            out.print("{\"state\":\"4\"}");
                        System.out.println("ScheduleServlet:state = " + state);
                    } catch (SQLException e) {
                        e.printStackTrace();
                        out.print("{\"state\":\"failed\"}");
                    }
                }
                break;
            case "fillRoom" :
                String idSchedule = URLDecoder.decode(request.getParameter("idSchedule"), "utf-8");
                String idRoom = URLDecoder.decode(request.getParameter("idRoom"), "utf-8");
                scheduleBean = new ScheduleBean();
                scheduleBean.setIdSchedule(Integer.parseInt(idSchedule));
                scheduleBean.setIdRoom(Integer.parseInt(idRoom));
                scheduleBean.setTime(0);
                scheduleBean.setIdTeacher(0);
                System.out.println("idSchedule = " + Integer.parseInt(idSchedule) + "  idRoom = " + Integer.parseInt(idRoom));
                state = 0;
                if(Integer.parseInt(idRoom) == 0) {
                    try {
                        scheduleBean.setIdTeacher(1);
                        scheduleBean.setTime(1);
                        new ScheduleDao().unfillRow(scheduleBean);
                        out.print("{\"state\":\"4\"}");
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    try {
                        state = new ScheduleDao().fillRow(scheduleBean);
                        if(state == 0)
                            out.print("{\"state\":\"0\"}");
                        else if(state == 2)
                            out.print("{\"state\":\"2\"}");
                        else if(state == 4)
                            out.print("{\"state\":\"4\"}");
                        System.out.println("ScheduleServlet:state = " + state);
                    } catch (SQLException e) {
                        out.print("{\"state\":\"failed\"}");
                    }
                }
                break;
            case "fillTime" :
                String idSchedule1 = URLDecoder.decode(request.getParameter("idSchedule"), "utf-8");
                String time = URLDecoder.decode(request.getParameter("time"), "utf-8");
                scheduleBean = new ScheduleBean();
                scheduleBean.setIdSchedule(Integer.parseInt(idSchedule1));
                scheduleBean.setTime(Integer.parseInt(time));
                scheduleBean.setIdTeacher(0);
                scheduleBean.setIdRoom(0);
                System.out.println("idSchedule = " + Integer.parseInt(idSchedule1) + "  time = " + Integer.parseInt(time));
                state = 0;
                if(Integer.parseInt(time) == 0) {
                    try {
                        scheduleBean.setIdRoom(1);
                        scheduleBean.setIdTeacher(1);
                        new ScheduleDao().unfillRow(scheduleBean);
                        out.print("{\"state\":\"4\"}");
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    try {
                        state = new ScheduleDao().fillRow(scheduleBean);
                        out.print("{\"state\":\"" + state + "\"}");
                        System.out.println("ScheduleServlert:state = " + state);
                    } catch (SQLException e) {
                        out.print("{\"state\":\"failed\"}");
                    }
                }
                break;
            case "fillAll" :
                System.out.println("自动排课");
                ScheduleDao scheduleDao = new ScheduleDao();
                try {
                    int state1 = scheduleDao.fillTeacherRemain();
                    int state2 = scheduleDao.fillRoomRemain();
                    int state3 = scheduleDao.fillTimeRemain();
                    state = state1 + state2 + state3;
                    out.print("{\"state\":\"" + state + "\"}");
                    System.out.println("排课综合数：" + state + "  teacher : " + state1 + "  room : " + state2 + "  time : " + state3);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
