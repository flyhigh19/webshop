package com.bianyiit.servlet;

import com.bianyiit.dao.UserDao;
import com.bianyiit.domain.Student;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/userServlet")
public class UserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*1.查询所有的用户信息
        * 2.遍历用户集合，生成表格，响应到浏览器中*/
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=UTF-8");
        UserDao userDao = new UserDao();
        List<Student> userList = userDao.findAll();
        response.getWriter().println("<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>数据展示</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <table border='1px' width='200px'>\n" +
                "        <tr>\n" +
                "            <td>id</td>\n" +
                "            <td>username</td>\n" +
                "            <td>password</td>\n" +
                "        </tr>\n");
        for (Student student : userList) {
            response.getWriter().println("<tr>");
            response.getWriter().println("<td>"+student.getId()+"</td>");
            response.getWriter().println("<td>"+student.getUsername()+"</td>");
            response.getWriter().println("<td>"+student.getPassword()+"</td>");
            response.getWriter().println("</tr>");
        }
        response.getWriter().println("    </table>\n" +
                "</body>\n" +
                "</html>");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
