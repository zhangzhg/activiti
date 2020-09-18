package com.fk.activiti.controller;

import com.fk.activiti.dto.WfProcessDefinitionDTO;
import com.fk.activiti.model.WfProcessDefinition;
import com.fk.activiti.service.IProcessDefinitionService;
import com.fk.common.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 流程定义操作接口
 */
@RestController
@RequestMapping("/wf/def")
public class ProcessDefinitionController {
    @Autowired
    IProcessDefinitionService processDefinitionService;

    /**
     * 上传流程定义文件-流程开发步骤1
     * @param mFile 流程定义文件.bpm
     * @return 流程定义基本信息
     * @throws Exception 错误信息(统一处理)
     */
    @RequestMapping(value = "/importProcessFile")
    public WfProcessDefinitionDTO importProcess(@RequestParam("file") MultipartFile mFile,
                                                @RequestParam(name = "id", required=false) String id)
            throws Exception {
        return processDefinitionService.importProcess(mFile, id);
    }

    /**
     * 发布（部署）流程定义
     *
     * @param id 流程定义扩展表ID
     * @param explanation 发布说明
     * @return WfProcessDefinitionDTO
     */
    @RequestMapping(value = "/{id}/deploy")
    public WfProcessDefinitionDTO deploy(@PathVariable String id,
                                         @RequestParam(required = false, defaultValue = "") String explanation)
            throws Exception {
        WfProcessDefinition def = processDefinitionService.deploy(id, explanation);
        return processDefinitionService.findOne(def);
    }

}
