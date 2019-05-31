package com.github.liuzhuoming23.vegetable.admin.common.cons;

/**
 * 日志等级
 *
 * @author liuzhuoming
 */
public enum LogLevel {
    /**
     * 默认等级（最低）
     */
    DEFAULT(0),
    /**
     * 等级1
     */
    LV1(1),
    /**
     * 等级2
     */
    LV2(2),
    /**
     * 等级3
     */
    LV3(3),
    /**
     * 等级4
     */
    LV4(4),
    /**
     * 等级5（最高）
     */
    LV5(5);
    private int val;

    LogLevel(int val) {
        this.val = val;
    }

    public int getVal() {
        return val;
    }
}
