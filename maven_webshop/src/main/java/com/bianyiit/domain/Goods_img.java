package com.bianyiit.domain;

import lombok.Data;

import java.io.Serializable;
@Data
public class Goods_img implements Serializable {
    private int mid;
    private int tid;
    private String bigPic;//详情商品大图
    private String smallPic;//详情商品小图
}
