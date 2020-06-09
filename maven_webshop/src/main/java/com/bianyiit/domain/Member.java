package com.bianyiit.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class Member implements Serializable {
    private String memberID;        //会员编号，主键，由字母M+9位数字组成，自增长,长度10
    private String memberPhone;     //会员手机号 长度11 非空
    @JsonIgnore
    private String memberPassword;  //会员登录密码 长度16 非空
    private String memberEmail;     //会员邮箱 长度16
    private String memberPayment;   //会员支付密码 长度6
    private int    member_ReceiveMsg; //会员收货信息表ID 长度40
    private String memberNickname;  //会员昵称 长度20
    private String memberAddress;   //会员居住地 长度16
    private String memberSex;       //会员性别 长度6 "famale" or "male"
    private String memberShip;      //会员身份 长度8
    private Date   memberBirthday;  //会员生日 日期类型
    private String memberBlog;      //会员博客地址
    private String memberInterests; //会员兴趣爱好
    private String memberIntroduce; //会员自我介绍
    private String memberRegDate;   //会员注册时间
    private String memberGrade;     //会员等级
    private int    isActive;        //是否激活 0未激活 1已激活
    private List<ReceiveMsg> receiveMsg;  //收货详细信息
}
