package com.bianyiit.services;

import com.bianyiit.domain.Member;

import java.util.Map;

public interface PaymentService {
    /*获取微信支付二维码*/
    Map<String, String> getWeiXinPayCode(Member member);
    /*根据订单号来查找订单，并生成微信支付二维码*/
    Map<String, String> getWeiXinPayCode(Member loginUser,String orderID);
}
