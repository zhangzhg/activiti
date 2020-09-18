package com.fk.activiti.service.impl;

import com.fk.activiti.domain.BaseBomModel;
import com.fk.activiti.dto.WfBaseTaskDTO;
import com.fk.activiti.dto.WfProcessDefinitionDTO;
import com.fk.activiti.mapper.ProcessDefinitionMapper;
import com.fk.activiti.model.WfProcessDefinition;
import com.fk.activiti.repository.ProcessDefinitionRepository;
import com.fk.activiti.service.IWorkflowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Map;

@Service
public class WorkflowService implements IWorkflowService {
    @Autowired
    ProcessDefinitionRepository repository;
    @Autowired
    ProcessDefinitionMapper processDefinitionMapper;
    @Autowired
    EntityManager em;

    @Override
    public BaseBomModel startProcess(BaseBomModel bomModel) throws Exception {
        BaseBomModel model = new BaseBomModel();
        model.setSubject("start ...");
        WfProcessDefinition def = new WfProcessDefinition();
        def.setVersionNo("0");
        def.setIsMain("1");
        def.setProcDefId("111111111");
        repository.save(def);
        em.flush();
        WfProcessDefinition params = new WfProcessDefinition();
        params.setId(def.getId());
        WfProcessDefinitionDTO dto = processDefinitionMapper.findOne1(params);
        model.setId(dto.getProcDefId());
        return model;
    }

    @Override
    public BaseBomModel startProcess(BaseBomModel bomModel, Map<String, Object> variables) throws Exception {
        return null;
    }

    @Override
    public void suspendProcess(String procInstId) {

    }

    @Override
    public void activateProcess(String procInstId) {

    }

    @Override
    public void terminateProcess(String procInstId) {

    }

    @Override
    public void retrieveProcess(String procInstId) {

    }

    @Override
    public void completeTask(WfBaseTaskDTO model) throws Exception {

    }

    @Override
    public void rejectTask(WfBaseTaskDTO model) throws Exception {

    }

    @Override
    public void rejectToStartTask(WfBaseTaskDTO model) throws Exception {

    }

    @Override
    public void saveBom(String taskId, BaseBomModel model) {

    }

    @Override
    public Object findBom(String taskId) {
        return null;
    }
}
