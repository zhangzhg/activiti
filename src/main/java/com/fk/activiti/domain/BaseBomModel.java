package com.fk.activiti.domain;

import java.util.List;

public class BaseBomModel extends BaseModel {
    //流程定义ID
    private String procDefKey;

    //标题
    private String subject;

    //优先级
    private String priority;

    // 提出部门
    private String startDeptId;

    // 提出机构
    private String startOrgId;

    // 发起人
    private String startUserId;

    // 流程实例ID
    private String procInstId;

    // 有效状态
    private String status;

    // 人工选择的下一环节处理人集合
    private List<TaskExecutor> selectAssigneeList;

    public List<TaskExecutor> getSelectAssigneeList() {
        return selectAssigneeList;
    }

    public void setSelectAssigneeList(List<TaskExecutor> selectAssigneeList) {
        this.selectAssigneeList = selectAssigneeList;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getStartDeptId() {
        return startDeptId;
    }

    public void setStartDeptId(String startDeptId) {
        this.startDeptId = startDeptId;
    }

    public String getStartOrgId() {
        return startOrgId;
    }

    public void setStartOrgId(String startOrgId) {
        this.startOrgId = startOrgId;
    }

    public String getStartUserId() {
        return startUserId;
    }

    public void setStartUserId(String startUserId) {
        this.startUserId = startUserId;
    }

    public String getProcInstId() {
        return procInstId;
    }

    public void setProcInstId(String procInstId) {
        this.procInstId = procInstId;
    }

    public String getProcDefKey() {
        return procDefKey;
    }

    public void setProcDefKey(String procDefKey) {
        this.procDefKey = procDefKey;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
