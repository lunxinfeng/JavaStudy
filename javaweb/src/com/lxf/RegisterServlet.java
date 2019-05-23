package com.lxf;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "RegisterServlet" ,urlPatterns = {"/register"})
public class RegisterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        String account = request.getParameter("account");
//        String password = request.getParameter("password");
//
//        //数据库操作插入 用户信息
//        RegisterDao daoModel=new RegisterDao();
//        int result=daoModel.insertUser(account, password);

        PrintWriter out = response.getWriter();
        out.print("doPost");
        out.flush();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        out.print("doGet");
        out.flush();
    }
}
