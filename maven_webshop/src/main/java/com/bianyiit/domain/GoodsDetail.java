package com.bianyiit.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class GoodsDetail implements Serializable {
    private int did;
    private String subTitle;   /*商品副标题*/
    private double discountPrice; /*商品折扣价格*/
    private String discountDesc; /*商品折扣描述*/
    private int evalCount; /*评论人数*/
    private int repertory; /*库存数量*/
}
