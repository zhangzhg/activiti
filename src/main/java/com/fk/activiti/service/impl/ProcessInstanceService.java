package com.fk.activiti.service.impl;

import com.fk.activiti.model.WfProcessInstance;
import com.fk.activiti.repository.ProcessInstanceRepository;
import com.fk.activiti.service.IProcessInstanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProcessInstanceService implements IProcessInstanceService {
    @Autowired
    ProcessInstanceRepository repository;

    @Override
    public WfProcessInstance findOne(WfProcessInstance instance) {
        return repository.findOne(instance);
    }

    @Override
    public WfProcessInstance findById(String id) {
        return repository.findOne(id);
    }

    @Override
    public void save(WfProcessInstance instance) {
        repository.save(instance);
    }

    @Override
    public WfProcessInstance findByProcInsId(String processInstanceId) {
        return repository.findByProcInstId(processInstanceId);
    }
}
