package com.fk.activiti.command;

import org.activiti.bpmn.model.*;
import org.activiti.bpmn.model.Process;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.impl.cmd.NeedsActiveTaskCmd;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntityManager;
import org.activiti.engine.impl.util.ProcessDefinitionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class RejectToStartCmd extends NeedsActiveTaskCmd<Void> {
    private static Logger logger = LoggerFactory.getLogger(RejectToStartCmd.class);

    public RejectToStartCmd(String taskId) {
        super(taskId);
    }

    @Override
    public Void execute(CommandContext commandContext, TaskEntity task) {
        String processDefinitionId = task.getProcessDefinitionId();
        String executionId = task.getExecutionId();

        FlowNode firstUserTask = this.findFirstActivity(processDefinitionId);
        ExecutionEntity executionEntity = commandContext.getExecutionEntityManager().findById(executionId);

        // 获取目标节点的来源连线
        List<SequenceFlow> flows = firstUserTask.getIncomingFlows();
        if (flows == null || flows.isEmpty()) {
            throw new ActivitiException("回退错误，目标节点没有来源连线");
        }
        // 随便选一条连线来执行，时当前执行计划为，从连线流转到目标节点，实现跳转
        executionEntity.setCurrentFlowElement(flows.get(0));
        commandContext.getAgenda().planTakeOutgoingSequenceFlowsOperation(executionEntity, true);

        return null;
    }

    public FlowNode findFirstActivity(String processDefinitionId) {
        Process process = ProcessDefinitionUtil.getProcess(processDefinitionId);
        FlowElement flowElement = process.getInitialFlowElement();
        FlowNode startActivity = (FlowNode) flowElement;

        if (startActivity.getOutgoingFlows().size() != 1) {
            throw new IllegalStateException(
                    "start activity outgoing transitions cannot more than 1, now is : "
                            + startActivity.getOutgoingFlows().size());
        }

        SequenceFlow sequenceFlow = startActivity.getOutgoingFlows().get(0);
        FlowNode targetActivity = (FlowNode) sequenceFlow.getTargetFlowElement();

        if (!(targetActivity instanceof UserTask)) {
            logger.info("first activity is not userTask, just skip");
            return null;
        }

        return targetActivity;
    }

}
