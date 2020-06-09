package com.bianyiit.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class Order_Main implements Serializable {
    private String orderID; /*订单ID*/
    private String orderTime; /*下单时间*/
    private double totalMoney; /*总金额*/
    private String paymentTime; /*付款时间*/
    private String orderStatus; /*订单状态*/
    private String receiveName; //收货人姓名
    private String receivePhone; //收货人手机号码
    private String receiveAddress; //收货地址
    private String receiveDetailAddress; //收货详细地址
    private String memberID; /*用户ID*/
    private List<Order_Detail> orderDetail; /*订单详细信息*/
}
