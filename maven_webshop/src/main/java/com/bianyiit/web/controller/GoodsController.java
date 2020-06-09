package com.bianyiit.web.controller;

import com.bianyiit.domain.Goods;
import com.bianyiit.domain.PageBean;
import com.bianyiit.services.GoodsService;
import com.bianyiit.services.Impl.GoodsServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GoodsController {
    public void findPage(HttpServletRequest request, HttpServletResponse response)throws IOException {
        response.setContentType("application/json;charset=utf-8");
        String goodsID = null;
        String price = null;
        int currenPage = 1;
        int pageSize = 9;
        try {
            goodsID = String.valueOf(request.getParameter("goodsID"));
        } catch (NumberFormatException e) {
            //e.printStackTrace();
        }
        currenPage = Integer.parseInt(request.getParameter("currentPage"));
        pageSize = Integer.parseInt(request.getParameter("pageSize"));
        String wd = request.getParameter("wd");
        try {
            price = request.getParameter("price");
        } catch (Exception e) {
            /*e.printStackTrace();*/
        }
        try {
            wd=new String(wd.getBytes("ISO8859-1"),"UTF-8");
        } catch (Exception e) {

        }
        GoodsService goodsService=new GoodsServiceImpl();
        PageBean<Goods> pageBean;
        if(price!=null&&price.equals("price_asc")){
            pageBean=goodsService.findPageAscPrice(goodsID,currenPage,pageSize,wd,price);
        }else{
            pageBean=goodsService.findPage(goodsID,currenPage,pageSize,wd);
        }
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(response.getWriter(),pageBean);
    }
    public void findGoodsDetail(HttpServletRequest request, HttpServletResponse response)throws IOException {
        //告诉浏览器响应的消息体格式（MIME）
        //解决中文乱码问题
        //接收请求参数
        response.setContentType("application/json;charset=utf-8");
        String goodsID = request.getParameter("goodsID");
        String item = request.getParameter("item");
        //调用Service处理查询产品详情的业务逻辑
        GoodsService goodsService=new GoodsServiceImpl();
        Goods goods=goodsService.findGoodsDetail(goodsID,item);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(response.getWriter(),goods);
    }
}
