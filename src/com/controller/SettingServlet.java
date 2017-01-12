package com.controller;

import com.core.SettingProp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;

/**
 * Created by recycle on 1/6 0006.
 */
@WebServlet(name = "setting", urlPatterns = "/setting")
public class SettingServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String num = URLDecoder.decode(request.getParameter("num"), "utf-8");
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
//        int num1;
//        num1 = Integer.parseInt(num.trim());
//        System.out.println(num1);
//        if(num1 == 0) {
//            out.print("{\"state\":\"数据格式出错，请输入数字\"}");
//        }
//        else {
//            new SettingProp().setNum(num1);
//            out.print("{\"state\":\"设置完成\"}");
//        }
        try {
            if(Integer.parseInt(num) <= 0) {
                out.print("{\"state\":\"请输入大于0的整数\"}");
            }
            else {
                new SettingProp().setNum(Integer.parseInt(num.trim()));
                out.print("{\"state\":\"设置完成\"}");
            }
        }
        catch (NumberFormatException e) {
            out.print("{\"state\":\"数据格式出错，请输入数字\"}");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
