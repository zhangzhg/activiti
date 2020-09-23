package com.fk.activiti.service;

import com.fk.activiti.model.WfProcessInstance;

public interface IProcessInstanceService {
    WfProcessInstance findOne(WfProcessInstance instance);

    WfProcessInstance findById(String id);

    void save(WfProcessInstance instance);

    WfProcessInstance findByProcInsId(String processInstanceId);
}
