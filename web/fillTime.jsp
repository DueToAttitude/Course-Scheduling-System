<%@ page import="com.bean.GroupBean" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.bean.ScheduleBean" %>
<%@ page import="com.dao.*" %>
<%@ page import="com.core.SettingProp" %><%--
  Created by IntelliJ IDEA.
  User: recycle
  Date: 12/22 0022
  Time: 15:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE HTML>
<html>
<head>
    <!-- Loading Bootstrap -->
    <link href="css/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <!-- Loading Flat UI -->
    <link href="css/flat-ui.css" rel="stylesheet">
    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="js/vendor/jquery.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="js/flat-ui.min.js"></script>
    <script src="js/application.js"></script>
    <!-- icon -->
    <link rel = "icon" href = "img/favicon.ico" type = "image/x-icon">
    <link rel = "Shortcut Icon" href = "img/favicon.ico" type = "image/x-icon">

    <script type="text/javascript" src="js/jquery-3.1.1.min.js"></script>
    <script type="text/javascript" src="js/myJs.js"></script>
    <script type="text/javascript" src="js/layer/layer.js"></script>
    <style type="text/css">
        div.block {background:#ffffc0;border-radius:5px;width:120px; height:auto;line-height:1.5;border:1px solid black;padding: 2px;margin: 10px 0px}
        table.box {height:50px; border:2px solid black}
        div.box {width:150px; height:100px; border:2px solid black;margin-left: -10px;border-radius: 5px}
        .alert {padding:0px;margin:0px;text-align: center}
        .alert-success,.alert-danger,.alert-warning,.alert-info {padding:3px;margin-top: 5px}
    </style>
    <script type="text/javascript">
        function autoFillRemain() {
//            alert("开始");
            $.ajax({
                type:"post",
                url:"schedule?page=fillAll",
                dataType:"json",
                success:function (data) {
                    if(data.state == "12")  {
                        layer.msg('操作成功！',{icon:1,time:1000},function () {
                            location.reload();
                        });
                    }
                    else  {
                        layer.msg('出现时间冲突',{icon:0,time:1000},function () {
                            location.reload();
                        });
                    }
                }
            })
        }

        function allowDrop(ev)
        {
            ev.preventDefault();
        }

        function drag(ev)
        {
            ev.dataTransfer.setData("Text",ev.target.id);
        }

        function drop(ev)
        {
            ev.preventDefault();
            var data=ev.dataTransfer.getData("Text");
            var idSchedule = document.getElementById(data).getAttribute("schedule");
            var time = ev.target.getAttribute("value");
            if(time == undefined || time == 0) {
                document.getElementById(data).parentNode.removeChild(document.getElementById(data));
                location.reload();
            }
            else {
                ev.target.appendChild(document.getElementById(data));
            }
            $.ajax({
                type:"post",
                url:"schedule?page=fillTime&idSchedule=" + encodeURI(idSchedule) + "&time=" + encodeURI(time),
                dataType:"json",
                success:function (data) {
                    if(data.state == "1") {
                        layer.msg('时间冲突',{icon:0,time:500},function () {
                            location.reload();
                        });
//                        $(".alert").html("<div class='alert alert-warning'>时间冲突</div>");
//                        $(".alert").show();
                    }
                    else if(data.state == "4") {
                        layer.msg('操作成功！',{icon:1});
//                        $(".alert").hide();
//                        $(".alert").html("<div class='alert alert-success'>成功</div>");
//                        $(".alert").show();
//                        $(".alert").fadeOut(1000);
                    }
                    else {
                        layer.msg('异常！',{icon:2,time:2000},function () {
                            location.reload();
                        });
//                        $(".alert").html("<div class='alert alert-danger'>异常</div>");
//                        $(".alert").show();
                    }
                }
            })
        }

        function setting() {
            layer.prompt({title: '设置一天的课时', formType: 2}, function(text, index){
                layer.close(index);
                $.ajax({
                    type:"post",
                    url:"setting?num=" + encodeURI(text),dataType:"json",
                    success:function (data) {
                        layer.msg(data.state);
                    }
                });
            });
        }
    </script>
</head>
<body>
<%@include file="banner.jsp"%>
<!-- navbar -->
<%
    if(new UserDao().userType(loginName) == 1) {
%>
<style>
    .navbar-static-top {
        margin-bottom: 19px;
    }
</style>
<div class="navbar navbar-default navbar-static-top" role="navigation">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                <span class="sr-only">Toggle navigation</span>
            </button>
            <a class="navbar-brand" href="#">Welcome</a>
        </div>
        <div class="navbar-collapse collapse">
            <ul class="nav navbar-nav">
                <li><a href="main.jsp"><span class="fui-home"></span></a></li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">基础信息 <b class="caret"></b></a>
                    <ul class="dropdown-menu">
                        <li><a href="groupInfo.jsp">班级</a></li>
                        <li><a href="roomInfo.jsp">教室</a></li>
                        <li><a href="courseInfo.jsp">课程</a></li>
                        <li><a href="teacherInfo.jsp">教师</a></li>
                        <li><a href="workInfo.jsp">任务</a></li>
                    </ul>
                </li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">排课 <b class="caret"></b></a>
                    <ul class="dropdown-menu">
                        <li><a href="fillTeacher.jsp">安排教师</a></li>
                        <li><a href="fillRoom.jsp">安排教室</a></li>
                        <li><a href="fillTime.jsp">安排时间</a></li>
                    </ul>
                </li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">课表 <b class="caret"></b></a>
                    <ul class="dropdown-menu">
                        <li><a href="groupSchedule.jsp">班级课表</a></li>
                        <li><a href="roomSchedule.jsp">教室课表</a></li>
                        <li><a href="teacherSchedule.jsp">教师课表</a></li>
                    </ul>
                </li>
                <li><a class="btn" onclick="autoFillRemain();">自动排课</a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right">

                <li><a onclick="setting();"><span class="fui-gear"></span></a></li>                 <li class="active"><a href="userCtrl?action=logout"><b>管理员</b> <%=loginName%></a></li>
            </ul>
        </div><!--/.nav-collapse -->
    </div>
</div>
<%
}
else {
    String URI = request.getRequestURI();
    String[] URIs = URI.split("/");
    String path = URIs[URIs.length-1];
    System.out.println(path.trim());
    if("errorPage.jsp".equals(path.trim()) || "main.jsp".equals(path.trim()) || "teacherSchedule.jsp".equals(path.trim()) ||
            "roomSchedule.jsp".equals(path.trim()) || "groupSchedule.jsp".equals(path.trim())) {
%>
<div class="navbar navbar-default navbar-static-top" role="navigation">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                <span class="sr-only">Toggle navigation</span>
            </button>
            <a class="navbar-brand" href="#">Welcome</a>
        </div>
        <div class="navbar-collapse collapse">
            <ul class="nav navbar-nav">
                <li><a href="main.jsp"><span class="fui-home"></span></a></li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">课表 <b class="caret"></b></a>
                    <ul class="dropdown-menu">
                        <li><a href="groupSchedule.jsp">班级课表</a></li>
                        <li><a href="roomSchedule.jsp">教室课表</a></li>
                        <li><a href="teacherSchedule.jsp">教师课表</a></li>
                    </ul>
                </li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li class="active"><a href="./"><b>用户</b> <%=loginName%></a></li>
                <li><a onclick="setting();"><span class="fui-gear"></span></a></li>                 <li class="active"><a href="userCtrl?action=logout"><b>管理员</b> <%=loginName%></a></li>
            </ul>
        </div><!--/.nav-collapse -->
    </div>
</div>
<%
        }
        else {
            response.sendRedirect("main.jsp");
        }
    }
%>
<!-- /navbar -->
<%
    ArrayList<GroupBean> groupBeanArrayList = new GroupDao().queryAll();
%>
<%
    ArrayList<ScheduleBean> scheduleBeanArrayList1 = new ArrayList<>();
    ArrayList<ScheduleBean> scheduleBeanArrayList2 = new ArrayList<>();
    String idGroup = request.getParameter("idGroup");
    if(idGroup == null || "".equals(idGroup)) {
        idGroup = (groupBeanArrayList.size() > 0) ? Integer.toString(groupBeanArrayList.get(0).getIdGroup()) : "0";
    }
    scheduleBeanArrayList1 = new ScheduleDao().queryByGroup1(idGroup);
    scheduleBeanArrayList2 = new ScheduleDao().queryByGroup2(idGroup);

%>
<div class="container">
    <div class="row page-header" style="margin-top: 10px;">
        <div class="col-md-6 col-md-offset-1">
        <div>
            <h5>安排时间</h5>
        </div>
        </div>
        <div style="margin-top: 2px" class="col-md-5">
            <form style="margin:15px 0px 0px" name="group" id="groupSelect">
                <div class="control-group">
                    <label for="selectGroup">班级： </label>
                    <select data-toggle="select" style="background-color: inherit;border: 2px" class="select select-default select-sm" id="selectGroup" name="idGroup" onchange="formsubmit(this);">
                        <%
                            for(GroupBean groupBean : groupBeanArrayList) {
                                if(idGroup.equals(Integer.toString(groupBean.getIdGroup()))) {
                        %>
                        <option value="<%=groupBean.getIdGroup()%>" selected="true"><%=groupBean.getGroupName()%></option>
                        <%
                        }
                        else {
                        %>
                        <option value="<%=groupBean.getIdGroup()%>"><%=groupBean.getGroupName()%></option>
                        <%
                                }
                            }
                        %>
                    </select>
                </div>
            </form>
        </div>

    </div>
    <div class="row">
        <div class="col-md-2">
            <div class="box text-center" ondrop="drop(event)" ondragover="allowDrop(event)" value="0"><small>RecycleBin</small></div>
            <%
                int k = 0;
                for(ScheduleBean scheduleBean : scheduleBeanArrayList2) {
                    k++;
            %>
            <div class="block row" draggable="true" ondragstart="drag(event)" id="<%=20+k%>" schedule="<%=scheduleBean.getIdSchedule()%>">
                <%=new CourseDao().courseName(scheduleBean.getIdCourse())%><br>
                <%=new TeacherDao().teacherName(scheduleBean.getIdTeacher())%>
            </div>
            <%
                }
            %>
        </div>
        <div class="col-md-10 jumbotron">
            <table class="row table table-bordered">
                <thead><td>&nbsp;&nbsp;</td><td>Monday</td><td>Tuesday</td><td>Wednesday</td><td>Thirsday</td><td>Friday</td></thead>
                <%
                    for (int i = 0; i < new SettingProp().getNum(); i++) {
                %>
                <tr>
                    <td>第<%=i+1%>节</td>
                    <%
                        for (int j = 0; j < 5; j++) {
                    %>
                    <td class="box" ondrop="drop(event)" ondragover="allowDrop(event)" value="<%=i*5+j+1%>">
                        <%
                            for(ScheduleBean scheduleBean : scheduleBeanArrayList1) {
                                if(scheduleBean.getTime() == i*5+j+1) {

                        %>
                        <div class="row block" draggable="true" id="<%=i*5+j+1%>" ondragstart="drag(event)" schedule="<%=scheduleBean.getIdSchedule()%>">
                            <%=new CourseDao().courseName(scheduleBean.getIdCourse())%><br>
                            <%=new TeacherDao().teacherName(scheduleBean.getIdTeacher())%>
                        </div>
                        <%
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
        </div>
    </div>
</div>
</body>
</html>