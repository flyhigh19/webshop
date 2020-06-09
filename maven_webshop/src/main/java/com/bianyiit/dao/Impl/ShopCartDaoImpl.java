package com.bianyiit.dao.Impl;

import com.bianyiit.dao.ShopCartDao;
import com.bianyiit.domain.BuyerItem;
import com.bianyiit.domain.Goods;
import com.bianyiit.domain.GoodsDetail;
import com.bianyiit.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class ShopCartDaoImpl implements ShopCartDao {
    JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());
    /*用户登陆时的添加购物车到数据库*/
    @Override
    public void addShopCart(String memberID, String goodsID, String item, String goodsName,String factory, double price, int goodsNum, String imgSrc) {
        String sql="insert into mycart values(?,?,?,?,?,?,?,?)";
        jdbcTemplate.update(sql,memberID,goodsID,item,goodsName,factory,price,goodsNum,imgSrc);
    }

    @Override
    public GoodsDetail findGoodsBasicInformation(String goodsID, String item) {
        String sql="SELECT * FROM goods_detail WHERE did=(SELECT did FROM goods WHERE goodsID=? AND item=?)";
        try {
            return jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<>(GoodsDetail.class),goodsID,item);
        } catch (DataAccessException e) {
            /*e.printStackTrace();*/
            return null;
        }
    }

    @Override
    public int findShopCartGoods(String memberID, String goodsID, String item) {
        String sql="select count(*) from mycart where memberID=? and goodsID=? and item=?";
        return jdbcTemplate.queryForObject(sql,Integer.class,memberID,goodsID,item);
    }

    @Override
    public int findShopCartGoodsNum(String memberID, String goodsID, String item) {
        String sql="select goodsNum from mycart where memberID=? and goodsID=? and item=?";
        return jdbcTemplate.queryForObject(sql,Integer.class,memberID,goodsID,item);
    }

    @Override
    public void updateShopCart(String memberID, String goodsID, String item, int goodsNum) {
        String sql="update mycart set goodsNum=? where memberID=? and goodsID=? and item=?";
        jdbcTemplate.update(sql,goodsNum,memberID,goodsID,item);
    }

    @Override
    public List<BuyerItem> findShopCartBuyerItem(String memberID) {
        String sql="select * from mycart where memberID=?";
        return jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(BuyerItem.class),memberID);
    }

    @Override
    public List<Goods> findShopCartGoods(String memberID) {
        String sql="select * from mycart where memberID=?";
        return jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(Goods.class),memberID);
    }

    @Override
    public Integer deleteGoodsInCart(String goodsID, String item) {
        String sql="delete from mycart where goodsID=? and item=?";
        int update = 0;
        try {
            update = jdbcTemplate.update(sql,goodsID,item);
        } catch (DataAccessException e) {
            //e.printStackTrace();
        }
        return update;
    }

}
