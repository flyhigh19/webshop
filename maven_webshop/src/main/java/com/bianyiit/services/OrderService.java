package com.bianyiit.services;

import com.bianyiit.domain.*;

import java.util.List;

public interface OrderService {
    /*根据商品分类ID和商品编号item得出商品表的did*/
    int findGoodsDid(String goodsID,String item);
    /*根据商品的did获取该商品明细表的所有商品*/
    GoodsDetail findGoodsDetail(int goodsDid);
    /*根据商品ID和商品编号item得出商品的基本信息*/
    Goods findGoods(String goodsID, String item);
    /*将用户添加的收货信息添加到数据库中*/
    int addReceiveMsg(ReceiveMsg receiveMsg, Member loginUser);
    /*更新用户收货地址*/
    int updateReceiveMsg(ReceiveMsg receiveMsg);
    /*删除用户收货地址*/
    int deleteReceiveMsg(int receiveID);
    /*更新用户默认收货地址*/
    void setDefaultAddress(int receiveID);
    /*将用户选择好的订单存储进数据库订单表中*/
    boolean confirmOrder(Member loginUser);
    /*查找用户以下的订单数据*/
    List<Order_Main> findConfirmOrder(Member loginUser);
    /*找到用户需要支付的订单*/
    Order_Main findMyPayOrder(Member loginUser, String orderID);
    /*删除订单*/
    int deleteMyPayOrder(String orderID);
}
