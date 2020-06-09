package com.bianyiit.servlet;

import com.bianyiit.dao.StudentDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*@WebServlet("/web")*/
public class ServletDemo3 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //接收get请求并处理
        doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //接收post请求并处理
        req.setCharacterEncoding("UTF-8");
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        boolean login = false;
        StudentDao dao=new StudentDao();
        login = dao.findByUsernameAndPassword(username, password);
        /*//进行判断
        if(login){
            *//*resp.setContentType("text/html;charset=UTF-8");
            resp.getWriter().write("登录成功");*//*
            req.getRequestDispatcher("/study01.html").forward(req,resp);
        }else{
           *//* resp.setContentType("text/html;charset=UTF-8");
            resp.getWriter().write("登录失败");*//*
            req.getRequestDispatcher("/study02.html").forward(req,resp);
        }*/
    }
}
