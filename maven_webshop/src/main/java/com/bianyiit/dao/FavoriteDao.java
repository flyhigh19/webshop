package com.bianyiit.dao;

import com.bianyiit.domain.Favorite;
import com.bianyiit.domain.FavoriteRank;
import com.bianyiit.domain.Goods;
import com.bianyiit.domain.Member;

import java.util.List;

public interface FavoriteDao {
    Favorite isFavorite(String goodsID, int item, String memberID);

    Integer addFavorite(String goodsID, int item, String memberID);

    Integer findFavoriteCount(String goodsID, int item);

    Integer removeFavorite(String goodsID, int item, String memberID);

    List<FavoriteRank> findFavoriteRank();

    List<FavoriteRank> myFavoriteList(Member loginUser);
}
