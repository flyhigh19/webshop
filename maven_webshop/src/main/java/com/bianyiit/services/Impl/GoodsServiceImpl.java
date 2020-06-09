package com.bianyiit.services.Impl;

import com.bianyiit.dao.GoodsDao;
import com.bianyiit.dao.Impl.GoodsDaoImpl;
import com.bianyiit.domain.*;
import com.bianyiit.services.GoodsService;

import java.util.List;

public class GoodsServiceImpl implements GoodsService {
    @Override
    public PageBean<Goods> findPage(String goodsID, int currenPage, int pageSize, String wd) {
        PageBean<Goods> pageBean=new PageBean<>();
        pageBean.setCurrentPage(currenPage);
        pageBean.setPageSize(pageSize);
        GoodsDao goodsDao=new GoodsDaoImpl();
        int count = goodsDao.findCountByGoodsID(goodsID,wd);
        pageBean.setTotalCount(count);
        int totalPage=count%pageSize==0?count/pageSize:count/pageSize+1;
        pageBean.setTotalPage(totalPage);
        int start=(currenPage-1)*pageSize;
        List<Goods> goodsList = goodsDao.findList(goodsID,start,pageSize,wd);
        pageBean.setList(goodsList);
        return pageBean;
    }

    @Override
    public Goods findGoodsDetail(String goodsID, String item) {
        GoodsDao goodsDao=new GoodsDaoImpl();
        /*根据商品分类ID和商品编号去数据中查询出商品基本信息*/
        Goods goods=goodsDao.findGoodsBasicInformation(goodsID,item);
        if(goods!=null){
            /*根据商品分类ID和商品编号去数据中查询出商品的所有相关图片信息*/
            List<Goods_img> goodsImgList=goodsDao.findGoodsImgInformation(goods.getTid());
            goods.setGoodsImgList(goodsImgList);
            /*根据商品分类ID和商品编号去数据中查询出商品的所有相关图片详细信息*/
            GoodsDetail goodsDetail=goodsDao.findGoodsDetailInformation(goods.getDid());
            goods.setGoodsDetail(goodsDetail);
        }
        return goods;
    }

    @Override
    public PageBean<Goods> findPageAscPrice(String goodsID, int currenPage, int pageSize, String wd, String price) {
        PageBean<Goods> pageBean=new PageBean<>();
        pageBean.setCurrentPage(currenPage);
        pageBean.setPageSize(pageSize);
        GoodsDao goodsDao=new GoodsDaoImpl();
        int count = goodsDao.findCountByGoodsID(goodsID,wd);
        pageBean.setTotalCount(count);
        int totalPage=count%pageSize==0?count/pageSize:count/pageSize+1;
        pageBean.setTotalPage(totalPage);
        int start=(currenPage-1)*pageSize;
        List<Goods> goodsList = goodsDao.findListAscPrice(goodsID,start,pageSize,wd,price);
        pageBean.setList(goodsList);
        return pageBean;
    }
}
