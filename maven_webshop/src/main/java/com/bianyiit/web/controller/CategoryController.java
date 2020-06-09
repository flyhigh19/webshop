package com.bianyiit.web.controller;

import com.bianyiit.services.CategoryService;
import com.bianyiit.services.Impl.CategoryServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CategoryController {
    /*查询分类数据的操作*/
    public void FindAll(HttpServletRequest request, HttpServletResponse response)throws IOException{
        response.setContentType("application/json;charset=utf-8");
        CategoryService categoryService = new CategoryServiceImpl();
        String categoryServiceJson = categoryService.findAll();
        response.getWriter().println(categoryServiceJson);
    }
}
