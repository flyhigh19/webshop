package com.bianyiit.servlet;

import com.bianyiit.util.FilenameUtils;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@WebServlet("/downloadServlet")
public class DownloadServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*1.接收请求参数,返回文件名*/
        String filename = request.getParameter("filename");
        /*2.通过输入流读取文件内容*/
        String basepath="D:\\学习专用代码库\\day01\\web\\img\\";
        FileInputStream fis = new FileInputStream(basepath+filename);
        //处理中文文件名
        String agent = request.getHeader("User-Agent");
        filename= FilenameUtils.getFileName(agent,filename);
        /*3.通过responnse把文件写回浏览器*/
        response.setHeader("Content-Disposition","attachment;filename="+filename);
        ServletOutputStream os = response.getOutputStream();
        byte[] buf=new byte[1024];
        int len=0;
        while((len=fis.read(buf))!=-1){
            os.write(buf,0,len);
        }
        fis.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
