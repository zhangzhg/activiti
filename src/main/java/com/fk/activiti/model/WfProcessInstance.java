package com.fk.activiti.model;

import com.fk.activiti.domain.BaseModel;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@DynamicInsert
@Entity
@Table(name="wf_process_instance")
public class WfProcessInstance extends BaseModel implements Serializable {
    //主题
    @Column
    private String subject;

    //优先级
    @Column
    private String priority;

    //状态
    @Column
    private String status;

    //业务ID
    @Column(name="business_key")
    private String businessKey;

    //业务路由
    @Column(name="business_url")
    private String businessUrl;

    //流程实例ID
    @Column(name="procinst_id")
    private String procInstId;

    //流程定义ID
    @Column(name="proc_def_id")
    private String procDefId;

    //发起者
    @Column(name="start_user_id")
    private String startUserId;

    //发起机构
    @Column(name="start_org_id")
    private String startOrgId;

    //发起部门
    @Column(name="start_dept_id")
    private String startDeptId;

    //发起时间
    @Column(name="start_time")
    private Date startTime;


    private static final long serialVersionUID = 1L;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBusinessKey() {
        return businessKey;
    }

    public void setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
    }

    public String getBusinessUrl() {
        return businessUrl;
    }

    public void setBusinessUrl(String businessUrl) {
        this.businessUrl = businessUrl;
    }

    public String getProcInstId() {
        return procInstId;
    }

    public void setProcInstId(String procInstId) {
        this.procInstId = procInstId;
    }

    public String getProcDefId() {
        return procDefId;
    }

    public void setProcDefId(String procDefId) {
        this.procDefId = procDefId;
    }

    public String getStartUserId() {
        return startUserId;
    }

    public void setStartUserId(String startUserId) {
        this.startUserId = startUserId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public String getStartOrgId() {
        return startOrgId;
    }

    public void setStartOrgId(String startOrgId) {
        this.startOrgId = startOrgId;
    }

    public String getStartDeptId() {
        return startDeptId;
    }

    public void setStartDeptId(String startDeptId) {
        this.startDeptId = startDeptId;
    }
}
