package com.fk.activiti.mapper;

import com.fk.activiti.dto.WfProcessDefinitionDTO;
import com.fk.activiti.model.WfProcessDefinition;

import java.util.Map;

public interface ProcessDefinitionMapper {
    Integer checkProcessNameRepeat(Map<String, Object> map);

    void updateProcessDefName(WfProcessDefinitionDTO params);

    WfProcessDefinitionDTO findOne(WfProcessDefinition def);

    WfProcessDefinitionDTO findOne1(WfProcessDefinition def);
}
