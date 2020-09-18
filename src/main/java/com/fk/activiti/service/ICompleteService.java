package com.fk.activiti.service;

import com.fk.activiti.domain.BaseBomModel;
import org.activiti.engine.delegate.DelegateExecution;

public interface ICompleteService {
    boolean isComplete(DelegateExecution execution, String opinion);

    void afterEnd(BaseBomModel bomModel);
}
