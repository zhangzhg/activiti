package com.fk.activiti.service;

import com.fk.activiti.dto.WfProcessDefinitionDTO;
import com.fk.activiti.model.WfProcessDefinition;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface IProcessDefinitionService {
    /**
     * 上传流程定义文件
     * @param mFile 文件流
     * @return 流程基本信息
     */
    WfProcessDefinitionDTO importProcess(MultipartFile mFile, String processDefinitionId);

    /**
     * 发布流程
     * @param id 流程定义扩展表ID
     * @param explanation 发布说明
     */
    WfProcessDefinition deploy(String id, String explanation);

    /**
     * 查询流程定义
     * @param def 查询参数
     * @return 流程基本信息
     */
    WfProcessDefinitionDTO findOne(WfProcessDefinition def);
}
