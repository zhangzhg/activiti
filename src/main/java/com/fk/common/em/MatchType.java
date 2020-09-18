package com.fk.common.em;

/**
 * jpa动态查询-属性比较类型
 * @author inno
 * @date 15/8/6
 */
public enum MatchType {
    /**
     * 等于
     * */
    EQ,
    /**
     *
     * 不等于
     *
     */
    NEQ,
    /**
     * like '%value%'
     * */
    LIKE,
    /**
     * like '%value'
     * */
    LLIKE,
    /**
     * like 'value%'
     * */
    RLIKE,
    /**
     * not like '%value%'
     * */
    NLIKE,
    /**
     * 小于
     * */
    LT,
    /**
     * 小于等于
     * */
    LTE,
    /**
     * 大于
     * */
    GT,
    /**
     * 大于等于
     * */
    GTE,
    /**
     *
     * 在两者之间
     *
     */
    BETWEEN,
    /**
     *
     * not null
     *
     */
    NULL,
    /**
     *
     * not null
     *
     */
    NNULL,
    /**
     *
     * in ()
     *
     */
    INQ,
    /**
     *
     * not in ()
     *
     */
    NIN

}
