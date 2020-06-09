package com.bianyiit.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class Employee implements Serializable {
    private String EmployeeID;	//职员编号
    private String EmployeeUser; // 员工用户名
    private String EmployeeName; // 员工姓名
    private String EmployeePwd; // 员工登录密码
    private String EmployeePhone; // 员工联系方式
    private String EmployeeAddr; // 员工家庭住址
    private String EmployeeEmail; // 员工电子邮箱
    private String Competence; // 员工权限
    private String BusinessID; // 公司编码
}
