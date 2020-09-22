package com.fk.activiti.controller;

import com.fk.activiti.domain.BaseBomModel;
import com.fk.activiti.domain.LeaveModel;
import com.fk.activiti.service.IProcessTaskService;
import com.fk.activiti.service.IWorkflowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/leave")
public class LeaveController {
    @Autowired
    private IWorkflowService workflowService;

    @RequestMapping("/start")
    public BaseBomModel startProcess(@RequestBody LeaveModel model) throws Exception {
        return workflowService.startProcess(model);
    }
}
