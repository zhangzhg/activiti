package com.fk.activiti.service.impl;

import com.fk.activiti.domain.BaseBomModel;
import com.fk.activiti.service.ICompleteService;
import org.activiti.engine.delegate.DelegateExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class CompleteService implements ICompleteService {
    private Logger logger = LoggerFactory.getLogger(CompleteService.class);

    @Override
    public boolean isComplete(DelegateExecution execution) {
        // 获取完成实例数
        int nrOfCompletedInstances = (Integer) execution.getVariable("nrOfCompletedInstances");
        // 获取总实例数
        int nrOfInstances = (Integer) execution.getVariable("nrOfInstances");
        // 获取审批意见
        Object opinion = execution.getVariable("opinion");

        boolean agree = true;
        if (!ObjectUtils.isEmpty(opinion)) {
            agree = "1".equals(opinion);
        }

        // 全部通过才算是通过
        boolean isComplete = nrOfCompletedInstances == nrOfInstances;
        if (isComplete || !agree) {
            execution.setVariable("signResult", agree);
        }

        return isComplete;
    }

    public List<String> userList(DelegateExecution execution) {
        List<String> assigneeList = new ArrayList<>();
        Object obj = execution.getVariable("users");
        // 直接指派下一节点处理人
        if (!ObjectUtils.isEmpty(obj) && obj instanceof List) {
            assigneeList = (List<String>) obj;
        } else {
            // 自动选择 // TODO: 2020/9/22 0022  
            assigneeList.add("userId");
            assigneeList.add("userId2");
        }

        return assigneeList;
    }

    @Override
    public void complete(DelegateExecution execution) {
        BaseBomModel bomModel = (BaseBomModel) execution.getVariable("bom");
        // // TODO: 2020/9/22 0022
        logger.info("流程结束：{}", bomModel.getId());
    }
}
