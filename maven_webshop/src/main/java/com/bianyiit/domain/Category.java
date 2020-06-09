package com.bianyiit.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * 分类实体类
 */
@Data
public class Category implements Serializable {
    private String goodsID;//分类id
    private String typeName;//分类名称
}
