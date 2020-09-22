package com.fk.activiti.service;

import com.fk.activiti.dto.TaskInfo;
import com.fk.activiti.dto.WfProcessTaskDTO;

import java.util.List;

public interface IProcessTaskService {
    List<TaskInfo> taskList(WfProcessTaskDTO model);
}
