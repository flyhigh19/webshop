package com.bianyiit.web.controller;

import com.bianyiit.domain.*;
import com.bianyiit.services.Impl.OrderServiceImpl;
import com.bianyiit.services.Impl.PaymentServiceImpl;
import com.bianyiit.services.Impl.ShopCartServiceImpl;
import com.bianyiit.services.OrderService;
import com.bianyiit.services.PaymentService;
import com.bianyiit.services.ShopCartService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class OrderController {
    /*将用户选择好的购物车商品信息反馈给浏览器订单生成界面去显示*/
    public void findGoodsOrderList(HttpServletRequest request, HttpServletResponse response)throws IOException {
        response.setContentType("application/json;charset=utf-8");
        ObjectMapper objectMapper=new ObjectMapper();
        ResultInfo resultInfo=new ResultInfo();
        Member loginUser = (Member)request.getSession().getAttribute("loginUser");
        ShopCartService shopCartService=new ShopCartServiceImpl();
        /*从数据库中取出数据封装进购物车实体类中，然后响应给浏览器*/
        ShopCart shopCart = null;
        try {
            shopCart = shopCartService.findGoodsMsg(loginUser);
            for (BuyerItem item : shopCart.getItems()) {
                OrderService orderService=new OrderServiceImpl();
                Goods goods=orderService.findGoods(item.getGoods().getGoodsID(), item.getGoods().getItem());
                item.setGoods(goods);
                Float result=0f;
                if(item.getGoodsNum()*goods.getPrice()<20&&item.getGoodsNum()*goods.getPrice()>0){
                    result=1f;
                }else if(item.getGoodsNum()*goods.getPrice()>=20&&item.getGoodsNum()*goods.getPrice()<40){
                    result=2f;
                }else if(item.getGoodsNum()*goods.getPrice()>=40&&item.getGoodsNum()*goods.getPrice()<80){
                    result=3f;
                }else if(item.getGoodsNum()*goods.getPrice()>=80&&item.getGoodsNum()*goods.getPrice()<100){
                    result=4f;
                }else if(item.getGoodsNum()*goods.getPrice()>=100&&item.getGoodsNum()*goods.getPrice()<150){
                    result=5f;
                }else{
                    result=10f;
                }
                item.setYufei(result);
                int goodsDid = orderService.findGoodsDid(item.getGoods().getGoodsID(), item.getGoods().getItem());
                GoodsDetail goodsDetail=orderService.findGoodsDetail(goodsDid);
                item.getGoods().setGoodsDetail(goodsDetail);
                resultInfo.setFlag(true);
                resultInfo.setData(shopCart);
            }
        } catch (Exception e) {
            resultInfo.setFlag(false);
        }
        objectMapper.writeValue(response.getWriter(),resultInfo);
    }

    public void addReceiveMsg(HttpServletRequest request, HttpServletResponse response)throws IOException {
        response.setContentType("application/json;charset=utf-8");
        Map<String, String[]> parameterMap = request.getParameterMap();
        ObjectMapper objectMapper=new ObjectMapper();
        ReceiveMsg receiveMsg=new ReceiveMsg();
        try {
            BeanUtils.populate(receiveMsg,parameterMap);
        } catch (Exception e) {
            //e.printStackTrace();
        }
        Member loginUser = (Member)request.getSession().getAttribute("loginUser");
        OrderService orderService = new OrderServiceImpl();
        int add=0;
        try {
            add=orderService.addReceiveMsg(receiveMsg,loginUser);
        } catch (Exception e) {
            //e.printStackTrace();
        }
        ResultInfo resultInfo=new ResultInfo();
        if(add>0){
            resultInfo.setFlag(true);
        }else{
            resultInfo.setFlag(false);
        }
        objectMapper.writeValue(response.getWriter(),resultInfo);
    }
    /*修改收货地址*/
    public void updateReceiveMsg(HttpServletRequest request, HttpServletResponse response)throws IOException {
        response.setContentType("application/json;charset=utf-8");
        Map<String, String[]> parameterMap = request.getParameterMap();
        ReceiveMsg receiveMsg=new ReceiveMsg();
        try {
            BeanUtils.populate(receiveMsg,parameterMap);
        } catch (Exception e) {
            //e.printStackTrace();
        }
        /*直接更新用户收货信息*/
        OrderService orderService = new OrderServiceImpl();
        int update= 0;
        try {
            update = orderService.updateReceiveMsg(receiveMsg);
        } catch (Exception e) {
            //e.printStackTrace();
        }
        ObjectMapper objectMapper=new ObjectMapper();
        ResultInfo resultInfo=new ResultInfo();
        if(update>0){
            resultInfo.setFlag(true);
        }else{
            resultInfo.setFlag(false);
            resultInfo.setIdentify("收货地址更新失败！！");
        }
        objectMapper.writeValue(response.getWriter(),resultInfo);
    }
    /*删除收货地址*/
    public void deleteReceiveMsg(HttpServletRequest request, HttpServletResponse response)throws IOException {
        response.setContentType("application/json;charset=utf-8");
        int receiveID = Integer.parseInt(request.getParameter("receiveID"));
        /*直接删除用户收货信息*/
        OrderService orderService = new OrderServiceImpl();
        int deleteCount=0;
        try {
            deleteCount=orderService.deleteReceiveMsg(receiveID);
        } catch (Exception e) {
            //e.printStackTrace();
        }
        ObjectMapper objectMapper=new ObjectMapper();
        ResultInfo resultInfo=new ResultInfo();
        if(deleteCount>0){
            resultInfo.setFlag(true);
        }else{
            resultInfo.setFlag(false);
            resultInfo.setIdentify("删除收货地址失败！！");
        }
        objectMapper.writeValue(response.getWriter(),resultInfo);
    }
    /*设置默认收货地址*/
    public void setDefaultAddress(HttpServletRequest request, HttpServletResponse response)throws IOException {
        response.setContentType("application/json;charset=utf-8");
        int receiveID = Integer.parseInt(request.getParameter("receiveID"));
        ObjectMapper objectMapper=new ObjectMapper();
        ResultInfo resultInfo=new ResultInfo();
        /*直接更新用户收货信息*/
        OrderService orderService = new OrderServiceImpl();
        try {
            orderService.setDefaultAddress(receiveID);
            resultInfo.setFlag(true);
        } catch (Exception e) {
            //e.printStackTrace();
            resultInfo.setFlag(false);
            resultInfo.setIdentify("更新默认收货地址失败！！");
        }
        objectMapper.writeValue(response.getWriter(),resultInfo);
    }

    public void confirmOrder(HttpServletRequest request, HttpServletResponse response)throws IOException {
        response.setContentType("application/json;charset=utf-8");
        /*得到此时正在线的用户*/
        Member loginUser = (Member)request.getSession().getAttribute("loginUser");
        OrderService orderService=new OrderServiceImpl();
        boolean flag=orderService.confirmOrder(loginUser);
        ObjectMapper objectMapper=new ObjectMapper();
        ResultInfo resultInfo=new ResultInfo();
        if(flag){
            resultInfo.setFlag(true);
        }else{
            resultInfo.setFlag(false);
            resultInfo.setIdentify("生成订单失败！！");
        }
        objectMapper.writeValue(response.getWriter(),resultInfo);
    }

    public void findConfirmOrder(HttpServletRequest request, HttpServletResponse response)throws IOException {
        response.setContentType("application/json;charset=utf-8");
        /*从数据库中找到已完成的订单数据，将数据返回至前端*/
        /*得到此时正在线的用户*/
        Member loginUser = (Member)request.getSession().getAttribute("loginUser");
        OrderService orderService=new OrderServiceImpl();
        ObjectMapper objectMapper=new ObjectMapper();
        List<Order_Main> orderMainList= null;
        try {
            orderMainList = orderService.findConfirmOrder(loginUser);
        } catch (Exception e) {
            //e.printStackTrace();
        }
        if(orderMainList==null||orderMainList.size()==0){
            ResultInfo resultInfo=new ResultInfo();
            resultInfo.setFlag(false);
            resultInfo.setIdentify("用户没有完成下单操作，无法支付！！");
            objectMapper.writeValue(response.getWriter(),resultInfo);
        }else{
            objectMapper.writeValue(response.getWriter(),orderMainList.get(orderMainList.size()-1));
        }
    }
    /*获取用户订单列表*/
    public void findMyOrderList(HttpServletRequest request, HttpServletResponse response)throws IOException {
        response.setContentType("application/json;charset=utf-8");
        Member loginUser = (Member)request.getSession().getAttribute("loginUser");
        OrderService orderService=new OrderServiceImpl();
        ObjectMapper objectMapper=new ObjectMapper();
        List<Order_Main> orderMainList= null;
        try {
            orderMainList = orderService.findConfirmOrder(loginUser);
            /*对订单列表进行排序*/
            Collections.sort(orderMainList, new Comparator<Order_Main>() {
                @Override
                public int compare(Order_Main o1, Order_Main o2) {
                    return -1;
                }
            });
        } catch (Exception e) {
            //e.printStackTrace();
        }
        if(orderMainList==null||orderMainList.size()==0){
            ResultInfo resultInfo=new ResultInfo();
            resultInfo.setFlag(false);
            resultInfo.setIdentify("用户没有完成下单操作，无法支付！！");
            objectMapper.writeValue(response.getWriter(),resultInfo);
        }else{
            objectMapper.writeValue(response.getWriter(),orderMainList);
        }
    }
    /*获取用户需要支付的订单*/
    public void findMyPayOrder(HttpServletRequest request, HttpServletResponse response)throws IOException {
        response.setContentType("application/json;charset=utf-8");
        ObjectMapper objectMapper=new ObjectMapper();
        String orderID = request.getParameter("orderID");
        Member loginUser = (Member)request.getSession().getAttribute("loginUser");
        OrderService orderService=new OrderServiceImpl();
        Order_Main orderMain=orderService.findMyPayOrder(loginUser,orderID);
        if(orderMain==null){
            ResultInfo resultInfo=new ResultInfo();
            resultInfo.setFlag(false);
            resultInfo.setIdentify("用户订单出现问题，无法支付！！");
            objectMapper.writeValue(response.getWriter(),resultInfo);
        }else{
            objectMapper.writeValue(response.getWriter(),orderMain);
        }
    }
    /*删除订单*/
    public void deleteMyPayOrder(HttpServletRequest request, HttpServletResponse response)throws IOException {
        response.setContentType("application/json;charset=utf-8");
        String orderID = request.getParameter("orderID");
        ObjectMapper objectMapper=new ObjectMapper();
        ResultInfo resultInfo=new ResultInfo();
        OrderService orderService=new OrderServiceImpl();
        int deleteCount=orderService.deleteMyPayOrder(orderID);
        if(deleteCount>0){
            resultInfo.setFlag(true);
        }else{
            resultInfo.setFlag(false);
            resultInfo.setIdentify("删除订单出错，请重试！！");
        }
        objectMapper.writeValue(response.getWriter(),resultInfo);
    }
}
