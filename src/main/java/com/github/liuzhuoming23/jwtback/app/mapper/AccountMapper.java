package com.github.liuzhuoming23.jwtback.app.mapper;

import com.github.liuzhuoming23.jwtback.app.domain.Account;
import java.util.List;

/**
 * 账户mapper
 *
 * @author liuzhuoming
 */
public interface AccountMapper {

    /**
     * 添加账户
     *
     * @param account 账户信息
     */
    void insert(Account account);

    /**
     * 获取账户信息
     *
     * @param account 账户查询信息
     */
    List<Account> select(Account account);

    /**
     * 根据用户名获取账户信息
     *
     * @param username 用户名
     */
    Account selectOneByName(String username);
}
