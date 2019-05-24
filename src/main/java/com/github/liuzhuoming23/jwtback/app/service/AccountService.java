package com.github.liuzhuoming23.jwtback.app.service;

import com.github.liuzhuoming23.jwtback.app.domain.Account;
import java.util.List;

/**
 * 账户service
 *
 * @author liuzhuoming
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

    /**
     * 更新账户密码
     *
     * @param account 账户信息
     */
    Account updatePasswordByUsername(Account account);

    /**
     * 根据用户名删除账户密码
     *
     * @param username 用户名
     */
    void deleteOneByUsername(String username);
}
