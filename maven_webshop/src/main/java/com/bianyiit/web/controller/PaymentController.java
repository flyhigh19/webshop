package com.bianyiit.web.controller;

import com.bianyiit.domain.Member;
import com.bianyiit.services.Impl.PaymentServiceImpl;
import com.bianyiit.services.PaymentService;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class PaymentController {
    /*根据下单的商品获取微信付款二维码的操作*/
    public void getWeiXinPayCode(HttpServletRequest request, HttpServletResponse response)throws IOException {
        response.setContentType("application/json;charset=utf-8");
        Member loginUser = (Member)request.getSession().getAttribute("loginUser");
        PaymentService paymentService=new PaymentServiceImpl();
        Map<String, String> result = paymentService.getWeiXinPayCode(loginUser);
        ObjectMapper objectMapper=new ObjectMapper();
        objectMapper.writeValue(response.getWriter(),result);
    }
    /*进入支付界面*/
    public void orderPay(HttpServletRequest request, HttpServletResponse response)throws IOException {
        response.setContentType("application/json;charset=utf-8");
        String orderID = request.getParameter("orderID");
        Member loginUser = (Member)request.getSession().getAttribute("loginUser");
        PaymentService paymentService=new PaymentServiceImpl();
        Map<String, String> result = paymentService.getWeiXinPayCode(loginUser,orderID);
        ObjectMapper objectMapper=new ObjectMapper();
        objectMapper.writeValue(response.getWriter(),result);
    }
}
