package com.bianyiit.domain;

import java.io.Serializable;

/*购物项实体类*/
public class BuyerItem implements Serializable {
    private static final long serialVersionUID = 1L;
    private Goods goods; /*商品*/
    private Integer goodsNum=1; /*用户选购的商品数量*/
    private Boolean isHave=true; /*是否有货*/
    private String isFavorite; /*商品是否被收藏*/
    private Float yufei=0f;  /*商品运费*/

    public String getIsFavorite() {
        return isFavorite;
    }

    public void setIsFavorite(String isFavorite) {
        this.isFavorite = isFavorite;
    }

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    public Integer getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(Integer goodsNum) {
        this.goodsNum = goodsNum;
    }

    public Boolean getHave() {
        return isHave;
    }

    public void setHave(Boolean have) {
        isHave = have;
    }

    public Float getYufei() {
        return yufei;
    }

    public void setYufei(Float yufei) {
        this.yufei = yufei;
    }

    @Override
    public boolean equals(Object obj) {
        if(this==obj){
            return true;
        }
        if(obj==null){
            return false;
        }
        if(getClass()!=obj.getClass()){
            return false;
        }
        BuyerItem other= (BuyerItem) obj;
        if(goods==null){
            if(other.goods!=null) {
                return false;
            }
        }else if(!(goods.getGoodsID().equals(other.goods.getGoodsID())&&(goods.getItem().equals(other.goods.getItem())))){
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int prime=31;
        int result=1;
        result=prime*result+((goods==null)?0:goods.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "BuyerItem{" +
                "goods=" + goods +
                ", goodsNum=" + goodsNum +
                ", isHave=" + isHave +
                ", yufei=" + yufei +
                '}';
    }
}
