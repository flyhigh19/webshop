package com.bianyiit.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 收藏实体类
 */
@Data
public class Favorite implements Serializable {
    private String goodsID;
    private Integer item;
    private Date date;
    private String memberID;
}
