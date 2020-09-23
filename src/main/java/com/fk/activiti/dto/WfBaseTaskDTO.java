package com.fk.activiti.dto;

import com.fk.activiti.domain.BaseBomModel;
import com.fk.activiti.domain.TaskExecutor;

import java.util.List;
import java.util.Map;

public class WfBaseTaskDTO {
    private String taskId;

    private BaseBomModel bom;

    //意见内容，驳回 转办 协办等原因
    private String description;

    //意见代码  201同意 202不同意等
    private String opinion;

    // 人工选择的下一环节处理人集合
    private List<String> selectAssigneeList;
    // 其他属性变量
    private Map<String,Object> attribute;

    public List<String> getSelectAssigneeList() {
        return selectAssigneeList;
    }

    public void setSelectAssigneeList(List<String> selectAssigneeList) {
        this.selectAssigneeList = selectAssigneeList;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public BaseBomModel getBom() {
        return bom;
    }

    public void setBom(BaseBomModel bom) {
        this.bom = bom;
    }

    public String getOpinion() {
        return opinion;
    }

    public void setOpinion(String opinion) {
        this.opinion = opinion;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Map<String,Object> getAttribute(){
    	return attribute;
    }

    public void setAttribute(Map<String,Object> attribute){
    	this.attribute = attribute;
    }

}
