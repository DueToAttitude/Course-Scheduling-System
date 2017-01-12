<%@ page import="java.util.ArrayList" %>
<%@ page import="com.bean.GroupBean" %>
<%@ page import="com.dao.GroupDao" %>
<%@ page import="com.dao.UserDao" %><%--
  Created by IntelliJ IDEA.
  User: recycle
  Date: 12/17 0017
  Time: 14:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>班级信息</title>
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

    <script>
        function infosubmit() {
            var name = $("input#name").val();
//            alert(name);
            if(name != undefined && name != "") {
//                alert("submit");
                $("form").submit();
            }
            else {
                layer.msg('表单不能为空',{icon:2});
            }
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
                <li><a onclick="setting();"><span class="fui-gear"></span></a></li>           776336      <li class="active"><a href="userCtrl?action=logout"><b>管理员</b> <%=loginName%></a></li>
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
    ArrayList<GroupBean> groupList = new GroupDao().queryAll();
%>
<div class="container">
    <div style="margin-top: 10px;margin-bottom: 2px" class="row page-header">
        <div class="col-md-11 text-center">
            <h5>班级信息</h5>
        </div>
        <div class="col-md-1"></div>
    </div>
    <div class="jumbotron">
        <div class="row">
            <div class="col-md-3">
                <form role="form" action="showInfo">
                    <div class="control-group">
                        <label for="name">班级</label>
                        <input class="form-control" type="text" name="name" id="name">
                    </div>
                    <div class="control-group">
                        <label for="remark">备注</label>
                        <input class="form-control" type="text" name="remark" id="remark">
                    </div>
                    <a style="margin: 40px 10px 10px 0px" class="btn btn-success col-lg-12" onclick="infosubmit();">新增</a>
                    <input type="hidden" name="action" value="insert"/>
                    <input type="hidden" name="page" value="groupInfo">
                </form>
            </div>
            <div class="col-md-7 col-md-offset-1">
                <table style="width:100%" class="table-striped table-hover text-center">
                    <thead><td>班级</td><td>操作</td></thead>
                    <%
                        for(GroupBean groupBean : groupList) {
                    %>
                    <tr>
                        <td><%=groupBean.getGroupName()%></td>
                        <td><a href="showInfo?action=delete&page=groupInfo&row=<%=groupBean.getIdGroup()%>"><span class="fui-trash"></span></a></td>
                    </tr>
                    <%
                        }
                    %>
                </table>
            </div>
            <div class="col-md-1"></div>
        </div>
    </div>
</div> <!-- /container -->
</body>
</html>
