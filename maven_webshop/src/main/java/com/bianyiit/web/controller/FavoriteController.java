package com.bianyiit.web.controller;

import com.bianyiit.domain.*;
import com.bianyiit.services.FavoriteService;
import com.bianyiit.services.GoodsService;
import com.bianyiit.services.Impl.FavoriteServiceImpl;
import com.bianyiit.services.Impl.GoodsServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class FavoriteController {
    //判断用户是否已经收藏了此产品
    public void isFavorite(HttpServletRequest request, HttpServletResponse response)throws IOException {
        //告诉浏览器响应的消息体格式（MIME）
        //解决中文乱码问题
        //接收请求参数
        response.setContentType("application/json;charset=utf-8");
        String goodsID = request.getParameter("goodsID");
        int item = Integer.parseInt(request.getParameter("item"));
        String item1 = String.valueOf(item);
        //调用Service处理查询产品详情的业务逻辑
        //获取登录的用户信息
        ResultInfo resultInfo = new ResultInfo();
        Member loginUser = (Member)request.getSession().getAttribute("loginUser");
        if(loginUser==null){
            resultInfo.setFlag(false);
        }else{
            FavoriteService favoriteService=new FavoriteServiceImpl();
            Favorite favorite=favoriteService.isFavorite(goodsID,item,loginUser.getMemberID());
            GoodsService goodsService=new GoodsServiceImpl();
            Goods goods=goodsService.findGoodsDetail(goodsID,item1);
            FavoriteService routeService=new FavoriteServiceImpl();
            Integer count=routeService.findFavoriteCount(goodsID,item);
            if(favorite==null){
                resultInfo.setFlag(false);
            }else{
                resultInfo.setFlag(true);
            }
            resultInfo.setGoods(goods);
            resultInfo.setCount(count);
        }
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(response.getWriter(),resultInfo);
    }
    public void addFavorite(HttpServletRequest request, HttpServletResponse response)throws IOException {
        //告诉浏览器响应的消息体格式（MIME）
        //解决中文乱码问题
        //接收请求参数
        response.setContentType("application/json;charset=utf-8");
        String goodsID = request.getParameter("goodsID");
        int item = Integer.parseInt(request.getParameter("item"));
        String favoriteid=null;
        try {
            favoriteid = request.getParameter("favoriteid");
        } catch (Exception e) {
            //e.printStackTrace();
        }
        //调用Service处理查询产品详情的业务逻辑
        //获取登录的用户信息
        Member loginUser = (Member)request.getSession().getAttribute("loginUser");
        FavoriteService favoriteService=new FavoriteServiceImpl();
        Integer update=favoriteService.addFavorite(goodsID,item,loginUser.getMemberID());
        ResultInfo resultInfo = new ResultInfo();
        if(update<=0){
            resultInfo.setFlag(false);
        }else{
            resultInfo.setFlag(true);
            resultInfo.setIdentify(favoriteid);
        }
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(response.getWriter(),resultInfo);
    }
    //处理取消收藏功能
    public void removeFavorite(HttpServletRequest request, HttpServletResponse response)throws IOException {
        //告诉浏览器响应的消息体格式（MIME）
        //解决中文乱码问题
        //接收请求参数
        response.setContentType("application/json;charset=utf-8");
        String goodsID = request.getParameter("goodsID");
        int item = Integer.parseInt(request.getParameter("item"));
        //调用Service处理查询产品详情的业务逻辑
        //获取登录的用户信息
        Member loginUser = (Member)request.getSession().getAttribute("loginUser");
        FavoriteService favoriteService=new FavoriteServiceImpl();

        Integer count=favoriteService.removeFavorite(goodsID,item,loginUser.getMemberID());
        ResultInfo resultInfo = new ResultInfo();
        if(count<=0){
            resultInfo.setFlag(false);
        }else{
            resultInfo.setFlag(true);
        }
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(response.getWriter(),resultInfo);
    }
    //获取产品的收藏次数
    public void favoriteCount(HttpServletRequest request, HttpServletResponse response)throws IOException {
        //告诉浏览器响应的消息体格式（MIME）
        //解决中文乱码问题
        //接收请求参数
        response.setContentType("application/json;charset=utf-8");
        String goodsID = request.getParameter("goodsID");
        int item = Integer.parseInt(request.getParameter("item"));
        String item1= String.valueOf(item);
        //调用Service处理查询产品详情的业务逻辑
        FavoriteService routeService=new FavoriteServiceImpl();
        Integer count=routeService.findFavoriteCount(goodsID,item);
        GoodsService goodsService=new GoodsServiceImpl();
        Goods goods=goodsService.findGoodsDetail(goodsID,item1);
        Count count1=new Count();
        count1.setCount(count);
        count1.setGoods(goods);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(response.getWriter(),count1);
    }
    //获取收藏排行榜
    public void favoriteRank(HttpServletRequest request, HttpServletResponse response)throws IOException {
        //告诉浏览器响应的消息体格式（MIME）
        //解决中文乱码问题
        //接收请求参数
        response.setContentType("application/json;charset=utf-8");
        //调用Service处理查询产品详情的业务逻辑
        FavoriteService routeService=new FavoriteServiceImpl();
        List<FavoriteRank> favoriteRankList=routeService.findFavoriteRank();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(response.getWriter(),favoriteRankList);
    }
    /*获取用户个人收藏列表*/
    public void myFavoriteList(HttpServletRequest request, HttpServletResponse response)throws IOException {
        //告诉浏览器响应的消息体格式（MIME）
        //解决中文乱码问题
        //接收请求参数
        response.setContentType("application/json;charset=utf-8");
        //调用Service处理查询产品详情的业务逻辑
        FavoriteService routeService=new FavoriteServiceImpl();
        Member loginUser = (Member) request.getSession().getAttribute("loginUser");
        List<FavoriteRank> favoriteRankList= null;
        try {
            favoriteRankList = routeService.myFavoriteList(loginUser);
        } catch (Exception e) {
            //e.printStackTrace();
        }
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(response.getWriter(),favoriteRankList);
    }
}
