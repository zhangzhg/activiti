package com.fk.activiti.service;

import com.fk.activiti.dto.TaskInfo;
import com.fk.activiti.dto.WfProcessTaskDTO;

import java.io.InputStream;
import java.util.List;

public interface IProcessTaskService {
    List<TaskInfo> taskList(WfProcessTaskDTO model);

    List<TaskInfo> taskHisList(WfProcessTaskDTO model);

    InputStream getDiagram(String taskId);
}
