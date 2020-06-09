package com.bianyiit.dao;

import com.bianyiit.domain.*;

import java.util.List;

public interface OrderDao {
    int findGoodsDid(String goodsID, String item);
    GoodsDetail findGoodsDetail(int goodsDid);
    Goods findGoods(String goodsID, String item);
    int addReceiveMsg(ReceiveMsg receiveMsg, Member loginUser);
    int getMember_ReceiveMsg(String memberID);
    int updateReceiveMsg(ReceiveMsg receiveMsg);
    int deleteReceiveMsg(int receiveID);
    ReceiveMsg findReceiveMsgIsDefault(int receiveID);
    List<ReceiveMsg> findReceiveMsg(int member_receiveAddress);
    ReceiveMsg findReceiveMsg(String memberID);
    String getLastOrderID();
    int addOrderMain(Order_Main orderMain);
    void addOrderDetail(Order_Detail orderDetail);
    void deleteShopCart(String memberID);
    List<Order_Main> findConfirmOrderMain(String memberID);
    List<Order_Detail> findConfirmOrderDetail(String orderID);
    Order_Main findConfirmOrder(String memberID,String orderID);
    int deleteMyPayOrderDetail(String orderID);
    int deleteMyPayOrderMain(String orderID);
}
