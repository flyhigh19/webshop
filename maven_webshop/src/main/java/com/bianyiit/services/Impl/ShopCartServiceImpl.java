package com.bianyiit.services.Impl;

import com.bianyiit.dao.GoodsDao;
import com.bianyiit.dao.Impl.GoodsDaoImpl;
import com.bianyiit.dao.Impl.ShopCartDaoImpl;
import com.bianyiit.dao.ShopCartDao;
import com.bianyiit.domain.*;
import com.bianyiit.services.ShopCartService;

import java.util.List;

public class ShopCartServiceImpl implements ShopCartService {
    @Override
    public void addShopCart(ShopCart shopCart, Member member) {
        ShopCartDao shopCartDao=new ShopCartDaoImpl();
        for (BuyerItem item : shopCart.getItems()) {
            /*如果数据库中已经存在该商品，取出该商品，在该商品的数量上加一，然后重写存储进数据库中*/
            Integer flag=shopCartDao.findShopCartGoods(member.getMemberID(), item.getGoods().getGoodsID(), item.getGoods().getItem());
            if(flag>0){
                /*得到该商品的数量*/
                int goodsNum = shopCartDao.findShopCartGoodsNum(member.getMemberID(), item.getGoods().getGoodsID(), item.getGoods().getItem());
                goodsNum=goodsNum+item.getGoodsNum();
                /*更新商品数量*/
                shopCartDao.updateShopCart(member.getMemberID(), item.getGoods().getGoodsID(), item.getGoods().getItem(),goodsNum);
            }else{
                /*如果数据库中不存在该商品，则直接添加至数据库中*/
                if(item.getGoods().getGoodsDetail()==null){
                    shopCartDao.addShopCart(
                            member.getMemberID(),
                            item.getGoods().getGoodsID(),
                            item.getGoods().getItem(),
                            item.getGoods().getGoodsName(),
                            item.getGoods().getFactory(),
                            item.getGoods().getPrice(),
                            item.getGoodsNum(),
                            item.getGoods().getImgsrc());
                }else{
                    double discountPrice=item.getGoods().getGoodsDetail().getDiscountPrice();
                    /*是否存在折扣价格*/
                    if((discountPrice<0.0001)&&(discountPrice>-0.0001)){
                        //将商品保存到数据库中
                        shopCartDao.addShopCart(
                                member.getMemberID(),
                                item.getGoods().getGoodsID(),
                                item.getGoods().getItem(),
                                item.getGoods().getGoodsName(),
                                item.getGoods().getFactory(),
                                item.getGoods().getPrice(),
                                item.getGoodsNum(),
                                item.getGoods().getImgsrc());
                    }else{
                        System.out.println(789);
                        shopCartDao.addShopCart(
                                member.getMemberID(),
                                item.getGoods().getGoodsID(),
                                item.getGoods().getItem(),
                                item.getGoods().getGoodsName(),
                                item.getGoods().getFactory(),
                                item.getGoods().getGoodsDetail().getDiscountPrice(),
                                item.getGoodsNum(),
                                item.getGoods().getImgsrc());
                    }
                }
            }
        }
    }

    @Override
    public Goods findGoodsMsg(String goodsID, String item) {
        GoodsDao goodsDao=new GoodsDaoImpl();
        Goods goods = goodsDao.findGoodsBasicInformation(goodsID, item);
        ShopCartDao shopCartDao=new ShopCartDaoImpl();
        GoodsDetail goodsDetail=shopCartDao.findGoodsBasicInformation(goodsID, item);
        goods.setGoodsDetail(goodsDetail);
        return goods;
    }

    @Override
    public ShopCart findGoodsMsg(Member member) {
        ShopCartDao shopCartDao=new ShopCartDaoImpl();
        ShopCart shopCart = new ShopCart();
        List<BuyerItem> items = shopCartDao.findShopCartBuyerItem(member.getMemberID());
        List<Goods> goodsList = shopCartDao.findShopCartGoods(member.getMemberID());
        for (int i = 0; i < items.size(); i++) {
            items.get(i).setGoods(goodsList.get(i));
        }
        shopCart.setItems(items);
        return shopCart;
    }

    @Override
    public Integer deleteGoodsInCart(String goodsID, String item) {
        ShopCartDao shopCartDao=new ShopCartDaoImpl();
        return shopCartDao.deleteGoodsInCart(goodsID,item);
    }

    @Override
    public void updateShopCart(ShopCart shopCart, Member member) {
        ShopCartDao shopCartDao=new ShopCartDaoImpl();
        for (BuyerItem item : shopCart.getItems()) {
            shopCartDao.updateShopCart(member.getMemberID(), item.getGoods().getGoodsID(), item.getGoods().getItem(),item.getGoodsNum());
        }
    }
}
