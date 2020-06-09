package com.bianyiit.service.impl;

import com.bianyiit.dao.AccountDao;
import com.bianyiit.pojo.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements com.bianyiit.service.AccountService {
    @Autowired
    private AccountDao accountDao;

    @Override
    public List<Account> findAll() {
        return accountDao.findAll();
    }

    @Override
    public void addAccount(Account account) {
        accountDao.addAccount(account);
    }
}
