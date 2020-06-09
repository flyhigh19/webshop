package com.bianyiit.domain;

import lombok.Data;

import java.io.Serializable;
@Data
public class Order_Detail implements Serializable {
    private String orderID; /*订单ID*/
    private String goodsID;	 //商品类型编码
    private String item;	//商品编号
    private double price; //商品价格
    private Integer goodsNum; //商品数量
    private String factory; //商品厂家
    private String imgsrc; //商品图片地址
    private String description; //商品描述信息
    private double discountPrice; /*商品折扣价格*/
    private Float yufei;  /*运费*/
}
