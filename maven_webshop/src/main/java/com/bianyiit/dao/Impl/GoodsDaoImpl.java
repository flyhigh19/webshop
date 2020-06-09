package com.bianyiit.dao.Impl;

import com.bianyiit.dao.GoodsDao;
import com.bianyiit.domain.Goods;
import com.bianyiit.domain.GoodsDetail;
import com.bianyiit.domain.Goods_img;
import com.bianyiit.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class GoodsDaoImpl implements GoodsDao {
    JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public int findCountByGoodsID(String goodsID, String wd) {
        if(!wd.equals("null")&&!goodsID.equals("null")) {
            String sql="select count(Item) from goods where goodsID=? and goodsName like ?";
            return jdbcTemplate.queryForObject(sql, Integer.class, goodsID, "%" + wd + "%");
        }else if(!wd.equals("null")&& goodsID.equals("null")){
            String sql="select count(Item) from goods where goodsName like ?";
            return jdbcTemplate.queryForObject(sql, Integer.class, "%" + wd + "%");
        }else if(wd.equals("null")&&!goodsID.equals("null")){
            String sql="select count(Item) from goods where goodsID = ?";
            return jdbcTemplate.queryForObject(sql, Integer.class, goodsID);
        }else {
            String sql="select count(Item) from goods";
            return jdbcTemplate.queryForObject(sql, Integer.class);
        }
    }

    @Override
    public List<Goods> findList(String goodsID, int start, int pageSize, String wd) {
        if(!wd.equals("null")&&!goodsID.equals("null")){
            String sql="select * from goods where goodsID=? and goodsName like ? ORDER BY item limit ?,?";
            return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Goods.class), goodsID,"%"+wd+"%", start, pageSize);
        }else if(!wd.equals("null")&&goodsID.equals("null")){
            String sql="select * from goods where goodsName like ? ORDER BY item limit ?,?";
            return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Goods.class),"%"+wd+"%", start, pageSize);
        }else if(wd.equals("null")&&!goodsID.equals("null")){
            String sql="select * from goods where goodsID=? ORDER BY item limit ?,?";
            return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Goods.class),goodsID, start, pageSize);
        }else {
            String sql="select * from goods ORDER BY item limit ?,?";
            return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Goods.class),start, pageSize);
        }
    }

    @Override
    public Goods findGoodsBasicInformation(String goodsID, String item) {
        String sql="select * from goods where goodsID=? and item=?";
        return jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<>(Goods.class),goodsID,item);
    }

    @Override
    public List<Goods_img> findGoodsImgInformation(int tid) {
        String sql="select * from goods_img where tid=?";
        try {
            return jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(Goods_img.class),tid);
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public GoodsDetail findGoodsDetailInformation(int did) {
        String sql="select * from goods_detail where did=?";
        try {
            return jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<>(GoodsDetail.class),did);
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Goods> findListAscPrice(String goodsID, int start, int pageSize, String wd, String price) {
        if(!wd.equals("null")&&!goodsID.equals("null")){
            String sql="select * from goods where goodsID=? and goodsName like ? ORDER BY price ASC limit ?,?";
            return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Goods.class), goodsID,"%"+wd+"%", start, pageSize);
        }else if(!wd.equals("null")&&goodsID.equals("null")){
            String sql="select * from goods where goodsName like ? ORDER BY price ASC limit ?,?";
            return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Goods.class),"%"+wd+"%", start, pageSize);
        }else if(wd.equals("null")&&!goodsID.equals("null")){
            String sql="select * from goods where goodsID=? ORDER BY price ASC limit ?,?";
            return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Goods.class),goodsID, start, pageSize);
        }else {
            String sql="select * from goods where ORDER BY price ASC limit ?,?";
            return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Goods.class),start, pageSize);
        }
    }
}
