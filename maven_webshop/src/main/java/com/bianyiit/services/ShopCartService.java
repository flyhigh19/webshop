package com.bianyiit.services;

import com.bianyiit.domain.Goods;
import com.bianyiit.domain.Member;
import com.bianyiit.domain.ShopCart;

public interface ShopCartService {
    void addShopCart(ShopCart shopCart, Member member);
    Goods findGoodsMsg(String goodsID,String item);
    ShopCart findGoodsMsg(Member member);
    Integer deleteGoodsInCart(String goodsID,String item);
    void updateShopCart(ShopCart shopCart, Member loginUser);
}
