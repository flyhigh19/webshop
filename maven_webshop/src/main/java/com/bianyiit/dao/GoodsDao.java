package com.bianyiit.dao;

import com.bianyiit.domain.Goods;
import com.bianyiit.domain.GoodsDetail;
import com.bianyiit.domain.Goods_img;

import java.util.List;

public interface GoodsDao {
    /*根据商品的分类ID得出该类下商品的总页数*/
    int findCountByGoodsID(String goodsID, String wd);
    /*根据商品的分类ID，用户输入的搜索关键字，起始页数和每页条数得出所有的分类商品列表*/
    List<Goods> findList(String goodsID, int start, int pageSize, String wd);
    /*根据商品的分类编号和商品的编号查询出商品的基本信息*/
    Goods findGoodsBasicInformation(String goodsID, String item);
    /*根据商品分类ID和商品编号去数据中查询出商品的所有相关图片信息*/
    List<Goods_img> findGoodsImgInformation(int tid);
    /*根据商品分类ID和商品编号去数据中查询出商品的所有相关图片详细信息*/
    GoodsDetail findGoodsDetailInformation(int did);
    /*根据价格的升序查询出所有商品列表信息*/
    List<Goods> findListAscPrice(String goodsID, int start, int pageSize, String wd, String price);
}
