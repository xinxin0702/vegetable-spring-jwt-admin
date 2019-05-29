package com.github.liuzhuoming23.svea.app.service;

import com.github.liuzhuoming23.svea.app.domain.Account;
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
     * @param account 账户
     */
    void insert(Account account);

    /**
     * 获取账户
     *
     * @param account 账户查询条件
     */
    List<Account> select(Account account);

    /**
     * 根据用户名获取账户
     *
     * @param username 用户名
     */
    Account selectOneByUsername(String username);

    /**
     * 更新账户密码
     *
     * @param account 账户
     */
    void updatePasswordByUsername(Account account);

    /**
     * 根据用户名删除账户密码
     *
     * @param username 用户名
     */
    void deleteOneByUsername(String username);
}
