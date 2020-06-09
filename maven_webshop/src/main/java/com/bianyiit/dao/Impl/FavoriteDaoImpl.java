package com.bianyiit.dao.Impl;

import com.bianyiit.dao.FavoriteDao;
import com.bianyiit.domain.Favorite;
import com.bianyiit.domain.FavoriteRank;
import com.bianyiit.domain.Goods;
import com.bianyiit.domain.Member;
import com.bianyiit.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Date;
import java.util.List;

public class FavoriteDaoImpl implements FavoriteDao {
    JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());

    //判断用户是否已经收藏了此产品
    @Override
    public Favorite isFavorite(String goodsID, int item, String memberID) {
        String sql="select * from goods_favorite where goodsID=? and item=? and memberID=?";
        Favorite favorite = null;
        try {
            favorite = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Favorite.class), goodsID, item,memberID);
        } catch (DataAccessException e) {
            //e.printStackTrace();
        }
        return favorite;
    }
    //处理点击收藏功能
    @Override
    public Integer addFavorite(String goodsID, int item, String memberID) {
        String sql="insert into goods_favorite values(?,?,?,?)";
        try {
            return jdbcTemplate.update(sql,goodsID,item,new Date(),memberID);
        } catch (DataAccessException e) {
            return 0;
        }
    }
    //获取请求次数
    @Override
    public Integer findFavoriteCount(String goodsID, int item) {
        String sql="select count(*) from goods_favorite where goodsID=? and item=?";
        return jdbcTemplate.queryForObject(sql, Integer.class,goodsID,item);
    }
    //处理取消收藏功能
    @Override
    public Integer removeFavorite(String goodsID, int item, String memberID) {
        String sql="delete from goods_favorite where goodsID=? and item=? and memberID=?";
        int update = 0;
        try {
            update = jdbcTemplate.update(sql,goodsID,item,memberID);
        } catch (DataAccessException e) {
            //e.printStackTrace();
        }
        return update;
    }

    @Override
    public List<FavoriteRank> findFavoriteRank() {
        String sql="SELECT * FROM goods,(SELECT goodsID,item,COUNT(*) rank FROM goods_favorite GROUP BY goodsID,item ORDER BY COUNT(*) DESC) gf WHERE goods.goodsID=gf.goodsID AND goods.item=gf.item ORDER BY gf.rank DESC";
        return jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(FavoriteRank.class));
    }

    @Override
    public List<FavoriteRank> myFavoriteList(Member loginUser) {
        String sql="SELECT * FROM goods,(SELECT memberID,goodsID,item FROM goods_favorite WHERE memberID=?) m WHERE goods.goodsID=m.goodsID AND goods.item=m.item;";
        try {
            return jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(FavoriteRank.class),loginUser.getMemberID());
        } catch (DataAccessException e) {
            return null;
        }
    }
}
