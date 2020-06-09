package com.bianyiit.dao;

import com.bianyiit.domain.Student;
import com.bianyiit.util.DruidUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

//data Access object
public class StudentDao {
    /**
     * 用来输入的用户名和密码是否与数据中的数据匹配
     * @param username
     * @param password
     * @return
     */
    public boolean findByUsernameAndPassword(String username,String password){
        //创建ds对象
        DataSource ds = DruidUtils.getDataSource();
        //2.创建用于简化sql语句的JDBCTemplate对象，必须依赖于连接池对象
        JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
        //定义一个sql语句
        //String sql = "SELECT * from message where username='"+username+"' and password='"+password+"'";
        String sql = "SELECT * from message where username=? and password=?";
        //执行sql语句
        List<Student> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Student>(Student.class),username,password);
        if(list==null || list.size() ==0){
            return false;
        }else{
            return true;
        }
    }
}
