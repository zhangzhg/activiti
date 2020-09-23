package com.fk.activiti.service.impl;

import com.fk.activiti.command.RejectTaskCmd;
import com.fk.activiti.command.RejectToStartCmd;
import com.fk.activiti.domain.BaseBomModel;
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
import org.activiti.engine.*;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

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
    @Autowired
    TaskService taskService;
    @Autowired
    private ProcessEngine processEngine;


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
        AssertUtils.notNull(bomModel.getStartUserId(), "启动人员编号");
        logger.info("启动流程开始:id={};defKey={}", bomModel.getId(), bomModel.getProcDefKey());
        WfProcessInstance wfProcessInstance = setWfProcessInstance(bomModel);
        if (DataDict.WfProcessStatus.DRAFT.toString().equals(bomModel.getStatus())) {
            throw new BusinessException("流程文件未发布！");
        }

        Map<String, Object> processVariables = new HashMap<>();
        List<String> selectAssigneeList = bomModel.getSelectAssigneeList();
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

        // 开始节点自动通过
        Task first = taskService.createTaskQuery().processInstanceId(instance.getId()).singleResult();
        WfBaseTaskDTO model = getStartModel(first, bomModel);
        completeTask(model);
        logger.info("启动流程结束...");
        return bomModel;
    }

    @Override
    public WfBaseTaskDTO completeTask(WfBaseTaskDTO model) {
        logger.info("处理任务开始:taskId=" + model.getTaskId());
        List<String> users = model.getSelectAssigneeList();
        Task task = taskService.createTaskQuery().taskId(model.getTaskId()).singleResult();
        HashMap<String, Object> variables = new HashMap();
        variables.put("bom", model.getBom());
        variables.put("opinion", model.getOpinion());

        // 下一节点处理人
        variables.put("users", users);
        if(model.getAttribute() != null && model.getAttribute().size() > 0) {
            variables.putAll(model.getAttribute());
        }

        Map<String, Object> map = taskService.getVariables(task.getId());
        map.putAll(variables);
        taskService.addComment(model.getTaskId(), null, model.getDescription());
        taskService.complete(model.getTaskId(), variables);
        // 修改流程状态，
        WfProcessInstance ins = processInstanceService.findByProcInsId(task.getProcessInstanceId());
        String status = ins.getStatus();

        // 进行中不用修改状态
        if (DataDict.WfInsStatus.RUNNING.toString().equals(status)) {
            return model;
        }

        // 启动节点时修改成未开始（可回收）
        Map<String, Object> params = model.getAttribute();
        if (!ObjectUtils.isEmpty(params) && !ObjectUtils.isEmpty(params.get("isStartTask"))) {
            boolean isStartTask = (boolean) params.get("isStartTask");
            // 启动流程-代办
            if (isStartTask) {
                ins.setStatus(DataDict.WfInsStatus.TO_START.toString());
                processInstanceService.save(ins);
            }

            return model;
        }

        // 设置成进行中
        ins.setStatus(DataDict.WfInsStatus.RUNNING.toString());
        processInstanceService.save(ins);

        logger.info("处理任务结束...");

        return model;
    }

    @Override
    public void suspendProcess(String procInstId) {
        runtimeService.suspendProcessInstanceById(procInstId);
    }

    @Override
    public void activateProcess(String procInstId) {
        runtimeService.activateProcessInstanceById(procInstId);
    }

    @Override
    public void terminateProcess(String procInstId) {
        runtimeService.setVariable(procInstId, DataDict.WfVariableKey.STOP_PROCESS.toString(), true);
        runtimeService.deleteProcessInstance(procInstId, "终止");
    }

    @Override
    public void retrieveProcess(String procInstId) {
        //校验
        WfProcessInstance ins = processInstanceService.findById(procInstId);
        String status = ins.getStatus();
        if(!DataDict.WfInsStatus.TO_START.toString().equals(status)){
            throw new BusinessException("流程已被处理,不允许进行追回操作！");
        }

        runtimeService.setVariable(procInstId, DataDict.WfInsStatus.DELETED.toString(), true);
        runtimeService.deleteProcessInstance(procInstId, "追回");

        // 修改状态
        ins.setStatus(DataDict.WfInsStatus.DELETED.toString());
        processInstanceService.save(ins);
    }

    @Override
    public void rejectTask(WfBaseTaskDTO model) throws Exception {
        String taskId = model.getTaskId();
        logger.info("驳回任务开始:taskId={}", taskId);
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if (ObjectUtils.isEmpty(task)) {
            throw new BusinessException("找不到相关任务");
        }

        RejectTaskCmd cmd = new RejectTaskCmd(taskId);
        processEngine.getManagementService().executeCommand(cmd);

        List<Task> list = taskService.createTaskQuery()
                .processInstanceId(task.getProcessInstanceId())
                .list();

        // 删除掉任务
        for (Task t : list) {
            taskService.deleteTask(t.getId(), "任务驳回");
        }

        logger.info("驳回任务结束...");
    }

    @Override
    public void rejectToStartTask(WfBaseTaskDTO model) throws Exception {
        String taskId = model.getTaskId();
        logger.info("驳回任务开始:taskId={}", taskId);
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if (ObjectUtils.isEmpty(task)) {
            throw new BusinessException("找不到相关任务");
        }

        RejectToStartCmd cmd = new RejectToStartCmd(taskId);
        processEngine.getManagementService().executeCommand(cmd);

        List<Task> list = taskService.createTaskQuery()
                .processInstanceId(task.getProcessInstanceId())
                .list();

        // 删除掉任务
        for (Task t : list) {
            taskService.deleteTask(t.getId(), "任务驳回");
        }

        logger.info("驳回任务结束...");
    }

    @Override
    public void saveBom(String taskId, BaseBomModel model) {
        taskService.setVariableLocal(taskId, "bom", model);
    }

    @Override
    public Object findBom(String taskId) {
        if (taskService.createTaskQuery().taskId(taskId).singleResult() != null) {
            return taskService.getVariableLocal(taskId, "bom");
        }
        return null;
    }

    private WfBaseTaskDTO getStartModel(Task first, BaseBomModel bomModel) {
        WfBaseTaskDTO model = new WfBaseTaskDTO();
        List<String> list = bomModel.getSelectAssigneeList();
        model.setTaskId(first.getId());
        model.setSelectAssigneeList(list);
        model.setBom(bomModel);
        model.setOpinion("1");

        Map<String, Object> params = new HashMap<>();
        params.put("isStartTask", true);
        model.setAttribute(params);
        return model;
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
