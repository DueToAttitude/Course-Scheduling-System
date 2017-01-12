<%@ page import="com.dao.UserDao" %><%--
  Created by IntelliJ IDEA.
  User: recycle
  Date: 12/28 0028
  Time: 16:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Home</title>
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
</head>
<body>
<%@include file="banner.jsp"%>
<%
    String url;
    if(new UserDao().userType(loginName) == 1) {
        url = "groupInfo.jsp";
    }
    else {
        url = "groupSchedule.jsp";
    }
%>
<div class="container">
    <div class="row">
        <div style="margin-top: 50px" class="col-md-12">
            <div class="jumbotron">
                <h2>
                    欢迎来到排课系统！
                </h2>
                <br>
                <p>
                    &nbsp;&nbsp;&nbsp;&nbsp;在这里，你可以创建学期教学任务并进行排课。排课基本流程为：添加基本信息->手动预排->查看并下载课表。本系统提供自动和手动两种排课方式，自动排课不会覆盖手动排课的数据，而是会在手动预排的基础上完成排课。
                </p>
                <p>
                    <a style="width:150px" class="btn btn-primary btn-large" href=<%=url%>>
                        开始
                    </a>
                </p>
                <p>
                    <a class="btn btn-link" href="#">
                        <small>了解更多</small>
                    </a>
                </p>
            </div>
        </div>
    </div>
</div>
</body>
</html>
