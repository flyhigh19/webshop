package com.bianyiit.service;

import com.bianyiit.pojo.Account;

import java.util.List;

public interface AccountService {
    //查询所有用户
    List<Account> findAll();

    //添加用户
    void addAccount(Account account);
}
