package com.fk.activiti.service.impl;

import com.fk.activiti.domain.BaseBomModel;
import com.fk.activiti.service.ICompleteService;
import org.activiti.engine.delegate.DelegateExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CompleteService implements ICompleteService {
    private Logger logger = LoggerFactory.getLogger(CompleteService.class);

    @Override
    public boolean isComplete(DelegateExecution execution, String opinion) {
        // 获取完成实例数
        int nrOfCompletedInstances = (Integer) execution.getVariable("nrOfCompletedInstances");
        // 获取总实例数
        int nrOfInstances = (Integer) execution.getVariable("nrOfInstances");

        // 全部通过才算是通过
        boolean agree = "1".equals(opinion);
        boolean isComplete = nrOfCompletedInstances == nrOfInstances;
        if (isComplete || !agree) {
            execution.setVariable("signResult", agree);
        }

        return isComplete;
    }

    @Override
    public void afterEnd(BaseBomModel bomModel) {
        logger.info("流程结束：{}", bomModel.getId());
    }
}
