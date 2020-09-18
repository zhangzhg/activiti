package com.fk.activiti.service;

import com.fk.activiti.dto.WfBaseTaskDTO;
import com.fk.activiti.domain.BaseBomModel;
import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public interface IWorkflowService {
    /**
     * 启动流程
     *
     * @param bomModel
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws IntrospectionException
     */
    BaseBomModel startProcess(BaseBomModel bomModel) throws Exception;

    /**
     * 启动流程
     *
     * @param bomModel
     * @param variables
     * @return
     */
    BaseBomModel startProcess(BaseBomModel bomModel,
                                     Map<String, Object> variables) throws Exception;

    /**
     * 挂起流程
     *
     * @param procInstId 流程实例ID
     */
    void suspendProcess(String procInstId);

    /**
     * 激活流程
     *
     * @param procInstId 流程实例ID
     */
    void activateProcess(String procInstId);

    /**
     * 终止流程
     *
     * @param procInstId 流程实例ID
     */
    void terminateProcess(String procInstId);

    /**
     * 追回流程
     *
     * @param procInstId 流程实例ID
     */
    void retrieveProcess(String procInstId);

    /**
     * 提交任务
     *
     * @param model
     */
    void completeTask(WfBaseTaskDTO model) throws Exception;

    /**
     * 驳回任务
     *
     * @param model
     */
    void rejectTask(WfBaseTaskDTO model) throws Exception;

    /**
     * 驳回任务至发起人
     *
     * @param model
     */
    void rejectToStartTask(WfBaseTaskDTO model) throws Exception;


    /**
     * 临时保存bom
     *
     * @param taskId
     * @param model
     */
    void saveBom(String taskId, BaseBomModel model);

    /**
     * 根据taskId获取bom
     * @param taskId
     */
    Object findBom(String taskId);

}
