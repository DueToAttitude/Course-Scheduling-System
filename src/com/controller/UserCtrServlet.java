package com.controller;

import com.bean.UserBean;
import com.dao.UserDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by recycle on 12/22 0022.
 */
@WebServlet(name = "UserCtrServlet", urlPatterns = "/userCtrl")
public class UserCtrServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if("login".equals(action)) {
            String userName = request.getParameter("userName");
            String userPassword = request.getParameter("userPassword");
            UserBean userBean = new UserBean();
            userBean.setUserName(userName);
            userBean.setUserPassword(userPassword);
            UserDao userDao = new UserDao();
            Boolean flag = userDao.queryOne(userBean);
            if(flag) {
                request.getSession().setAttribute("loginName", userBean.getUserName());
                response.sendRedirect("main.jsp");
            }
            else {
                request.setAttribute("message", true);
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        }

        if("register".equals(action)) {
            String userName = request.getParameter("userName");
            String userPassword = request.getParameter("password");
            String pswTwice = request.getParameter("pswTwice");
            int userTpye = 0;
            if(!userPassword.trim().equals(pswTwice)) {
                request.setAttribute("message", "密码不一致！");
                request.getRequestDispatcher("register.jsp").forward(request, response);
            }
            else {
                UserBean userBean = new UserBean();
                userBean.setUserName(userName);
                userBean.setUserPassword(userPassword);
                userBean.setUserType(userTpye);
                int result = new UserDao().insert(userBean);
                if(result <= 0) {
                    request.setAttribute("message", "用户名不可用，请重试！");
                    request.getRequestDispatcher("register.jsp").forward(request, response);
                }
            }
            response.sendRedirect("login.jsp");
        }

        if("logout".equals(action)) {
            request.getSession().removeAttribute("loginName");
            response.sendRedirect("login.jsp");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
