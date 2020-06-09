package com.bianyiit.services.Impl;

import com.bianyiit.dao.CategoryDao;
import com.bianyiit.dao.Impl.CategoryDaoImpl;
import com.bianyiit.domain.Category;
import com.bianyiit.services.CategoryService;
import com.bianyiit.util.JedisUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import redis.clients.jedis.Jedis;

import java.util.List;

public class CategoryServiceImpl implements CategoryService {
    CategoryDao categoryDao=new CategoryDaoImpl();
    @Override
    public String findAll() {
        Jedis jedis = JedisUtil.getJedis();
        String categorys = jedis.get("categorys");

        if(categorys==null||categorys.length()<=0){
            List<Category> categoryList = categoryDao.findAll();
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                categorys = objectMapper.writeValueAsString(categoryList);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            jedis.set("categorys",categorys);
        }
        jedis.close();
        return categorys;
    }
}
