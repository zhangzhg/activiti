package com.fk.activiti.model;

import com.fk.activiti.domain.BaseModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import java.io.Serializable;

/**
 * 流程定义模型
 */
@Entity
@XmlAccessorType(XmlAccessType.NONE)
@Table(name="wf_process_definition")
public class WfProcessDefinition extends BaseModel implements Serializable {

    private static final long serialVersionUID = 5657025881968683413L;

    // 模型ID
    @XmlAttribute
    @Column(name="model_id")
    private String modelId;

    // 流程定义ID
    @XmlAttribute
    @Column(name="proc_def_id")
    private String procDefId;

    // 版本号
    @XmlAttribute
    @Column(name="version_no")
    private String versionNo;

    // 文件编号
    @XmlAttribute
    @Column(name="file_id")
    private String fileId;

    // 键值
    @XmlAttribute
    @Column(name="key_")
    private String key;

    // 是否主版本
    @XmlAttribute
    @Column(name="is_main")
    private String isMain;

    // 发布描述
    @XmlAttribute
    @Column(name="explanation")
    private String explanation;

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public String getProcDefId() {
        return procDefId;
    }

    public void setProcDefId(String procDefId) {
        this.procDefId = procDefId;
    }

    public String getVersionNo() {
        return versionNo;
    }

    public void setVersionNo(String versionNo) {
        this.versionNo = versionNo;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getIsMain() {
        return isMain;
    }

    public void setIsMain(String isMain) {
        this.isMain = isMain;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }
}
