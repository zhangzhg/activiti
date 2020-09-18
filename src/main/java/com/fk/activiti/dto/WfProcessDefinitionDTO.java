package com.fk.activiti.dto;

import com.fk.activiti.model.WfProcessDefinition;

import javax.xml.bind.annotation.*;
import java.util.Date;

/**
 * 流程定义模型DTO
 */
@XmlRootElement(name = "bpmDefinition")
@XmlAccessorType(XmlAccessType.NONE)
public class WfProcessDefinitionDTO extends WfProcessDefinition {
    /**
     * 流程定义发布状态 0,未发布 1,已发布
     */
    private String publishStatus;

    /**
     * 流程定义启状态  0,禁用 1,启用
     */
    private String enabledStatus;

    /**
     * 流程分类名称
     */
    private String categoryName;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 预导入流程文件全路径
     */
    private String bpmFileFullPath;

    /**
     * 草稿ID
     */
    private String draftId;

    /**
     * 流程定义文件资源ID
     */
    private String editorSourceValueId;

    /**
     * 流程定义文件名称
     */

    private String fileName;

    @XmlAttribute
    private String name;

    private String description;

    @XmlAttribute
    private String category;

    @XmlElement
    private String activityXml;

    // 导入流程定义的结果
    private String importResult;

    // 平台是否已存在该key的流程定义
    private boolean existKey = false;

    /**
     * 是否全被代理
     */
    private boolean allAgent = false;


    public boolean isExistKey() {
        return existKey;
    }

    public void setExistKey(boolean existKey) {
        this.existKey = existKey;
    }

    public String getActivityXml() {
        return activityXml;
    }

    public void setActivityXml(String activityXml) {
        this.activityXml = activityXml;
    }

    public String getImportResult() {
        return importResult;
    }

    public void setImportResult(String importResult) {
        this.importResult = importResult;
    }

    public String getEditorSourceValueId() {
        return editorSourceValueId;
    }

    public void setEditorSourceValueId(String editorSourceValueId) {
        this.editorSourceValueId = editorSourceValueId;
    }

    public String getDraftId() {
        return draftId;
    }

    public void setDraftId(String draftId) {
        this.draftId = draftId;
    }

    public boolean isAllAgent() {
        return allAgent;
    }

    public void setAllAgent(boolean allAgent) {
        this.allAgent = allAgent;
    }

    public String getBpmFileFullPath() {
        return bpmFileFullPath;
    }

    public void setBpmFileFullPath(String bpmFileFullPath) {
        this.bpmFileFullPath = bpmFileFullPath;
    }

    public String getPublishStatus() {
        return publishStatus;
    }

    public void setPublishStatus(String publishStatus) {
        this.publishStatus = publishStatus;
    }

    public String getEnabledStatus() {
        return enabledStatus;
    }

    public void setEnabledStatus(String enabledStatus) {
        this.enabledStatus = enabledStatus;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
