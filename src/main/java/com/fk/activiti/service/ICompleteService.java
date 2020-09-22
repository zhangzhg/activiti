package com.fk.activiti.service;

import com.fk.activiti.domain.BaseBomModel;
import org.activiti.engine.delegate.DelegateExecution;

import java.util.List;

public interface ICompleteService {
    boolean isComplete(DelegateExecution execution);

    void complete(BaseBomModel bomModel);

    List<String> userList(DelegateExecution execution);
}
