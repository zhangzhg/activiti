package com.fk.common.listener;

/**
 * 功能描述:逻辑删除可监听
 *
 * @author :wengtt 2015/7/28
 */
public interface IDeleteListenable {

    /** 设置状态值 */
    void setStatus(String status);
}
