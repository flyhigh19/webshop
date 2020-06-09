package com.bianyiit.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class Business implements Serializable {
    private String businessID; // 公司编码 主键
    private String businessName; //公司名称
    private String businessAddress; //公司地址
    private String businessPhone; //公司电话
    private String legalName; //法人姓名
    private String legalPhone; //法人联系方式
    private String businessEmail; //法人邮箱
    private int    employeeNum;	//员工人数
    private String businessType; //经营类别
    private String businessLicense; //营业执照号码
    private String bankAccount;	//银行开户卡号
}
