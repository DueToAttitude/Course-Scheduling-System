<%--
  Created by IntelliJ IDEA.
  User: recycle
  Date: 12/20 0020
  Time: 18:35
  To change this template use File | Settings | File Templates.
--%>
<%
    String loginName = (String)session.getAttribute("loginName");
    if(loginName == null || "".equals(loginName)) {
        response.sendRedirect("login.jsp");
        return;
    }
%>