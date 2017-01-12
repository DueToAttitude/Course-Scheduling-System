<%@ page import="java.util.ArrayList" %>
<%@ page import="com.bean.ScheduleBean" %>
<%@ page import="com.dao.*" %>
<%@ page import="com.bean.GroupBean" %>
<%@ page import="com.bean.TeacherBean" %>
<%@ page import="com.bean.RoomBean" %>
<%@ page import="com.core.SettingProp" %><%--
  Created by IntelliJ IDEA.
  User: recycle
  Date: 12/25 0025
  Time: 16:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=gb2312"
         pageEncoding="gb2312"%>
<html>
<head>
    <title>Export to Excel</title>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
</head>
<body>
<%
    String way = request.getParameter("way");
    ArrayList<ScheduleBean> scheduleBeanArrayList = new ArrayList<>();
    ArrayList<GroupBean> groupBeanArrayList = new GroupDao().queryAll();
    ArrayList<TeacherBean> teacherBeanArrayList = new TeacherDao().queryAll();
    ArrayList<RoomBean> roomBeanArrayList = new RoomDao().queryAll();
    switch (way) {
        case "group":
            for (GroupBean groupBean : groupBeanArrayList) {
                scheduleBeanArrayList = new ScheduleDao().queryByGroup(Integer.toString(groupBean.getIdGroup()));
                %>
<table name="<%=groupBean.getGroupName()%>" border="2" width="100%">
    <tr><td>&nbsp;&nbsp;</td><td>Monday</td><td>Tuesday</td><td>Wednesday</td><td>Thirsday</td><td>Friday</td></tr>
    <%
        for (int i = 0; i < new SettingProp().getNum(); i++) {
    %>
    <tr>
        <td>第<%=i+1%>节</td>
        <%
            for (int j = 0; j < 5; j++) {
        %>
        <td>
            <%
                for (ScheduleBean scheduleBean : scheduleBeanArrayList) {
                    if(scheduleBean.getTime() == 5*i+j+1) {
                        out.print(new CourseDao().courseName(scheduleBean.getIdCourse()) + "<br>" + new TeacherDao().teacherName(scheduleBean.getIdTeacher()) +
                                "<br>" + new RoomDao().roomName(scheduleBean.getIdRoom()));
                    }
                }
            %>
        </td>
        <%
            }
        %>
    </tr>
    <%
        }
    %>
</table>
<%
            }
            break;
        case "teacher" :
            for(TeacherBean teacherBean : teacherBeanArrayList) {
                scheduleBeanArrayList = new ScheduleDao().queryByTeacher(Integer.toString(teacherBean.getIdTeacher()));
                %>
<table name="<%=teacherBean.getTeacherName()%>" border="2" width="100%">
    <tr><td>&nbsp;&nbsp;</td><td>Monday</td><td>Tuesday</td><td>Wednesday</td><td>Thirsday</td><td>Friday</td></tr>
    <%
        for (int i = 0; i < new SettingProp().getNum(); i++) {
    %>
    <tr>
        <td>第<%=i+1%>节</td>
        <%
            for (int j = 0; j < 5; j++) {
        %>
        <td>
            <%
                for (ScheduleBean scheduleBean : scheduleBeanArrayList) {
                    if(scheduleBean.getTime() == 5*i+j+1) {
                        out.print(new GroupDao().groupName(scheduleBean.getIdGroup()) + "<br>" + new CourseDao().courseName(scheduleBean.getIdCourse()) +
                                "<br>" + new RoomDao().roomName(scheduleBean.getIdRoom()));
                    }
                }
            %>
        </td>
        <%
            }
        %>
    </tr>
    <%
        }
    %>
</table>
<%
            }
            break;
        case "room" :
            for(RoomBean roomBean : roomBeanArrayList) {
                scheduleBeanArrayList = new ScheduleDao().queryByRoom(Integer.toString(roomBean.getIdRoom()));
                %>
<table name="<%=roomBean.getRoomName()%>" border="2" width="100%">
    <tr><td>&nbsp;&nbsp;</td><td>Monday</td><td>Tuesday</td><td>Wednesday</td><td>Thirsday</td><td>Friday</td></tr>
    <%
        for (int i = 0; i < new SettingProp().getNum(); i++) {
    %>
    <tr>
        <td>第<%=i+1%>节</td>
        <%
            for (int j = 0; j < 5; j++) {
        %>
        <td>
            <%
                for (ScheduleBean scheduleBean : scheduleBeanArrayList) {
                    if(scheduleBean.getTime() == 5*i+j+1) {
                        out.print(new CourseDao().courseName(scheduleBean.getIdCourse()) + "<br>" + new GroupDao().groupName(scheduleBean.getIdGroup()) +
                                "<br>" + new TeacherDao().teacherName(scheduleBean.getIdTeacher()));
                    }
                }
            %>
        </td>
        <%
            }
        %>
    </tr>
    <%
        }
    %>
</table>
<%
            }
            break;
    }
    response.setContentType("application/vnd.ms-word");
    response.setHeader("Content-Disposition", "inline; filename=课表.xls");
%>
</body>
</html>
