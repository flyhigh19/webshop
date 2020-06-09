package com.bianyiit.services;

import com.bianyiit.domain.Goods;
import com.bianyiit.domain.PageBean;

public interface GoodsService {
    PageBean<Goods> findPage(String goodsID, int currenPage, int pageSize, String wd);
    Goods findGoodsDetail(String goodsID,String item);
    PageBean<Goods> findPageAscPrice(String goodsID, int currenPage, int pageSize, String wd, String price);
}
