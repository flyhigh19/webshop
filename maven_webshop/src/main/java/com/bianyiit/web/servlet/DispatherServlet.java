package com.bianyiit.web.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 用户模块
 * /user/login
 * /user/regist
 * 分类模块
 * /category/findAll
 * /category/add
 * 产品模块
 *
 */
@WebServlet(value = "*.do",loadOnStartup = 1)
/*接收请求，处理请求*/
public class DispatherServlet extends HttpServlet {
    Properties properties=new Properties();
    Map<Object,Object> map=new HashMap<>();
    @Override
    public void init() throws ServletException {
        try {
            properties.load(DispatherServlet.class.getClassLoader().getResourceAsStream("url.properties"));
            for (Map.Entry<Object, Object> entry : properties.entrySet()) {
                Object key = entry.getKey();
                String className = (String)entry.getValue();
                Object value = Class.forName(className).newInstance();
                map.put(key,value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取方法名login.do
        String requestURI = request.getRequestURI();
        int index=requestURI.lastIndexOf("/")+1;
        String methodName = requestURI.substring(index,requestURI.lastIndexOf("."));
        //获取模块名user
        //模块名user--模块对象com.bianyiit.web.controller.UserController
        int index1 = requestURI.indexOf("/") + 1;
        String modelName = requestURI.substring(index1,(index-1));
        //从map中取出模块名反射获取的模块对象
        //模块对象UserController中有很多个方法，只有方法名login可以找到唯一的方法
        Object modelController = map.get(modelName);
        try {
            //通过模块对象获取对应方法对象
            //用户传过来的方法名是login，获取的就是login方法
            //用户传过来的方法名是regist，获取的就是regist方法
            Method method = modelController.getClass().getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
            //login()
            method.invoke(modelController,request,response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
