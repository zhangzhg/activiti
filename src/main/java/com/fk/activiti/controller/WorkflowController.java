package com.fk.activiti.controller;

import com.fk.activiti.domain.BaseBomModel;
import com.fk.activiti.dto.TaskInfo;
import com.fk.activiti.dto.WfBaseTaskDTO;
import com.fk.activiti.dto.WfProcessTaskDTO;
import com.fk.activiti.service.IProcessTaskService;
import com.fk.activiti.service.IWorkflowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/wf")
public class WorkflowController {
    @Autowired
    private IWorkflowService workflowService;
    @Autowired
    private IProcessTaskService processTaskService;

    @RequestMapping("/start")
    public BaseBomModel startProcess(@RequestBody BaseBomModel model) throws Exception {
        return workflowService.startProcess(model);
    }

    @RequestMapping("/complete")
    public WfBaseTaskDTO completeTask(@RequestBody WfBaseTaskDTO model) throws Exception {
        return workflowService.completeTask(model);
    }

    @RequestMapping("/tasks")
    public List<TaskInfo> taskList(WfProcessTaskDTO model) throws Exception {
        return processTaskService.taskList(model);
    }
}
