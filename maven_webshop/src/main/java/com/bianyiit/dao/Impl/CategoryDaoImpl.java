package com.bianyiit.dao.Impl;

import com.bianyiit.dao.CategoryDao;
import com.bianyiit.domain.Category;
import com.bianyiit.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class CategoryDaoImpl implements CategoryDao {
    JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public List<Category> findAll() {
        String sql="select * from category";
        List<Category> query = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Category.class));
        return query;
    }
}
