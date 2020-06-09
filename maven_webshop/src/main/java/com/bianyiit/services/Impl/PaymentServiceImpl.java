package com.bianyiit.services.Impl;

import com.bianyiit.dao.Impl.OrderDaoImpl;
import com.bianyiit.dao.OrderDao;
import com.bianyiit.domain.Member;
import com.bianyiit.domain.Order_Detail;
import com.bianyiit.domain.Order_Main;
import com.bianyiit.services.OrderService;
import com.bianyiit.services.PaymentService;
import com.github.wxpay.sdk.MyConfig;
import com.github.wxpay.sdk.WXPay;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PaymentServiceImpl implements PaymentService {

    @Override
    public Map<String, String> getWeiXinPayCode(Member loginUser) {
        OrderService orderService = new OrderServiceImpl();
        /*拿到商品中的所有订单数据*/
        List<Order_Main> orderMainList = orderService.findConfirmOrder(loginUser);
        if(orderMainList==null||orderMainList.size()==0){
            return null;
        }else{
            /*得到用户最后提交的订单*/
            Order_Main orderMain = orderMainList.get(orderMainList.size() - 1);
            //获取微信支付Url
            MyConfig config=new MyConfig();
            try {
                WXPay wxPay=new WXPay(config);
                Map<String,String> map=new HashMap();
                BigDecimal payMoney= new BigDecimal(String.valueOf(orderMain.getTotalMoney()));
                payMoney = payMoney.multiply(new BigDecimal("100"));
                payMoney = payMoney.setScale(0, BigDecimal.ROUND_UP);//取整
                map.put("body",orderMain.getOrderTime()+"  网上商城购物总金额");//商品描述
                map.put("out_trade_no",orderMain.getOrderID());//订单号
                map.put("total_fee", String.valueOf(payMoney));//金额
                map.put("spbill_create_ip","127.0.0.1");//终端IP
                map.put("notify_url","http://www.baidu.com");//回调地址
                map.put("trade_type","NATIVE");//交易类型
                Map<String, String> result = wxPay.unifiedOrder(map);
                return result;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    @Override
    public Map<String, String> getWeiXinPayCode(Member loginUser, String orderID) {
        /*拿到商品中的所有订单数据*/
        OrderDao orderDao = new OrderDaoImpl();
        Order_Main orderMain = orderDao.findConfirmOrder(loginUser.getMemberID(),orderID);
        //获取微信支付Url
        MyConfig config=new MyConfig();
        try {
            WXPay wxPay=new WXPay(config);
            Map<String,String> map=new HashMap();
            BigDecimal payMoney= new BigDecimal(String.valueOf(orderMain.getTotalMoney()));
            payMoney = payMoney.multiply(new BigDecimal("100"));
            payMoney = payMoney.setScale(0, BigDecimal.ROUND_UP);//取整
            map.put("body",orderMain.getOrderTime()+"  网上商城购物总金额");//商品描述
            map.put("out_trade_no",orderMain.getOrderID());//订单号
            map.put("total_fee", String.valueOf(payMoney));//金额
            map.put("spbill_create_ip","127.0.0.1");//终端IP
            map.put("notify_url","http://www.baidu.com");//回调地址
            map.put("trade_type","NATIVE");//交易类型
            Map<String, String> result = wxPay.unifiedOrder(map);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
