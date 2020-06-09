package com.bianyiit.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/*购物车实体类*/
@Data
public class ShopCart implements Serializable {

    private static final long serialVersionUID = 1L;
    //商品结果集
    private List<BuyerItem> items = new ArrayList<BuyerItem>();

    //添加购物项到购物车
    public void addItem(BuyerItem item){
        //判断是否包含同款商品
        if (items.contains(item)) {
        //如果存在，在该商品上直接追加数量
            for (BuyerItem buyerItem : items) {
                if (buyerItem.equals(item)) {
                    buyerItem.setGoodsNum(item.getGoodsNum() + buyerItem.getGoodsNum());
                }
            }
        }else {
            //如果不存在直接将该商品添加到购物车列表中
            items.add(item);
        }
    }

    //获取商品数量
    @JsonIgnore
    public Integer getGoodsTotalNum(){
        Integer result=0;
        //遍历集合，得到所有的商品总数
        for (BuyerItem buyerItem : items) {
            result=result+buyerItem.getGoodsNum();
        }
        return result;
    }

    //计算所有商品金额
    @JsonIgnore
    public Double getGoodsTotalPrice(){
        double result= 0;
        for (BuyerItem buyerItem : items) {
            double discountPrice=buyerItem.getGoods().getGoodsDetail().getDiscountPrice();
            /*如果存在折扣价格，那么计算折扣价格，如果没有，就计算商品原价*/
            if((discountPrice<0.0001)&&(discountPrice>-0.0001)){
                result=result+buyerItem.getGoodsNum()*buyerItem.getGoods().getPrice();
            }else{
                result=result+buyerItem.getGoodsNum()*buyerItem.getGoods().getGoodsDetail().getDiscountPrice();
            }
        }
        return result;
    }

    /*计算运费*/
    @JsonIgnore
    public Float getGoodsYuFei(){
        Float result=0f;
        if(getGoodsTotalPrice()<80){
            result=5f;
        }
        return result;
    }

    /*计算商品总价=商品金额+运费*/
    @JsonIgnore
    public double getTotalPrice(){
        return getGoodsTotalPrice()+getGoodsYuFei();
    }
}
