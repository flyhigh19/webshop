package com.bianyiit.dao;

import com.bianyiit.domain.BuyerItem;
import com.bianyiit.domain.Goods;
import com.bianyiit.domain.GoodsDetail;
import com.bianyiit.domain.ShopCart;

import java.util.ArrayList;
import java.util.List;

public interface ShopCartDao {
    /*添加购物车操作*/
    void addShopCart(String memberID,String goodsID,String item,String goodsName,String factory,double price,int goodsNum,String imgSrc);
    /*查询购物车中商品的具体信息*/
    GoodsDetail findGoodsBasicInformation(String goodsID, String item);
    /*查询数据库中是否存在该商品*/
    int findShopCartGoods(String memberID, String goodsID, String item);
    /*得到该商品的购买数量*/
    int findShopCartGoodsNum(String memberID, String goodsID, String item);
    /*更新商品数量*/
    void updateShopCart(String memberID, String goodsID, String item, int goodsNum);
    /*从数据库中取出会员添加好的购物车*/
    List<BuyerItem> findShopCartBuyerItem(String memberID);
    /*从数据库中取出会员添加好的购物车*/
    List<Goods> findShopCartGoods(String memberID);
    /*删除数据库中的商品*/
    Integer deleteGoodsInCart(String goodsID,String item);
}
