package com.bianyiit.services.Impl;

import com.bianyiit.dao.GoodsDao;
import com.bianyiit.dao.Impl.GoodsDaoImpl;
import com.bianyiit.dao.Impl.OrderDaoImpl;
import com.bianyiit.dao.Impl.ShopCartDaoImpl;
import com.bianyiit.dao.Impl.UserDaoImpl;
import com.bianyiit.dao.OrderDao;
import com.bianyiit.dao.ShopCartDao;
import com.bianyiit.dao.UserDao;
import com.bianyiit.domain.*;
import com.bianyiit.services.GoodsService;
import com.bianyiit.services.OrderService;
import com.bianyiit.services.ShopCartService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderServiceImpl implements OrderService {
    OrderDao orderDao=new OrderDaoImpl();
    @Override
    public int findGoodsDid(String goodsID, String item) {
        return orderDao.findGoodsDid(goodsID,item);
    }

    @Override
    public GoodsDetail findGoodsDetail(int goodsDid) {
        return orderDao.findGoodsDetail(goodsDid);
    }

    @Override
    public Goods findGoods(String goodsID, String item) {
        return orderDao.findGoods(goodsID,item);
    }

    @Override
    public int addReceiveMsg(ReceiveMsg receiveMsg, Member loginUser) {
        /*先从数据库中查找出用户已经添加好的收货信息*/
        UserDao userDao=new UserDaoImpl();
        List<ReceiveMsg> receiveMsgList=userDao.getReceiveMsg(loginUser.getMemberID());
        if(!receiveMsgList.isEmpty()&&receiveMsgList!=null){
            receiveMsg.setIsDefault(0);
        }else{
            receiveMsg.setIsDefault(1);
        }
        int member_ReceiveMsg=orderDao.getMember_ReceiveMsg(loginUser.getMemberID());
        receiveMsg.setMember_ReceiveAddress(member_ReceiveMsg);
        return orderDao.addReceiveMsg(receiveMsg,loginUser);
    }

    @Override
    public int updateReceiveMsg(ReceiveMsg receiveMsg) {
        return orderDao.updateReceiveMsg(receiveMsg);
    }

    @Override
    public int deleteReceiveMsg(int receiveID) {
        /*先查找删除的收货地址是否为默认地址*/
        ReceiveMsg receiveMsg=orderDao.findReceiveMsgIsDefault(receiveID);
        if(receiveMsg.getIsDefault()==1){
            /*如果是默认地址，先删除该地址*/
            int deleteCount=orderDao.deleteReceiveMsg(receiveID);
            if(deleteCount>0){
                /*根据删除的地址的Member_ReceiveAddress字段查找出其它的收货地址列表*/
                List<ReceiveMsg> receiveMsgList = orderDao.findReceiveMsg(receiveMsg.getMember_ReceiveAddress());
                if(!receiveMsgList.isEmpty()&&receiveMsgList!=null) {
                    ReceiveMsg receiveMsg1 = receiveMsgList.get(0);
                    receiveMsg1.setIsDefault(1);
                    orderDao.updateReceiveMsg(receiveMsg1);
                }
                return 1;
            }else{
                return 0;
            }
        }else{
            /*如果不是默认地址，直接删除就行*/
            return orderDao.deleteReceiveMsg(receiveID);
        }
    }

    @Override
    public void setDefaultAddress(int receiveID) {
        ReceiveMsg receiveMsg=orderDao.findReceiveMsgIsDefault(receiveID);
        List<ReceiveMsg> receiveMsgList = orderDao.findReceiveMsg(receiveMsg.getMember_ReceiveAddress());
        if(!receiveMsgList.isEmpty()&&receiveMsgList!=null) {
            for (ReceiveMsg msg : receiveMsgList) {
                if(msg.getIsDefault()==1){
                    msg.setIsDefault(0);
                    orderDao.updateReceiveMsg(msg);
                }
            }
        }
        receiveMsg.setIsDefault(1);
        orderDao.updateReceiveMsg(receiveMsg);
    }

    @Override
    public boolean confirmOrder(Member loginUser) {
        Order_Main orderMain=new Order_Main();
        List<Order_Detail> orderDetailList=new ArrayList<Order_Detail>();
        /*获取订单编号*/
        orderMain.setOrderID(getOrderIDAddOne());
        /*获取生成订单的时间*/
        orderMain.setOrderTime(getDateTime());
        /*设置会员订单状态*/
        orderMain.setOrderStatus("未付款");
        /*设置会员ID*/
        orderMain.setMemberID(loginUser.getMemberID());
        ShopCartService shopCartService=new ShopCartServiceImpl();
        //根据用户ID获取数据库中购物车中的所有数据
        ShopCart shopCart = shopCartService.findGoodsMsg(loginUser);
        for (int i = 0; i < shopCart.getItems().size(); i++) {
            Order_Detail orderDetail=new Order_Detail();
            Goods goodsList = shopCart.getItems().get(i).getGoods();
            GoodsDao goodsDao=new GoodsDaoImpl();
            /*得到商品基本信息表和商品的详细信息表*/
            Goods good=goodsDao.findGoodsBasicInformation(goodsList.getGoodsID(),goodsList.getItem());
            GoodsDetail goodsDetail = goodsDao.findGoodsDetailInformation(good.getDid());
            /*设置订单编号*/
            orderDetail.setOrderID(getOrderIDAddOne());
            /*设置商品分类ID*/
            orderDetail.setGoodsID(good.getGoodsID());
            /*设置商品编号ID*/
            orderDetail.setItem(good.getItem());
            /*设置商品原价格*/
            orderDetail.setPrice(good.getPrice());
            /*设置商品购买的数量*/
            orderDetail.setGoodsNum(shopCart.getItems().get(i).getGoodsNum());
            /*设置商品的销售厂家*/
            orderDetail.setFactory(good.getFactory());
            /*设置商品的图片的地址*/
            orderDetail.setImgsrc(good.getImgsrc());
            /*设置商品的描述信息*/
            orderDetail.setDescription(good.getDescription());
            /*设置商品的折扣价格*/
            try {
                orderDetail.setDiscountPrice(goodsDetail.getDiscountPrice());
            } catch (Exception e) {
                orderDetail.setDiscountPrice(0.00);
            }
            /*设置运费*/
            try {
                Float result=0f;
                if(orderDetail.getDiscountPrice()>0){
                    if(orderDetail.getGoodsNum()*orderDetail.getDiscountPrice()<20&&orderDetail.getGoodsNum()*orderDetail.getDiscountPrice()>0){
                        result=1f;
                    }else if(orderDetail.getGoodsNum()*orderDetail.getDiscountPrice()>=20&&orderDetail.getGoodsNum()*orderDetail.getDiscountPrice()<40){
                        result=2f;
                    }else if(orderDetail.getGoodsNum()*orderDetail.getDiscountPrice()>=40&&orderDetail.getGoodsNum()*orderDetail.getDiscountPrice()<80){
                        result=3f;
                    }else if(orderDetail.getGoodsNum()*orderDetail.getDiscountPrice()>=80&&orderDetail.getGoodsNum()*orderDetail.getDiscountPrice()<100){
                        result=4f;
                    }else if(orderDetail.getGoodsNum()*orderDetail.getDiscountPrice()>=100&&orderDetail.getGoodsNum()*orderDetail.getDiscountPrice()<150){
                        result=5f;
                    }else{
                        result=10f;
                    }
                }else{
                    if(orderDetail.getGoodsNum()*orderDetail.getPrice()<20&&orderDetail.getGoodsNum()*orderDetail.getPrice()>0){
                        result=1f;
                    }else if(orderDetail.getGoodsNum()*orderDetail.getPrice()>=20&&orderDetail.getGoodsNum()*orderDetail.getPrice()<40){
                        result=2f;
                    }else if(orderDetail.getGoodsNum()*orderDetail.getPrice()>=40&&orderDetail.getGoodsNum()*orderDetail.getPrice()<80){
                        result=3f;
                    }else if(orderDetail.getGoodsNum()*orderDetail.getPrice()>=80&&orderDetail.getGoodsNum()*orderDetail.getPrice()<100){
                        result=4f;
                    }else if(orderDetail.getGoodsNum()*orderDetail.getPrice()>=100&&orderDetail.getGoodsNum()*orderDetail.getPrice()<150){
                        result=5f;
                    }else{
                        result=10f;
                    }
                }
                orderDetail.setYufei(result);
            } catch (Exception e) {
                //e.printStackTrace();
            }
            /*将单个的订单明细表添加中订单明细集合中*/
            orderDetailList.add(orderDetail);
        }
        /*将订单明细表的列表信息封装进订单主表中*/
        orderMain.setOrderDetail(orderDetailList);
        /*将订单中添加的所有商品的总价格算出来并封装进订单主表中*/
        double totalMoney=0.00;
        for (int i = 0; i < orderDetailList.size(); i++) {
            if(orderDetailList.get(i).getDiscountPrice()>0){
                totalMoney=totalMoney+orderDetailList.get(i).getDiscountPrice()*orderDetailList.get(i).getGoodsNum()+orderDetailList.get(i).getYufei();
            }else{
                totalMoney=totalMoney+orderDetailList.get(i).getPrice()*orderDetailList.get(i).getGoodsNum()+orderDetailList.get(i).getYufei();
            }
        }
        orderMain.setTotalMoney(totalMoney);
        /*将订单主表中的收货地址信息封装进订单主表中*/
        ReceiveMsg receiveMsg=orderDao.findReceiveMsg(orderMain.getMemberID());
        orderMain.setReceiveName(receiveMsg.getReceiveName());
        orderMain.setReceivePhone(receiveMsg.getReceivePhone());
        orderMain.setReceiveAddress("中国"+receiveMsg.getReceiveAddress());
        orderMain.setReceiveDetailAddress(receiveMsg.getReceiveDetailAddress());
        /*将封装好的订单信息保存至数据库中*/
        int addCount=0;
        try {
            addCount=orderDao.addOrderMain(orderMain);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(addCount>0){
            /*将封装好的订单详细信息保存至数据库中*/
            for (Order_Detail orderDetail : orderMain.getOrderDetail()) {
                orderDao.addOrderDetail(orderDetail);
            }
            /*删除购物车中的列表信息*/
            orderDao.deleteShopCart(orderMain.getMemberID());
            return true;
        }else{
            return false;
        }
    }
    /*查找该用户的所有订单数据*/
    @Override
    public List<Order_Main> findConfirmOrder(Member loginUser) {
        List<Order_Main> orderMainList= null;
        try {
            orderMainList = orderDao.findConfirmOrderMain(loginUser.getMemberID());
            for (Order_Main orderMain : orderMainList) {
                List<Order_Detail> orderDetailList=orderDao.findConfirmOrderDetail(orderMain.getOrderID());
                orderMain.setOrderDetail(orderDetailList);
            }
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return orderMainList;
    }

    @Override
    public Order_Main findMyPayOrder(Member loginUser, String orderID) {
        return orderDao.findConfirmOrder(loginUser.getMemberID(), orderID);
    }

    @Override
    public int deleteMyPayOrder(String orderID) {
        int deleteOrderMainCount=orderDao.deleteMyPayOrderDetail(orderID);
        if(deleteOrderMainCount>0){
            return orderDao.deleteMyPayOrderMain(orderID);
        }else {
            return 0;
        }
    }

    /*用来获取存储进入数据库的最后一条记录的订单编号,进行处理，在数字位上加1*/
    public String getOrderIDAddOne(){
        String lastOrderID = orderDao.getLastOrderID();
        String orderID=null;
        if(lastOrderID==null){
            orderID="O000000001";
        }else{
            String substring = lastOrderID.substring(1, 10);
            int i = Integer.parseInt(substring);
            i++;
            String s = String.format("%09d", i);
            orderID = String.valueOf(new StringBuffer().append("O").append(s));
        }
        return orderID;
    }
    /*用来获取注册日期的方法*/
    public String getDateTime(){
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }
}
