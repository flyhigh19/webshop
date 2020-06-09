package com.bianyiit.domain;
import lombok.Data;

import java.io.Serializable;

@Data
public class ReceiveMsg implements Serializable {
    private int receiveID;  //收货表ID
    private int member_ReceiveAddress; //作为外键连接会员基本信息表的收货信息字段
    private String receiveName; //收货人姓名
    private String receivePhone; //收货人手机号码
    private String receiveAddress; //收货地址
    private String receiveDetailAddress; //收货详细地址
    private int isDefault; //是否设置成默认地址
}
