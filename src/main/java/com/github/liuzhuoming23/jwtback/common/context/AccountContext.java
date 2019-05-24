package com.github.liuzhuoming23.jwtback.common.context;

import com.github.liuzhuoming23.jwtback.app.domain.Account;

/**
 * 账户context
 *
 * @author liuzhuoming
 */
public class AccountContext {

    private static final ThreadLocal<Account> THREAD_LOCAL = new ThreadLocal<>();

    public static void set(Account account) {
        THREAD_LOCAL.set(account);
    }

    public static Account get() {
        return THREAD_LOCAL.get();
    }

    public static void remove() {
        THREAD_LOCAL.remove();
    }
}
