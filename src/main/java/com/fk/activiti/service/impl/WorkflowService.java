package com.fk.activiti.service.impl;

import com.fk.activiti.domain.BaseBomModel;
import com.fk.activiti.domain.TaskExecutor;
import com.fk.activiti.dto.WfBaseTaskDTO;
import com.fk.activiti.mapper.ProcessDefinitionMapper;
import com.fk.activiti.model.ActivitiUser;
import com.fk.activiti.model.WfProcessInstance;
import com.fk.activiti.repository.ProcessDefinitionRepository;
import com.fk.activiti.service.IProcessInstanceService;
import com.fk.activiti.service.IWorkflowService;
import com.fk.common.constant.DataDict;
import com.fk.common.exception.BusinessException;
import com.fk.common.util.AssertUtils;
import com.fk.common.util.StringUtils;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.ActivitiObjectNotFoundException;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WorkflowService implements IWorkflowService {
    private Logger logger = LoggerFactory.getLogger(ProcessDefinitionService.class);

    @Autowired
    ProcessDefinitionRepository repository;
    @Autowired
    ProcessDefinitionMapper processDefinitionMapper;
    @Autowired
    RuntimeService runtimeService;
    @Autowired
    RepositoryService repositoryService;
    @Autowired
    IProcessInstanceService processInstanceService;

    @Override
    public BaseBomModel startProcess(BaseBomModel bomModel) throws Exception {
        return this.startProcess(bomModel, null);
    }

    /**
     * @param bomModel 业务编号为事先生成， key根据类型不同获取不同
     * @param variables 流程参数
     * @return 启动结果
     */
    @Override
    public BaseBomModel startProcess(BaseBomModel bomModel, Map<String, Object> variables) {
        AssertUtils.notNull(bomModel.getId(), "业务编号");
        AssertUtils.notNull(bomModel.getProcDefKey(), "流程定义");
        logger.info("启动流程开始:id={};defKey={}", bomModel.getId(), bomModel.getProcDefKey());
        WfProcessInstance wfProcessInstance = setWfProcessInstance(bomModel);
        if (DataDict.WfProcessStatus.DRAFT.toString().equals(bomModel.getStatus())) {
            throw new BusinessException("流程文件未发布！");
        }

        Map<String, Object> processVariables = new HashMap<>();
        List<TaskExecutor> selectAssigneeList = bomModel.getSelectAssigneeList();
        if (selectAssigneeList != null && selectAssigneeList.size() > 0) {
            processVariables.put(DataDict.WfVariableKey.SELECT_ASSIGNEE.getName(), selectAssigneeList);
        }
        ActivitiUser user = new ActivitiUser();
        user.setId(bomModel.getStartUserId());
        processVariables.put("startUser", user);
        processVariables.put("bom", bomModel);
        if (variables != null && variables.size() > 0) {
            processVariables.putAll(variables);
        }

        ProcessInstance instance;
        try {
            // 启动流程实例
            instance = runtimeService.startProcessInstanceByKey(bomModel.getProcDefKey(), bomModel.getId(), processVariables);
        } catch (ActivitiObjectNotFoundException ane) {
            throw new BusinessException("流程定义不存在，启动流程实例失败！");
        } catch (ActivitiException ae) {
            if(ae.getMessage().indexOf("is suspended")!=-1){
                throw new BusinessException("流程定义文件已被禁用，启动流程实例失败！");
            }else {
                throw new BusinessException(ae.getMessage());
            }
        }
        wfProcessInstance.setProcInstId(instance.getId());
        // 返回对象赋值流程实例ID
        bomModel.setProcInstId(instance.getId());

        if (StringUtils.isEmpty(wfProcessInstance.getId())) {
            processInstanceService.save(wfProcessInstance);
        } else {
            WfProcessInstance updateWfProcessInstance = processInstanceService.findById(wfProcessInstance.getId());
            try {
                BeanUtils.copyProperties(updateWfProcessInstance, wfProcessInstance);
            } catch (Exception e) {
                logger.info(e.getMessage(), e);
            }

            processInstanceService.save(updateWfProcessInstance);
        }

        logger.info("启动流程结束...");
        return bomModel;
    }

    @Override
    public void suspendProcess(String procInstId) {

    }

    @Override
    public void activateProcess(String procInstId) {

    }

    @Override
    public void terminateProcess(String procInstId) {

    }

    @Override
    public void retrieveProcess(String procInstId) {

    }

    @Override
    public void completeTask(WfBaseTaskDTO model) throws Exception {

    }

    @Override
    public void rejectTask(WfBaseTaskDTO model) throws Exception {

    }

    @Override
    public void rejectToStartTask(WfBaseTaskDTO model) throws Exception {

    }

    @Override
    public void saveBom(String taskId, BaseBomModel model) {

    }

    @Override
    public Object findBom(String taskId) {
        return null;
    }


    private WfProcessInstance setWfProcessInstance(BaseBomModel bomModel) {
        String bomModelId = bomModel.getId();
        String procDefKey = bomModel.getProcDefKey();
        String status = bomModel.getStatus();

        ProcessDefinition processDefinition = repositoryService
                .createProcessDefinitionQuery()
                .processDefinitionKey(procDefKey).latestVersion()
                .singleResult();
        String procDefId = processDefinition.getId();
        WfProcessInstance instance = new WfProcessInstance();
        instance.setBusinessKey(bomModelId);
        WfProcessInstance query = processInstanceService.findOne(instance);
        if (!StringUtils.isEmpty(query)) {
            instance.setId(query.getId());
        }
        instance.setProcDefId(procDefId);
        instance.setSubject(bomModel.getSubject());
        instance.setPriority(bomModel.getPriority());
        instance.setStatus(status);
        instance.setStartUserId(bomModel.getStartUserId());
        instance.setStartDeptId(bomModel.getStartDeptId());
        instance.setStartOrgId(bomModel.getStartOrgId());
        instance.setStartTime(new Date());
        return instance;
    }
}
