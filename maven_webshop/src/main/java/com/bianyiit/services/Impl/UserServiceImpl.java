package com.bianyiit.services.Impl;

import com.bianyiit.dao.UserDao;
import com.bianyiit.dao.Impl.UserDaoImpl;
import com.bianyiit.domain.Business;
import com.bianyiit.domain.Employee;
import com.bianyiit.domain.Member;
import com.bianyiit.domain.ReceiveMsg;
import com.bianyiit.services.UserService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class UserServiceImpl implements UserService {
    UserDao userDao=new UserDaoImpl();

    @Override
    public int regist(Member member) {
        /*给新注册的会员提供一个会员编号*/
        member.setMemberID(getMemberIDAddOne());
        /*往member中封装一个会员注册时间memberRegDate*/
        member.setMemberRegDate(getDateTime());
        /*往member中封装一个会员刚注册的等级*/
        member.setMemberGrade("普通会员");
        /*往member中封装一个收货地址的外键*/
        int member_ReceiveMsg=userDao.getLastMember_ReceiveMsg();
        if(member_ReceiveMsg==0){
            member_ReceiveMsg=1;
        }else{
            member.setMember_ReceiveMsg(member_ReceiveMsg+1);
        }
        int update = userDao.add(member);
        return update;
    }

    /*用来获取注册日期的方法*/
    public String getDateTime(){
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    /*用来获取存储进入数据库的最后一条记录的会员编号,进行处理，在数字位上加1*/
    public String getMemberIDAddOne(){
        String lastMemberID = userDao.getLastMemberID();
        String MemberID=null;
        if(lastMemberID==null){
            MemberID="M000000001";
        }else{
            String substring = lastMemberID.substring(1, 10);
            int i = Integer.parseInt(substring);
            i++;
            String s = String.format("%09d", i);
            MemberID = String.valueOf(new StringBuffer().append("M").append(s));
        }
        return MemberID;
    }

    @Override
    /*用来断用户注册的手机号在数据库中是否存在*/
    public boolean includeSamePhone(String memberPhone){
        List<Member> members = userDao.includeSamePhone(memberPhone);
        if(members!=null&&!members.isEmpty()){
            return false;
        }else {
            return true;
        }
    }

    @Override
    public boolean isExistEmployeeUser(String employeeuser) {
        List<Employee> employees = userDao.isExistEmployeeUser(employeeuser);
        if(employees!=null&&!employees.isEmpty()){
            return false;
        }else {
            return true;
        }
    }

    @Override
    public boolean isExistEmail(String email) {
        List<Business> businesses = userDao.isExistEmail(email);
        if(businesses!=null&&!businesses.isEmpty()){
            return false;
        }else {
            return true;
        }
    }

    @Override
    public boolean isExistPhone(String legalPhone) {
        List<Business> businesses = userDao.isExistPhone(legalPhone);
        if(businesses!=null&&!businesses.isEmpty()){
            return false;
        }else {
            return true;
        }
    }

    @Override
    public int business_reigst(Business business, Employee employee) {
        business.setBusinessID(getBusinessIDAddOne());
        int updateBusiness = userDao.addBusiness(business);
        if(updateBusiness>0){
            employee.setEmployeeID(getEmployeeIDAddOne());
            employee.setBusinessID(business.getBusinessID());
            int updateEmployee = userDao.addEmployee(employee);
            return updateEmployee;
        }
        return 0;
    }

    @Override
    public String getMemberID(String memberPhone) {
        UserDao userDao=new UserDaoImpl();
        return userDao.getMemberID(memberPhone);
    }

    @Override
    public List<ReceiveMsg> getReceiveMsg(String memberID) {
        UserDao userDao=new UserDaoImpl();
        return userDao.getReceiveMsg(memberID);
    }

    @Override
    public String getmemberNickname(String memberID) {
        UserDao userDao=new UserDaoImpl();
        return userDao.getmemberNickname(memberID);
    }

    /*用来获取存储进入数据库的最后一条记录的企业员工用户编号,进行处理，在数字位上加1*/
    public String getEmployeeIDAddOne(){
        String lastEmployeeID = userDao.getLastEmployeeID();
        String EmployeeID=null;
        if(lastEmployeeID==null){
            EmployeeID="E000000001";
        }else{
            String substring = lastEmployeeID.substring(1, 10);
            int i = Integer.parseInt(substring);
            i++;
            String s = String.format("%09d", i);
            EmployeeID = String.valueOf(new StringBuffer().append("E").append(s));
        }
        return EmployeeID;
    }

    /*用来获取存储进入数据库的最后一条记录的企业用户编号,进行处理，在数字位上加1*/
    public String getBusinessIDAddOne(){
        String lastBusinessID = userDao.getLastBusinessID();
        String BusinessID=null;
        if(lastBusinessID==null){
            BusinessID="B000000001";
        }else{
            String substring = lastBusinessID.substring(1, 10);
            int i = Integer.parseInt(substring);
            i++;
            String s = String.format("%09d", i);
            BusinessID = String.valueOf(new StringBuffer().append("B").append(s));
        }
        return BusinessID;
    }

    @Override
    public Member login(Member member) {
        Member true_member = userDao.getPassword(member.getMemberPhone());
        if(member.getMemberPassword().equals(true_member.getMemberPassword())){
            return true_member;
        }else {
            return null;
        }
    }
}
