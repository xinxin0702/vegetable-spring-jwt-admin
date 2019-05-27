package com.github.liuzhuoming23.svea.app.mapper;

import com.github.liuzhuoming23.svea.app.domain.Account;
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
    Account selectOneByUsername(String username);

    /**
     * 根据用户id获取账户信息
     *
     * @param id 用户id
     */
    Account selectOneById(int id);

    /**
     * 根据用户名更新账户密码
     *
     * @param account 账户信息
     */
    void updatePasswordByUsername(Account account);

    /**
     * 根据用户名删除账户密码
     *
     * @param username 用户名
     */
    void deleteOneByUsername(String username);
}
