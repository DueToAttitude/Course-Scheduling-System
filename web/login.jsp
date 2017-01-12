<%--
  Created by IntelliJ IDEA.
  User: recycle
  Date: 12/15 0015
  Time: 8:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>登录</title>

    <link href="css/style.css" rel="stylesheet">
    <link rel = "icon" href = "img/favicon.ico" type = "image/x-icon">
    <link rel = "Shortcut Icon" href = "img/favicon.ico" type = "image/x-icon">
    <!-- Loading Bootstrap -->
    <link href="css/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <!-- Loading Flat UI -->
    <link href="css/flat-ui.css" rel="stylesheet">

    <script src="js/jquery.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="js/scripts.js"></script>
</head>
<body>
<br>
<div class="container">
    <div class="row">
        <div class="col-md-12">
            <h4 class="text-center text-capitalize">排课账号登录</h4>
        </div>
    </div>
    <div class="row">
        <div class="col-md-4">
        </div>
        <div class="col-md-8">
            <div class="col-lg-6">
                <form class="login-form" name="login" action="userCtrl">
                    <br>
                    <div class="control-group">
                        <input name="userName" type="text" class="form-control" id="InputUser" placeholder="Enter Username">
                        <label class="login-field-icon fui-user" for="InputUser"></label>
                    </div>
                    <br>
                    <div class="control-group">
                        <input name="userPassword" type="password" class="form-control" id="InputPassword" placeholder="Enter Password">
                        <label class="login-field-icon fui-lock" for="InputPassword"></label>
                    </div>
                    <br>
                    <div class="row">
                        <div class="col-md-1"></div>
                        <div class="col-md-4">
                            <button class="btn btn-primary btn-block">登录</button>
                        </div>
                        <div class="col-md-1"></div>
                        <div class="col-md-1"></div>
                        <div class="col-md-4">
                            <input type="button" class="btn btn-block btn-default" value="注册" onclick="window.location.href='register.jsp'">
                        </div>
                        <div class="col-md-1"></div>
                    </div>
                    <input type="hidden" name="action" value="login">
                    <%
                        if(request.getAttribute("message") != null && (boolean)request.getAttribute("message")) {
                            out.println("用户名或密码错误！");
                        }
                    %>
                </form>
            </div>
        </div>
        <div class="col-md-4">
        </div>
    </div>
</div>
</body>
</html>