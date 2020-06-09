package com.bianyiit.services;

import com.bianyiit.domain.Favorite;
import com.bianyiit.domain.FavoriteRank;
import com.bianyiit.domain.Goods;
import com.bianyiit.domain.Member;

import java.util.List;

public interface FavoriteService {
    //获取收藏次数
    Integer findFavoriteCount(String goodsID, int item);
    //处理点击收藏功能
    Integer addFavorite(String goodsID, int item ,String memberID);
    //判断用户是否已经收藏了此产品
    Favorite isFavorite(String goodsID, int item ,String memberID);
    //处理取消收藏功能
    Integer removeFavorite(String goodsID, int item ,String memberID);
    /*获取收藏排行榜*/
    List<FavoriteRank> findFavoriteRank();
    /*获取用户收藏列表*/
    List<FavoriteRank> myFavoriteList(Member loginUser);
}
