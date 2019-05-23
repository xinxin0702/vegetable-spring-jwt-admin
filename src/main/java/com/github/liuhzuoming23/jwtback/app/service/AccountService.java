package com.github.liuhzuoming23.jwtback.app.service;

import com.github.liuhzuoming23.jwtback.app.domain.Account;
import java.util.List;

/**
 * 账户service
 *
 * @author gnimouhzuil
 * @date 2019/5/23 21:42
 */
public interface AccountService {

    /**
     * 添加账户
     *
     * @param account 账户信息
     */
    Account insert(Account account);

    /**
     * 获取账户信息
     *
     * @param account 账户查询条件
     */
    List<Account> select(Account account);

    /**
     * 根据用户名获取账户信息
     *
     * @param username 用户名
     */
    Account selectOneByName(String username);
}
