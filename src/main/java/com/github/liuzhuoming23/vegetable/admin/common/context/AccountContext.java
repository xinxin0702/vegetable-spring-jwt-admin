package com.github.liuzhuoming23.vegetable.admin.common.context;

import com.github.liuzhuoming23.vegetable.admin.app.domain.Account;

/**
 * 账户context
 *
 * @author liuzhuoming
 */
public class AccountContext {

    private static final ThreadLocal<Account> THREAD_LOCAL = new ThreadLocal<>();

    /**
     * 登陆成功在ThreadLocal设置账户信息
     *
     * @param account 账户
     */
    public static void set(Account account) {
        THREAD_LOCAL.set(account);
    }

    /**
     * 在ThreadLocal取出账户信息
     */
    public static Account get() {
        return THREAD_LOCAL.get();
    }

    /**
     * 在ThreadLocal删除账户信息
     */
    public static void remove() {
        THREAD_LOCAL.remove();
    }
}
