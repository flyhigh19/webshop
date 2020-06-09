package com.bianyiit.dao;

import com.bianyiit.domain.Student;
import com.bianyiit.util.DruidUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class UserDao {
    public List<Student> findAll(){
        JdbcTemplate jdbcTemplate = new JdbcTemplate(DruidUtils.getDataSource());
        String sql="select * from message";
        List<Student> query = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Student>(Student.class));
        return query;
    }
}
