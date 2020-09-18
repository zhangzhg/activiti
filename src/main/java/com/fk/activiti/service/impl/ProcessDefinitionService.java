package com.fk.activiti.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fk.activiti.dto.WfProcessDefinitionDTO;
import com.fk.activiti.mapper.ProcessDefinitionMapper;
import com.fk.activiti.model.SysAttachment;
import com.fk.activiti.model.WfProcessDefinition;
import com.fk.activiti.repository.ProcessDefinitionRepository;
import com.fk.activiti.service.IProcessDefinitionService;
import com.fk.activiti.service.ISysAttachmentService;
import com.fk.common.constant.DataDict;
import com.fk.common.constant.ErrorCode;
import com.fk.common.exception.BusinessException;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.Process;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 流程定义文件操作
 * 上传流程文件
 * 读取流程文件到model
 * 发布流程文件
 */
@Service
public class ProcessDefinitionService implements IProcessDefinitionService {
    private Logger logger = LoggerFactory.getLogger(ProcessDefinitionService.class);
    /** spring jpa **/
    @Autowired
    ProcessDefinitionRepository repository;
    @Autowired
    ISysAttachmentService sysAttachmentService;
    @Autowired
    RepositoryService repositoryService;
    @Autowired
    ProcessDefinitionMapper processDefinitionMapper;

    @Override
    @Transactional
    public WfProcessDefinition deploy(String id, String explanation) {
        // 找到已上传的流程定义信息
        WfProcessDefinition wfProcessDefinition = repository.findOne(id);
        // 根据流程定义获取模型
        Model model = repositoryService.getModel(wfProcessDefinition.getModelId());
        // 设置名称
        String resourceName = model.getName()
                + DataDict.Separator.POINT.toString()
                + DataDict.Separator.BPMN_Suffix.toString();
        byte[] source = repositoryService.getModelEditorSource(model.getId());
        JsonNode modelNode;
        try {
            modelNode = new ObjectMapper().readTree(source);
        } catch (IOException e) {
            throw new BusinessException("读取流程定义文件异常", e);
        }
        // 转换成bpmn模型
        BpmnModel bpmnModel = new BpmnJsonConverter().convertToBpmnModel(modelNode);
        byte[] bpmnBytes = new BpmnXMLConverter().convertToXML(bpmnModel);
        // 发布流程，用流发布
        Deployment deployment = repositoryService.createDeployment()
                .name(resourceName)
                .addInputStream(resourceName, new ByteArrayInputStream(bpmnBytes))
                .deploy();
        // 获取发布生成的流程编号
        model.setDeploymentId(deployment.getId());
        // 保存发布信息
        repositoryService.saveModel(model);
        // 获取流程定义
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .deploymentId(deployment.getId()).singleResult();
        String processDefinitionId = processDefinition.getId();
        repositoryService.setProcessDefinitionCategory(processDefinitionId, model.getCategory());

        // 修改扩展表流程定义信息
        WfProcessDefinitionDTO params = new WfProcessDefinitionDTO();
        params.setName(model.getName());
        params.setProcDefId(processDefinitionId);
        params.setId(id);
        processDefinitionMapper.updateProcessDefName(params);

        wfProcessDefinition.setProcDefId(processDefinitionId);
        wfProcessDefinition.setExplanation(explanation);
        // 修改流程定义基本信息
        repository.save(wfProcessDefinition);

        return wfProcessDefinition;
    }

    public WfProcessDefinitionDTO findOne(WfProcessDefinition def) {
        // 返回基本信息
        return processDefinitionMapper.findOne(def);
    }

    /**
     * 上传或更新流程定义文件
     * @param mFile 文件流
     * @param processDefinitionId 流程文件编号
     * @return 流程基本信息
     */
    @Override
    public WfProcessDefinitionDTO importProcess(MultipartFile mFile, String processDefinitionId) {
        try (InputStream inputStream = new ByteArrayInputStream(mFile.getBytes())) {
            BpmnModel bpmnModel = convertInputStreamToBpmnModel(inputStream);
            WfProcessDefinitionDTO wfProcessDefinitionDto = convertBpmnModelToWfProcDefDTO(bpmnModel);
            // 由流程图点击进入的导入页面，需要判断流程key是否一样
            if (!StringUtils.isBlank(processDefinitionId)) {
                WfProcessDefinition oldWfProcessDefinition = repository.findOne(processDefinitionId);
                if (!oldWfProcessDefinition.getKey().equals(wfProcessDefinitionDto.getKey())) {
                    throw new BusinessException("流程图Key与当前流程定义不一致，无法导入");
                }
            }

            SysAttachment sysAttachment = new SysAttachment();
            sysAttachment.setModule("processManagement");
            sysAttachment.setSubModule("WfProcessDefinition");

            sysAttachmentService.upload(sysAttachment, mFile);
            wfProcessDefinitionDto.setBpmFileFullPath(sysAttachment.getPath());
            wfProcessDefinitionDto.setFileName(mFile.getOriginalFilename());
            wfProcessDefinitionDto.setFileId(sysAttachment.getId());

            // 解析文件
            create(wfProcessDefinitionDto, bpmnModel);
            return wfProcessDefinitionDto;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new BusinessException(ErrorCode.Bpm.preimportFailure);
        }
    }

    private void create(WfProcessDefinitionDTO def, BpmnModel bpmnModel) {
        String procDefName = def.getName();
        String description = def.getDescription();
        String category = def.getCategory();
        String fileId = def.getFileId();

        // 读取model信息转换成模型定义信息
        WfProcessDefinitionDTO processModel = convertBpmnModelToWfProcDefDTO(bpmnModel);

        String processKey = processModel.getKey();

        String processName = StringUtils.isBlank(procDefName) ? processModel
                .getName() : procDefName;
        processName = StringUtils.isBlank(processName) ? processKey
                : processName;

        if (!checkProcessNameRepeat(processName, processKey)) {
            throw new BusinessException(ErrorCode.Bpm.nameIsExists);
        }

        // 删除历史草稿
        String version = deleteDraft(processKey) + 1;

        // Activiti的model存储,如果不保存直接根据文件发布，这个表会没有值
        Model activitiModel = repositoryService.newModel();
        activitiModel.setCategory(category);
        activitiModel.setKey(processKey);
        activitiModel.setMetaInfo(description);
        activitiModel.setName(processName);
        repositoryService.saveModel(activitiModel);
        String modelId = activitiModel.getId();
        // bpmn的存储
        Process bpmnProcess = bpmnModel.getMainProcess();
        bpmnModel.setTargetNamespace(category);
        bpmnProcess.setName(processName);
        bpmnProcess.setDocumentation(description);
        BpmnJsonConverter jsonConverter = new BpmnJsonConverter();
        ObjectNode editorNode = jsonConverter.convertToJson(bpmnModel);
        repositoryService.addModelEditorSource(modelId, editorNode.toString().getBytes());

        // 保存流程定义
        WfProcessDefinition wfProcessDefinition = new WfProcessDefinition();
        wfProcessDefinition.setModelId(modelId);
        wfProcessDefinition.setKey(processKey);
        wfProcessDefinition.setVersionNo(version);
        wfProcessDefinition.setIsMain(DataDict.WfProcDefIsMain.MAIN.toString());
        wfProcessDefinition.setVersionNo(DataDict.WfProcDefVersion.DRAFT.toString());
        wfProcessDefinition.setFileId(fileId);
        repository.save(wfProcessDefinition);
        def.setId(wfProcessDefinition.getId());
    }

    /**
     * 删除流程定义草稿
     * @param key 流程定义编号
     * @return String 返回最大版本+1
     */
    private String deleteDraft(String key) {
        List<WfProcessDefinition> list = findDraft(key);
        int max = 0;
        for (WfProcessDefinition def : list) {
            String version = def.getVersionNo();
            if (StringUtils.isNotBlank(version) && Integer.parseInt(version) > max) {
                max = Integer.parseInt(version);
            }
            repository.delete(def);
            // 删除历史附件
            sysAttachmentService.deleteById(def.getFileId());
        }

        max++;
        return String.valueOf(max);
    }

    /**
     * 检查流程定义是否重复
     * @param processName 流程定义名称
     * @param processKey 流程定义key
     */
    private boolean checkProcessNameRepeat(String processName, String processKey) {
        Map<String, Object> map = new HashMap<>();
        map.put("processName", processName);
        map.put("processKey", processKey);
        Integer size = processDefinitionMapper.checkProcessNameRepeat(map);
        return size == null || size == 0;
    }

    /**
     * 将流程定义文件输入流转化为BpmnModel
     *
     * @param inputStream 流程定义文件输入流
     * @return BpmnModel
     */
    private BpmnModel convertInputStreamToBpmnModel(InputStream inputStream) {
        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        InputStreamReader inputStreamReader;
        XMLStreamReader xmlStreamReader;
        try {
            inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            xmlStreamReader = xmlInputFactory.createXMLStreamReader(inputStreamReader);
            return new BpmnXMLConverter().convertToBpmnModel(xmlStreamReader);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new BusinessException(ErrorCode.Bpm.fileConvertFailure);
        }

    }

    /**
     * 把BpmnModel转化为WfProcessDefinitionDTO
     *
     * @param bpmnModel BPMN模型
     * @return processDefinition
     */
    private WfProcessDefinitionDTO convertBpmnModelToWfProcDefDTO(
            BpmnModel bpmnModel) {
        WfProcessDefinitionDTO processDefinition = new WfProcessDefinitionDTO();
        if (bpmnModel.getMainProcess() != null && bpmnModel.getMainProcess().getId() != null) {
            if (bpmnModel.getLocationMap().size() > 0) {
                Process process = bpmnModel.getMainProcess();
                processDefinition.setKey(process.getId());
                processDefinition.setName(process.getName());
                processDefinition.setDescription(process.getDocumentation());
            }
        }

        return processDefinition;
    }

    private List<WfProcessDefinition> findDraft(String key) {
        WfProcessDefinition wfProcessDefinition = new WfProcessDefinition();
        wfProcessDefinition.setKey(key);
        wfProcessDefinition.setVersionNo(DataDict.WfProcDefVersion.DRAFT.toString());
        return repository.find(wfProcessDefinition);
    }
}
