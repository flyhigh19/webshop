package com.bianyiit.dao;

import com.bianyiit.pojo.Account;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface AccountDao {
    //查询所用用户信息的方法
    @Select("select * from account")
    List<Account> findAll();

    //添加用户
    @Insert("insert into account(name,money) values(#{name},#{money})")
    void addAccount(Account account);
}
