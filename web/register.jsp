<%--
  Created by IntelliJ IDEA.
  User: recycle
  Date: 12/9 0009
  Time: 23:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>register</title>
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
            <h4 class="text-center text-capitalize">排课账号注册</h4>
        </div>
    </div>
    <div class="row">
        <div class="col-md-4">
        </div>
        <div class="col-md-8">
            <div class="col-lg-6">
                <form class="login-form" name="register" action="userCtrl">
                    <br>
                    <div class="form-group">
                        <label class="login-field-icon fui-user" for="InputUser"></label>
                        <input name="userName" type="text" class="form-control" id="InputUser" placeholder="Enter Username">
                    </div>
                    <br>
                    <div class="form-group">
                        <label class="login-field-icon fui-lock" for="InputPassword"></label>
                        <input name="password" type="password" class="form-control" id="InputPassword" placeholder="Enter Password">
                    </div>
                    <br>
                    <div class="form-group">
                        <label class="login-field-icon fui-check" for="ConfirmPassword"></label>
                        <input name="pswTwice" type="password" class="form-control" id="ConfirmPassword" placeholder="Confirm Password">
                    </div>
                    <br>
                    <div class="row">
                        <div class="col-md-3"></div>
                        <div class="col-md-6">
                            <button class="btn btn-primary btn-block">提交</button>
                        </div>
                        <div class="col-md-3"></div>
                    </div>
                    <input type="hidden" name="action" value="register">
                    <%
                        if(request.getAttribute("message") != null && !("".equals(((String )request.getAttribute("message")).trim()))) {
                            out.println(((String )request.getAttribute("message")).trim());
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
