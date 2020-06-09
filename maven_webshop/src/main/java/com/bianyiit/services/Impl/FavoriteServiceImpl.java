package com.bianyiit.services.Impl;

import com.bianyiit.dao.FavoriteDao;
import com.bianyiit.dao.Impl.FavoriteDaoImpl;
import com.bianyiit.domain.Favorite;
import com.bianyiit.domain.FavoriteRank;
import com.bianyiit.domain.Goods;
import com.bianyiit.domain.Member;
import com.bianyiit.services.FavoriteService;

import java.util.List;

public class FavoriteServiceImpl implements FavoriteService {
    FavoriteDao favoriteDao=new FavoriteDaoImpl();
    //判断用户是否已经收藏了此产品
    @Override
    public Favorite isFavorite(String goodsID, int item, String memberID) {
        return favoriteDao.isFavorite(goodsID, item, memberID);
    }
    //处理取消收藏功能
    @Override
    public Integer removeFavorite(String goodsID, int item, String memberID) {
        return favoriteDao.removeFavorite(goodsID, item, memberID);
    }

    /*获取收藏排行榜*/
    @Override
    public List<FavoriteRank> findFavoriteRank() {
        return favoriteDao.findFavoriteRank();
    }

    /*获取我的收藏列表*/
    @Override
    public List<FavoriteRank> myFavoriteList(Member loginUser) {
        return favoriteDao.myFavoriteList(loginUser);
    }

    //获取收藏次数
    @Override
    public Integer findFavoriteCount(String goodsID, int item) {
        return favoriteDao.findFavoriteCount(goodsID,item);
    }

    //处理添加收藏功能
    @Override
    public Integer addFavorite(String goodsID, int item, String memberID) {
        return favoriteDao.addFavorite(goodsID, item, memberID);
    }
}
