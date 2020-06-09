package com.bianyiit.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

/**
 * 用于封装后端返回前端数据对象
 */
@Data
public class ResultInfo implements Serializable {
    private boolean flag;//后端返回结果正常为true，发生异常返回false
    private Object data;//后端返回结果数据对象
    private String errorMsg;//发生异常的错误消息
    private String identify;//标明错误的类型，是密码错误/验证码错误/用户名错误...
    private Goods goods; //封装商品信息
    private Integer count; //收藏次数
}
