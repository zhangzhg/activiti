package com.fk.activiti.service.impl;

import com.fk.activiti.dto.TaskInfo;
import com.fk.activiti.dto.WfProcessTaskDTO;
import com.fk.activiti.mapper.ProcessTaskMapper;
import com.fk.activiti.service.IProcessTaskService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
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

    @Override
    public List<TaskInfo> taskList(WfProcessTaskDTO model) {
        List<Task> list = taskService.createTaskQuery().list();
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
}
