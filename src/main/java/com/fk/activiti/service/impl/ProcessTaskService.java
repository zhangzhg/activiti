package com.fk.activiti.service.impl;

import com.fk.activiti.dto.TaskInfo;
import com.fk.activiti.dto.WfProcessTaskDTO;
import com.fk.activiti.mapper.ProcessTaskMapper;
import com.fk.activiti.service.IProcessTaskService;
import com.fk.common.util.StringUtils;
import org.activiti.engine.HistoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskInfoQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProcessTaskService implements IProcessTaskService {
    @Autowired
    ProcessTaskMapper processTaskMapper;
    @Autowired
    TaskService taskService;
    @Autowired
    HistoryService historyService;

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
}
