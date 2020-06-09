package com.bianyiit.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class FavoriteRank implements Serializable {
    private String goodsID;	 //商品类型编码
    private String typeName;	//商品类型名称
    private String item;	//商品编号
    private String goodsName; //商品名称
    private double price; //商品价格
    private String description; //商品描述信息
    private String imgsrc; //商品图片地址
    private String shelfTime; //商品上架时间
    private String factory; //商品厂家
    private Integer rank;  //商城收藏次数
}
