package com.bianyiit.dao;

import com.bianyiit.domain.*;

import java.util.List;

public interface UserDao {
    int active(String username);
    User login(String username);

    /*获取member会员表中的最后一条记录的memberID*/
    String getLastMemberID();
    /*将用户的注册信息添加至数据库中*/
    int add(Member member);
    /*用来断用户注册的手机号在数据库中是否存在*/
    List<Member> includeSamePhone(String memberPhone);
    /*根据用户的手机号去数据库中获取对应的密码*/
    Member getPassword(String memberPhone);
    /*是否存在相同的用户名*/
    List<Employee> isExistEmployeeUser(String employeeuser);
    /*是否存在相同的企业邮箱*/
    List<Business> isExistEmail(String email);
    /*用来断法人手机号码在数据库中是否存在*/
    List<Business> isExistPhone(String legalphone);
    /*获取business表中的最后一条记录的businessID*/
    String getLastBusinessID();
    /*企业用户信息添加到business中*/
    int addBusiness(Business business);
    /*将企业员工的用户信息添加到employee中*/
    int addEmployee(Employee employee);
    /*获取employee表中的最后一条记录的employeeID*/
    String getLastEmployeeID();
    /*取出会员编号*/
    String getMemberID(String memberPhone);
    /*取出会员收货地址*/
    List<ReceiveMsg> getReceiveMsg(String memberID);
    /*取出会员昵称*/
    String getmemberNickname(String memberID);
    /*取出会员收货信息地址的最后一位数字*/
    int getLastMember_ReceiveMsg();
}
