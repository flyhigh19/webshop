package com.bianyiit.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class Count implements Serializable {
    private Integer count;
    private Goods goods;
}
