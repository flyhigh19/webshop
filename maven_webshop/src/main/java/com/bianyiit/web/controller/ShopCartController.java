package com.bianyiit.web.controller;

import com.bianyiit.domain.*;
import com.bianyiit.services.Impl.ShopCartServiceImpl;
import com.bianyiit.services.ShopCartService;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ShopCartController {
    /*添加购物车的操作*/
    public void addShopCart(HttpServletRequest request, HttpServletResponse response)throws IOException{
        response.setContentType("application/json;charset=utf-8");
        String goodsID = request.getParameter("goodsID");
        String item = request.getParameter("item");
        Integer goodsNum = Integer.parseInt(request.getParameter("goodsNum"));
        //将对象转换成json字符串/json字符串转成对象
        ObjectMapper objectMapper = new ObjectMapper();
        /*实体转json为NULL或者为空不参加序列化*/
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        /*创建空的购物车*/
        ShopCart shopCart=null;
        /*1.获取Cookie中的购物车*/
        Cookie[] cookies=request.getCookies();
        if(cookies!=null&&cookies.length>0){
            for (Cookie cookie : cookies) {
                if(cookie.getName().equals("shopCart")){
                    /*cookie中使用中文会出现乱码问题*/
                    String sc=cookie.getValue();
                    sc = URLDecoder.decode(sc, "utf-8");
                    /*将购物车Json转换成购物车实体类*/
                    shopCart=objectMapper.readValue(sc,ShopCart.class);
                    break;
                }
            }
        }
        /*2.Cookie中没有购物车，创建购物车对象*/
        if(shopCart==null){
            shopCart=new ShopCart();
        }
        /*3.追加当前商品到购物车中*/
        if(goodsID!=null&&item!=null&&goodsNum!=null){
            Goods goods=new Goods();
            goods.setGoodsID(goodsID);
            goods.setItem(item);
            /*根据商品类别编号和商品编号从数据库中取出对应的商品名称、折扣价格和商品图片地址封装进goods中*/
            ShopCartService shopCartService1=new ShopCartServiceImpl();
            Goods goodsMsg=shopCartService1.findGoodsMsg(goodsID,item);
            goods.setGoodsName(goodsMsg.getGoodsName());
            try {
                GoodsDetail goodsDetail=new GoodsDetail();
                if((goodsMsg.getGoodsDetail().getDiscountPrice()<0.0001)&&(goodsMsg.getGoodsDetail().getDiscountPrice()>-0.0001)){
                    goods.setPrice(goodsMsg.getPrice());
                }else{
                    goodsDetail.setDiscountPrice(goodsMsg.getGoodsDetail().getDiscountPrice());
                    goods.setPrice(goodsDetail.getDiscountPrice());
                }
            } catch (Exception e) {
                /*如果不存在商品折扣价格，就设置商品原价格*/
                goods.setPrice(goodsMsg.getPrice());
            }
            goods.setImgsrc(goodsMsg.getImgsrc());
            goods.setFactory(goodsMsg.getFactory());
            /*创建购物项，将商品封装进购物项中*/
            BuyerItem buyerItem=new BuyerItem();
            buyerItem.setGoods(goods);
            buyerItem.setGoodsNum(goodsNum);
            /*如果选购的是同一件商品，那么就在商品数量上+1*/
            shopCart.addItem(buyerItem);
        }
        /*对购物车的购物项进行排序*/
        List<BuyerItem> items = shopCart.getItems();
        Collections.sort(items, new Comparator<BuyerItem>() {
            @Override
            public int compare(BuyerItem o1, BuyerItem o2) {
                return -1;
            }
        });
        /*判断用户是否登录*/
        Member loginUser = (Member)request.getSession().getAttribute("loginUser");
        if(loginUser!=null){
            /*如果用户登陆了,将购物车追加到数据库中*/
            ShopCartService shopCartService=new ShopCartServiceImpl();
            shopCartService.addShopCart(shopCart,loginUser);
            /*清空Cookie，设置存活时间为0*/
            Cookie cookie=new Cookie("shopCart",null);
            cookie.setPath("/");
            cookie.setMaxAge(0);
            response.addCookie(cookie);
        }else{
            /*如果用户没有登录，保存购物车到Cookie中*/
            Writer w=new StringWriter();
            objectMapper.writeValue(w,shopCart);
            /*cookie中使用中文会出现乱码问题*/
            String sc=w.toString();
            sc = URLEncoder.encode(sc, "utf-8");
            Cookie cookie=new Cookie("shopCart",sc);
            cookie.setPath("/");
            //设置Cookie的过期时间为24小时
            cookie.setMaxAge(24*60*60);
            //向浏览器响应Cookie
            response.addCookie(cookie);
        }
        /*添加商品至购物车成功，设置resultInfo的flag为true*/
        ResultInfo resultInfo=new ResultInfo();
        resultInfo.setFlag(true);
        objectMapper.writeValue(response.getWriter(),resultInfo);
    }
    /*将购物车反馈给浏览器去显示购物车商品信息*/
    public void findShopCartList(HttpServletRequest request, HttpServletResponse response)throws IOException{
        response.setContentType("application/json;charset=utf-8");
        ObjectMapper objectMapper=new ObjectMapper();
        ResultInfo resultInfo=new ResultInfo();
        /*判断用户是否登录*/
        Member loginUser = (Member)request.getSession().getAttribute("loginUser");
        if(loginUser==null){
            /*获取Cookie中的购物车*/
            Cookie[] cookies=request.getCookies();
            if(cookies!=null&&cookies.length>0){
                for (Cookie cookie : cookies) {
                    if(cookie.getName().equals("shopCart")){
                        /*cookie中使用中文会出现乱码问题*/
                        String sc=cookie.getValue();
                        sc = URLDecoder.decode(sc, "utf-8");
                        /*直接从Cookie中取出购物车反馈给浏览器*/
                        ShopCart shopCart=objectMapper.readValue(sc,ShopCart.class);
                        resultInfo.setFlag(true);
                        resultInfo.setData(shopCart);
                        objectMapper.writeValue(response.getWriter(),resultInfo);
                        break;
                    }
                }
                resultInfo.setFlag(false);
                resultInfo.setIdentify("购物车空空如也，请先添加好商品进购物车再来查看！！");
                objectMapper.writeValue(response.getWriter(),resultInfo);
            }
        }else{
            ShopCart shopCart=null;
            /*获取Cookie中的购物车*/
            Cookie[] cookies=request.getCookies();
            if(cookies!=null&&cookies.length>0){
                for (Cookie cookie : cookies) {
                    if(cookie.getName().equals("shopCart")){
                        /*cookie中使用中文会出现乱码问题*/
                        String sc=cookie.getValue();
                        sc = URLDecoder.decode(sc, "utf-8");
                        /*从Cookie中取出购物车*/
                        shopCart=objectMapper.readValue(sc,ShopCart.class);
                        break;
                    }
                }
            }
            /*如果用户登陆了,将购物车追加到数据库中*/
            ShopCartService shopCartService=new ShopCartServiceImpl();
            if(shopCart!=null){
                shopCartService.addShopCart(shopCart,loginUser);
                /*清空Cookie，设置存活时间为0*/
                Cookie cookie=new Cookie("shopCart",null);
                cookie.setPath("/");
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }
            /*从数据库中取出数据封装进购物车实体类中，然后响应给浏览器*/
            shopCart = shopCartService.findGoodsMsg(loginUser);
            if(shopCart.getItems()==null||shopCart.getItems().size()==0){
                resultInfo.setFlag(false);
                resultInfo.setIdentify("购物车空空如也，请先添加好商品进购物车再来查看！！");
                objectMapper.writeValue(response.getWriter(),resultInfo);
            }else{
                resultInfo.setFlag(true);
                resultInfo.setData(shopCart);
                objectMapper.writeValue(response.getWriter(),resultInfo);
            }
        }
    }
    /*删除特定的购物车商品*/
    public void deleteGoodsInCart(HttpServletRequest request, HttpServletResponse response)throws IOException{
        response.setContentType("application/json;charset=utf-8");
        String goodsID = request.getParameter("goodsID");
        String item = request.getParameter("item");
        ObjectMapper objectMapper=new ObjectMapper();
        ResultInfo resultInfo=new ResultInfo();
        /*判断用户是否登录*/
        Member loginUser = (Member)request.getSession().getAttribute("loginUser");
        if(loginUser==null){
            /*获取Cookie中的购物车*/
            Cookie[] cookies=request.getCookies();
            if(cookies!=null&&cookies.length>0){
                for (Cookie cookie : cookies) {
                    if(cookie.getName().equals("shopCart")){
                        /*cookie中使用中文会出现乱码问题*/
                        String sc=cookie.getValue();
                        sc = URLDecoder.decode(sc, "utf-8");
                        ShopCart shopCart=objectMapper.readValue(sc,ShopCart.class);
                        for (BuyerItem buyerItem : shopCart.getItems()) {
                            if(buyerItem.getGoods().getGoodsID().equals(goodsID)&&buyerItem.getGoods().getItem().equals(item)){
                                shopCart.getItems().remove(buyerItem);
                                /*成功删除该购物车中的商品,重新更新Cookie并返回给浏览器*/
                                Writer w=new StringWriter();
                                objectMapper.writeValue(w,shopCart);
                                /*cookie中使用中文会出现乱码问题*/
                                String sc1=w.toString();
                                sc1 = URLEncoder.encode(sc1, "utf-8");
                                Cookie cookie1=new Cookie("shopCart",sc1);
                                cookie1.setPath("/");
                                //设置Cookie的过期时间为24小时
                                cookie1.setMaxAge(24*60*60);
                                //向浏览器响应Cookie
                                response.addCookie(cookie1);
                                resultInfo.setFlag(true);
                                resultInfo.setIdentify("已经成功删除该商品！！");
                                objectMapper.writeValue(response.getWriter(),resultInfo);
                                break;
                            }
                        }
                        break;
                    }
                }
                resultInfo.setFlag(false);
                resultInfo.setIdentify("购物车空空如也，请先添加好商品进购物车再来查看！！");
                objectMapper.writeValue(response.getWriter(),resultInfo);
            }
        }else{
            /*只需要从数据库购物车中删除对应商品即可*/
            ShopCartService shopCartService=new ShopCartServiceImpl();
            int count = shopCartService.deleteGoodsInCart(goodsID,item);
            if(count>0){
                resultInfo.setFlag(true);
                resultInfo.setIdentify("已经成功删除该商品！！");
                objectMapper.writeValue(response.getWriter(),resultInfo);
            }else{
                resultInfo.setFlag(false);
                resultInfo.setIdentify("购物车中已经没有该商品了！！");
                objectMapper.writeValue(response.getWriter(),resultInfo);
            }
        }
    }
    /*获取购物车中的单个商品反馈给浏览器*/
    public void findGoodsInCart(HttpServletRequest request, HttpServletResponse response)throws IOException{
        response.setContentType("application/json;charset=utf-8");
        ObjectMapper objectMapper=new ObjectMapper();
        ResultInfo resultInfo=new ResultInfo();
        String goodsID = request.getParameter("goodsID");
        String item = request.getParameter("item");
        String favoriteResult=request.getParameter("favoriteResult");
        /*根据用户分类ID和商品ID去Cookie中取或者去数据库中取出商品*/
        /*判断用户是否登录*/
        Member loginUser = (Member)request.getSession().getAttribute("loginUser");
        if(loginUser==null){
            /*获取Cookie中的购物车*/
            Cookie[] cookies=request.getCookies();
            if(cookies!=null&&cookies.length>0){
                for (Cookie cookie : cookies) {
                    if(cookie.getName().equals("shopCart")){
                        /*cookie中使用中文会出现乱码问题*/
                        String sc=cookie.getValue();
                        sc = URLDecoder.decode(sc, "utf-8");
                        /*直接从Cookie中取出购物车反馈给浏览器*/
                        ShopCart shopCart=objectMapper.readValue(sc,ShopCart.class);
                        for (BuyerItem buyerItem : shopCart.getItems()) {
                            if(buyerItem.getGoods().getGoodsID().equals(goodsID)&&buyerItem.getGoods().getItem().equals(item)){
                                buyerItem.setIsFavorite(favoriteResult);
                                objectMapper.writeValue(response.getWriter(),buyerItem);
                                break;
                            }
                        }
                        break;
                    }
                }
                resultInfo.setFlag(false);
                resultInfo.setIdentify("购物车空空如也，请先添加好商品进购物车再来查看！！");
                objectMapper.writeValue(response.getWriter(),resultInfo);
            }
        }else{
            /*只需要从数据库购物车中将数据取出即可*/
            ShopCartService shopCartService=new ShopCartServiceImpl();
            ShopCart shopCart = shopCartService.findGoodsMsg(loginUser);
            for (BuyerItem buyerItem : shopCart.getItems()) {
                if(buyerItem.getGoods().getGoodsID().equals(goodsID)&&buyerItem.getGoods().getItem().equals(item)){
                    buyerItem.setIsFavorite(favoriteResult);
                    objectMapper.writeValue(response.getWriter(),buyerItem);
                    break;
                }
            }
        }
    }
    public void updateShopCart(HttpServletRequest request, HttpServletResponse response)throws IOException{
        /*删除原有Cookie，保存新的Cookie*/
        response.setContentType("application/json;charset=utf-8");
        String goodsID = request.getParameter("goodsID");
        String item = request.getParameter("item");
        Integer goodsNum = Integer.parseInt(request.getParameter("goodsNum"));
        //将对象转换成json字符串/json字符串转成对象
        ObjectMapper objectMapper = new ObjectMapper();
        /*实体转json为NULL或者为空不参加序列化*/
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        /*创建空的购物车*/
        ShopCart shopCart=null;
        /*1.获取Cookie中的购物车*/
        Cookie[] cookies=request.getCookies();
        if(cookies!=null&&cookies.length>0){
            for (Cookie cookie : cookies) {
                if(cookie.getName().equals("shopCart")){
                    /*cookie中使用中文会出现乱码问题*/
                    String sc=cookie.getValue();
                    sc = URLDecoder.decode(sc, "utf-8");
                    /*将购物车Json转换成购物车实体类*/
                    shopCart=objectMapper.readValue(sc,ShopCart.class);
                    break;
                }
            }
        }
        /*2.Cookie中没有购物车，创建购物车对象*/
        if(shopCart==null){
            shopCart=new ShopCart();
        }
        for (BuyerItem buyerItem : shopCart.getItems()) {
            if(buyerItem.getGoods().getGoodsID().equals(goodsID)&&buyerItem.getGoods().getItem().equals(item)){
                buyerItem.setGoodsNum(goodsNum);
                break;
            }
        }
        Writer w=new StringWriter();
        objectMapper.writeValue(w,shopCart);
        /*cookie中使用中文会出现乱码问题*/
        String sc=w.toString();
        sc = URLEncoder.encode(sc, "utf-8");
        Cookie cookie=new Cookie("shopCart",sc);
        cookie.setPath("/");
        //设置Cookie的过期时间为24小时
        cookie.setMaxAge(24*60*60);
        //向浏览器响应Cookie
        response.addCookie(cookie);
        /*判断用户是否登录*/
        Member loginUser = (Member)request.getSession().getAttribute("loginUser");
        if(loginUser!=null){
            /*清空Cookie，设置存活时间为0*/
            Cookie cookie1=new Cookie("shopCart",null);
            cookie1.setPath("/");
            cookie1.setMaxAge(0);
            response.addCookie(cookie1);
            /*直接从数据库中取出所有商品，然后更改数量之后再重新添加至数据库中*/
            ShopCartService shopCartService=new ShopCartServiceImpl();
            ShopCart shopCart1 = shopCartService.findGoodsMsg(loginUser);
            for (BuyerItem buyerItem : shopCart1.getItems()) {
                if(buyerItem.getGoods().getGoodsID().equals(goodsID)&&buyerItem.getGoods().getItem().equals(item)){
                    buyerItem.setGoodsNum(goodsNum);
                    break;
                }
            }
            if(shopCart1!=null){
                shopCartService.updateShopCart(shopCart1,loginUser);
            }
        }
    }
}
