package com.bianyiit.dao.Impl;

import com.bianyiit.dao.UserDao;
import com.bianyiit.domain.*;
import com.bianyiit.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class UserDaoImpl implements UserDao {
    JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public int add(Member member) {
        String sql="insert into member values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        int update = jdbcTemplate.update(sql,
                member.getMemberID(),
                member.getMemberPhone(),
                member.getMemberPassword(),
                member.getMemberEmail(),
                member.getMemberPayment(),
                member.getMember_ReceiveMsg(),
                member.getMemberNickname(),
                member.getMemberAddress(),
                member.getMemberSex(),
                member.getMemberShip(),
                member.getMemberBirthday(),
                member.getMemberBlog(),
                member.getMemberInterests(),
                member.getMemberIntroduce(),
                member.getMemberRegDate(),
                member.getMemberGrade(),
                member.getIsActive());
        return update;
    }

    @Override
    public int active(String username) {
        String sql="update tab_user set status=1 where username=?";
        int update = jdbcTemplate.update(sql, username);
        return update;
    }

    @Override
    public User login(String username) {
        String sql="select * from tab_user where username=?";
        try {
            return jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<>(User.class),username);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    /*获取会员表最后插入的最后一条记录的会员编号*/
    @Override
    public String getLastMemberID() {
        String sql="select memberID from member order by memberID desc limit 1";
        try {
            return jdbcTemplate.queryForObject(sql,String.class);
        } catch (DataAccessException e) {
            //e.printStackTrace();
        }
        return null;
    }
    /*用来断用户注册的手机号在数据库中是否存在*/
    @Override
    public List<Member> includeSamePhone(String memberPhone) {
        String sql="select * from member where memberPhone=?";
        try {
            return jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(Member.class),memberPhone);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Member getPassword(String memberPhone) {
        String sql="select * from member where memberPhone=?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Member.class), memberPhone);
    }

    @Override
    public List<Employee> isExistEmployeeUser(String employeeuser) {
        String sql="select * from employee where employeeUser=?";
        try {
            return jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(Employee.class),employeeuser);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Business> isExistEmail(String email) {
        String sql="select * from business where businessEmail=?";
        try {
            return jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(Business.class),email);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Business> isExistPhone(String legalphone) {
        String sql="select * from business where legalPhone=?";
        try {
            return jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(Business.class),legalphone);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getLastBusinessID() {
        String sql="select businessID from business order by businessID desc limit 1";
        try {
            return jdbcTemplate.queryForObject(sql,String.class);
        } catch (DataAccessException e) {
            //e.printStackTrace();
        }
        return null;
    }

    @Override
    public int addBusiness(Business business) {
        String sql="insert into business values (?,?,?,?,?,?,?,?,?,?,?)";
        int update = 0;
        try {
            update = jdbcTemplate.update(sql,
                    business.getBusinessID(),
                    business.getBusinessName(),
                    business.getBusinessAddress(),
                    business.getBusinessPhone(),
                    business.getLegalName(),
                    business.getLegalPhone(),
                    business.getBusinessEmail(),
                    business.getEmployeeNum(),
                    business.getBusinessType(),
                    business.getBusinessLicense(),
                    business.getBankAccount());
        } catch (DataAccessException e) {
            //e.printStackTrace();
        }
        return update;
    }

    @Override
    public int addEmployee(Employee employee) {
        String sql="insert into employee values (?,?,?,?,?,?,?,?,?)";
        int update = 0;
        try {
            update = jdbcTemplate.update(sql,
                    employee.getEmployeeID(),
                    employee.getEmployeeUser(),
                    employee.getEmployeeName(),
                    employee.getEmployeePwd(),
                    employee.getEmployeePhone(),
                    employee.getEmployeeAddr(),
                    employee.getEmployeeEmail(),
                    employee.getCompetence(),
                    employee.getBusinessID());
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return update;
    }

    @Override
    public String getLastEmployeeID() {
        String sql="select employeeID from employee order by employeeID desc limit 1";
        try {
            return jdbcTemplate.queryForObject(sql,String.class);
        } catch (DataAccessException e) {
            //e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getMemberID(String memberPhone) {
        String sql="select memberID from member where memberPhone=?";
        return jdbcTemplate.queryForObject(sql,String.class,memberPhone);
    }

    @Override
    public List<ReceiveMsg> getReceiveMsg(String memberID) {
        String sql="select * from receivemsg where member_ReceiveAddress=(select member_ReceiveMsg from member where memberID=?) order by isDefault desc";
        return jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(ReceiveMsg.class),memberID);
    }

    @Override
    public String getmemberNickname(String memberID) {
        String sql="select memberNickname from member where memberID=?";
        try {
            return jdbcTemplate.queryForObject(sql,String.class,memberID);
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public int getLastMember_ReceiveMsg() {
        String sql="select member_ReceiveMsg from member order by member_ReceiveMsg desc limit 1";
        try {
            return jdbcTemplate.queryForObject(sql,Integer.class);
        } catch (DataAccessException e) {
            return 0;
        }
    }
}
