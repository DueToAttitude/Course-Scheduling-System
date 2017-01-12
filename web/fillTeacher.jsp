<%@ page import="java.util.ArrayList" %>
<%@ page import="com.bean.GroupBean" %>
<%@ page import="com.bean.WorkBean" %>
<%@ page import="com.bean.TeacherBean" %>
<%@ page import="com.core.ConnDB" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="com.dao.*" %><%--
  Created by IntelliJ IDEA.
  User: recycle
  Date: 12/23 0023
  Time: 20:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>安排教师</title>
    <!-- Loading Bootstrap -->
    <link href="css/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <!-- Loading Flat UI -->
    <link href="css/flat-ui.css" rel="stylesheet">
    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="js/vendor/jquery.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="js/flat-ui.min.js"></script>
    <script src="js/application.js"></script>

    <script src="js/layer/layer.js"></script>
    <!-- icon -->
    <link rel = "icon" href = "img/favicon.ico" type = "image/x-icon">
    <link rel = "Shortcut Icon" href = "img/favicon.ico" type = "image/x-icon">

    <script type="text/javascript" language="JavaScript">
        function fillTeacher(selectElement) {
            var idTeacher = selectElement.value;
            var idWorkNode = selectElement.parentNode.parentNode;
            var idWork = idWorkNode.getAttribute("value");
            $.ajax({
                type:"post",
                url:"schedule?page=fillTeacher&idWork=" + encodeURI(idWork) + "&idTeacher=" + encodeURI(idTeacher),
                dataType:"json",
                success:function (data) {
                    if(data.state == "3") {
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
        function formsubmit(obj) {
            obj.parentNode.parentNode.submit()
        }
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
        function setting() {
            layer.prompt({title: '设置一天的课时', formType: 2}, function(text, index){
                layer.close(index);
                $.ajax({
                    type:"post",
                    url:"setting?num=" + encodeURI(text),
                    dataType:"json",
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

    ArrayList<WorkBean> workBeanArrayList = new ArrayList<>();
    String idGroup = request.getParameter("idGroup");
    if(idGroup == null || "".equals(idGroup) || "0".equals(idGroup)) {
        workBeanArrayList = new WorkDao().queryAll();
        idGroup = "0";
    }
    else {
        WorkBean workBean = new WorkBean();
        workBean.setIdGroup(Integer.parseInt(idGroup));
        workBeanArrayList = new WorkDao().queryByGroup(workBean);
    }
%>
<div class="container">
    <div class="row page-header" style="margin-top: 10px;">
        <div class="col-md-6 col-md-offset-1">
            <div>
                <h5>安排教师</h5>
            </div>
        </div>
        <div style="margin-top: 2px" class="col-md-5">
            <form style="margin:15px 0px 0px" name="group" id="groupSelect" action="fillTeacher.jsp">
                <div class="control-group">
                    <label for="selectGroup">班级： </label>
                    <select data-toggle="select" style="background-color: inherit;border: 2px" class="select select-default select-sm" id="selectGroup" name="idGroup" onchange="formsubmit(this);">
                        <%
                            if(idGroup.equals("0")) {
                        %>
                        <option value="0" selected="true">全部</option>
                        <%
                        }
                        else {
                        %>
                        <option value="0">全部</option>
                        <%
                            }
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
    <div class="row jumbotron">
        <div class="col-md-3"></div>
        <div class="col-md-6">
            <div class="row">
                <table class="table-striped text-center" style="width:100%">
                    <tr><td>班级</td><td>课程</td><td>课时</td><td>教师</td></tr>
                    <%
                        int i = 0;
                        for(WorkBean workBean : workBeanArrayList) {
                            ConnDB connDB = new ConnDB();
                            int idTeacher = 0;
                            ResultSet rs = connDB.executeQuery("select `idTeacher` from `schedule` where `idWork`=" + workBean.getIdWork());
                            if(rs.next()) {
                                idTeacher = rs.getInt(1);
                            }
                            i++;
                    %>
                    <tr class="<%=i%>" value="<%=workBean.getIdWork()%>">
                        <td><%=new GroupDao().groupName(workBean.getIdGroup())%></td>
                        <td><%=new CourseDao().courseName(workBean.getIdCourse())%></td>
                        <td><%=workBean.getSize()%></td>
                        <td id="selectTeacher">
                            <select style="margin: 5px;color: inherit" class="btn btn-default btn-xs" name="idTeacher" id="<%=i%>" onchange="fillTeacher(this);">
                                <%
                                    if(idTeacher == 0) {
                                        %>
                                <option value="0" selected="true">--无--</option>
                                <%
                                    }
                                    else {
                                        %>
                                <option value="0">--无--</option>
                                <%
                                    }
                                    ArrayList<TeacherBean> teacherBeanArrayList = new TeacherDao().queryByCourse(workBean.getIdCourse());
                                    for(TeacherBean teacherBean : teacherBeanArrayList) {
                                        if(teacherBean.getIdTeacher() == idTeacher) {
                                            %>
                                <option value="<%=teacherBean.getIdTeacher()%>" selected="true"><%=teacherBean.getTeacherName()%></option>
                                <%
                                        }
                                        else {
                                            %>
                                <option value="<%=teacherBean.getIdTeacher()%>"><%=teacherBean.getTeacherName()%></option>
                                <%
                                        }
                                    }
                                %>
                            </select>
                        </td>
                    </tr>
                    <%
                        }
                    %>
                </table>
            </div>
        </div>
        <div class="col-md-3"></div>
    </div>
</div>
</body>
</html>
