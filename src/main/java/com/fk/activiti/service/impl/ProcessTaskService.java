package com.fk.activiti.service.impl;

import com.fk.activiti.dto.TaskInfo;
import com.fk.activiti.dto.WfProcessTaskDTO;
import com.fk.activiti.mapper.ProcessTaskMapper;
import com.fk.activiti.service.IProcessTaskService;
import com.fk.common.constant.ErrorCode;
import com.fk.common.exception.BusinessException;
import com.fk.common.util.StringUtils;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskInfoQuery;
import org.activiti.image.ProcessDiagramGenerator;
import org.activiti.image.impl.DefaultProcessDiagramGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProcessTaskService implements IProcessTaskService {
    @Autowired
    ProcessTaskMapper processTaskMapper;
    @Autowired
    TaskService taskService;
    @Autowired
    HistoryService historyService;
    @Autowired
    RepositoryService repositoryService;
    @Autowired
    ProcessEngine processEngine;

    @Override
    public List<TaskInfo> taskList(WfProcessTaskDTO model) {
        TaskInfoQuery query = taskService.createTaskQuery()
                .processDefinitionKey(model.getProcessKey());
        if (StringUtils.isNotBlank(model.getUser())) {
            query = query.taskAssignee(model.getUser());
        }

        List<Task> list = query.list();
        List<TaskInfo> ret = new ArrayList<>();
        list.forEach(o -> {
            TaskInfo info = new TaskInfo();
            info.setDate(o.getCreateTime());
            info.setDesc(o.getDescription());
            info.setUser(o.getAssignee());
            info.setName(o.getName());
            info.setTaskId(o.getId());
            ret.add(info);
        });
        return ret;
    }

    @Override
    public List<TaskInfo> taskHisList(WfProcessTaskDTO model) {
        List<HistoricTaskInstance> list = historyService
                .createHistoricTaskInstanceQuery()
                .processDefinitionKey(model.getProcessKey())
                .taskAssignee(model.getUser())
                .finished()
                .list();
        List<TaskInfo> ret = new ArrayList<>();
        list.forEach(o -> {
            TaskInfo info = new TaskInfo();
            info.setName(o.getName());
            info.setDate(o.getCreateTime());
            info.setDesc(o.getDescription());
            info.setUser(o.getAssignee());
            info.setTaskId(o.getId());
            ret.add(info);
        });

        return ret;
    }

    @Override
    public InputStream getDiagram(String taskId) {
        org.activiti.engine.task.TaskInfo task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if (ObjectUtils.isEmpty(task)) {
            task = historyService.createHistoricTaskInstanceQuery().taskId(taskId).singleResult();
        }

        // 找不到任务节点
        if (ObjectUtils.isEmpty(task)) {
            throw new BusinessException(ErrorCode.Bpm.noUserTask);
        }

        // 根据流程定义获取模型对象
        String processDefId = task.getProcessDefinitionId();
        BpmnModel model = repositoryService.getBpmnModel(processDefId);
        // 自动生成图
        ProcessDiagramGenerator generator = new DefaultProcessDiagramGenerator();

        // 高亮节点
        List<String> activeActivityIds = processEngine.getRuntimeService().getActiveActivityIds(task.getExecutionId());

        // 生产图
        InputStream inputStream = generator.generateDiagram(model, activeActivityIds);
        // 根据模型生成图
        return inputStream;
    }
}
