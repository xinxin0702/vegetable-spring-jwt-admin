package com.github.liuzhuoming23.svea.app.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.liuzhuoming23.svea.app.domain.Account;
import com.github.liuzhuoming23.svea.common.domain.PageParams;
import com.github.liuzhuoming23.svea.common.domain.SortParams;
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
     * 更新账户
     *
     * @param account 账户
     */
    void update(Account account);

    /**
     * 获取账户
     *
     * @param account 账户查询条件
     */
    List<Account> select(Account account);

    /**
     * 获取分页账户列表
     *
     * @param account 账户查询
     * @param pageParams 分页参数
     * @param sortParams 排序参数
     */
    IPage<Account> page(Account account, PageParams pageParams, SortParams sortParams);

    /**
     * 根据用户名获取账户
     *
     * @param username 用户名
     */
    Account selectOneByUsername(String username);

    /**
     * 更新账户密码
     *
     * @param username 用户名
     * @param password 明文密码
     */
    void updatePasswordByUsername(String username, String password);

    /**
     * 根据用户名删除账户密码
     *
     * @param username 用户名
     */
    void deleteOneByUsername(String username);
}
