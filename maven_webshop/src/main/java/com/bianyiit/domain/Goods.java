package com.bianyiit.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class Goods implements Serializable {
    /*商品基本描述信息*/
    private String goodsID;	 //商品类型编码
    private String typeName;	//商品类型名称
    private String item;	//商品编号
    private String goodsName; //商品名称
    private double price; //商品价格
    private String description; //商品描述信息
    private String imgsrc; //商品图片地址
    private String shelfTime; //商品上架时间
    private String factory; //商品厂家
    private String status; //商品状态
    private String businessID; //商家编号
    private int did; //关联商品详情表
    private int tid; //关联商品详情图片列表

    private List<Goods_img> goodsImgList;//商品详情图片列表
    private GoodsDetail goodsDetail; //商品详细描述数据信息
}
