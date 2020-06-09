package com.bianyiit.dao;

import com.bianyiit.domain.Category;

import java.util.List;

public interface CategoryDao {
    List<Category> findAll();
}
