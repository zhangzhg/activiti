package com.fk.activiti.command;

import org.activiti.bpmn.model.FlowNode;
import org.activiti.bpmn.model.Process;
import org.activiti.bpmn.model.SequenceFlow;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.impl.cmd.NeedsActiveTaskCmd;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.util.ProcessDefinitionUtil;

import java.util.List;

/**
 * 驳回至上一环节Cmd
 */
public class RejectTaskCmd extends NeedsActiveTaskCmd<Void> {
	public RejectTaskCmd(String taskId) {
		super(taskId);
	}

	@Override
	public Void execute(CommandContext commandContext, TaskEntity task) {
		String processDefinitionId = task.getProcessDefinitionId();
		String executionId = task.getExecutionId();

		FlowNode firstUserTask = this.findBeforActivity(processDefinitionId, task.getTaskDefinitionKey());
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

	private FlowNode findBeforActivity(String processDefinitionId, String elementId) {
		Process process = ProcessDefinitionUtil.getProcess(processDefinitionId);
		FlowNode currNode = (FlowNode) process.getFlowElement(elementId);
		List<SequenceFlow> inFlows = currNode.getIncomingFlows();

		SequenceFlow preFlow =  inFlows.get(0);
		FlowNode sourceNode = (FlowNode) preFlow.getSourceFlowElement();

		return sourceNode;
	}
}
