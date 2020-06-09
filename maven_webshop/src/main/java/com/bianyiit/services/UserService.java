package com.bianyiit.services;

import com.bianyiit.domain.Business;
import com.bianyiit.domain.Employee;
import com.bianyiit.domain.Member;
import com.bianyiit.domain.ReceiveMsg;

import java.util.List;

public interface UserService {
    /*注册*/
    int regist(Member member);
    /*登录*/
    Member login(Member member);
    /*是否包含相同的手机号码*/
    boolean includeSamePhone(String memberPhone);
    /*是否存在相同的用户名*/
    boolean isExistEmployeeUser(String employeeuser);
    /*是否存在相同的邮箱*/
    boolean isExistEmail(String email);
    /*是否存在相同的法人手机号码*/
    boolean isExistPhone(String legalPhone);
    /*企业用户注册*/
    int business_reigst(Business business, Employee employee);
    /*从数据库取出会员编号*/
    String getMemberID(String memberPhone);
    /*从数据库中取出会员昵称*/
    String getmemberNickname(String memberID);
    /*从数据库中取出会员收货地址*/
    List<ReceiveMsg> getReceiveMsg(String memberID);
}
