package com.fk.activiti.controller;

import com.fk.activiti.domain.BaseBomModel;
import com.fk.activiti.dto.TaskInfo;
import com.fk.activiti.dto.WfBaseTaskDTO;
import com.fk.activiti.dto.WfProcessTaskDTO;
import com.fk.activiti.service.IProcessTaskService;
import com.fk.activiti.service.IWorkflowService;
import com.fk.common.util.Base64Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStream;
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

    @RequestMapping("/rejectToFirst")
    public void rejectToFirst(WfBaseTaskDTO model) throws Exception {
        workflowService.rejectToStartTask(model);
    }

    @RequestMapping(value = "/{id}/diagram")
    public String getDiagram(@PathVariable String id) throws Exception {
        InputStream inputStream = processTaskService.getDiagram(id);
        return Base64Utils.inputStreamToBase64(inputStream);
    }

    @RequestMapping("/tasks")
    public List<TaskInfo> taskList(WfProcessTaskDTO model) throws Exception {
        return processTaskService.taskList(model);
    }

    @RequestMapping("/his/tasks")
    public List<TaskInfo> taskHisList(WfProcessTaskDTO model) throws Exception {
        return processTaskService.taskHisList(model);
    }

}
