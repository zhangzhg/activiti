package com.fk.activiti.controller;

import com.fk.activiti.domain.BaseBomModel;
import com.fk.activiti.service.IWorkflowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wf")
public class WorkflowController {
    @Autowired
    private IWorkflowService workflowService;

    @RequestMapping("/start")
    public BaseBomModel startProcess(BaseBomModel model) throws Exception {
        return workflowService.startProcess(model);
    }
}
