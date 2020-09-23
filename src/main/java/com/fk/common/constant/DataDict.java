package com.fk.common.constant;

public interface DataDict {

	/**
	 * 流程节点-节点类型
	 */
	 enum WfNodeType{

           COMMON("1", "普通节点"),
           SIGN("2", "会签节点");

           private final String code;

           private final String name;

           private WfNodeType(String code, String name) {
               this.code = code;
               this.name = name;
           }

           public String getName(){
               return  this.name;
           }
           @Override
           public String toString() {
               return code;
           }
       };
	/**
	 * 流程节点-操作权限-公共
	 */
	 enum WfNodeOp{

           COMPLETE("101", "提交"),
           TRANSFER("104", "转办"),
           ASSIST("105", "协办");

           private final String code;

           private final String name;

           private WfNodeOp(String code, String name) {
               this.code = code;
               this.name = name;
           }

           public String getName(){
               return  this.name;
           }
           @Override
           public String toString() {
               return code;
           }
       };
	/**
	 * 流程节点-操作权限-普通节点
	 */
	 enum WfNodeOpCommon{

           COMPLETE("101", "提交"),
           TRANSFER("104", "转办"),
           ASSIST("105", "协办"),
           REJECT("102", "驳回"),
           REJECT_TO_START("103", "驳回发起人");

           private final String code;

           private final String name;

           private WfNodeOpCommon(String code, String name) {
               this.code = code;
               this.name = name;
           }

           public String getName(){
               return  this.name;
           }
           @Override
           public String toString() {
               return code;
           }
       };
	/**
	 * 流程节点-操作权限-会签节点
	 */
	 enum WfNodeOpSign{

           COMPLETE("101", "提交"),
           TRANSFER("104", "转办"),
           ASSIST("105", "协办"),
           ADD_SIGN("106", "加签");

           private final String code;

           private final String name;

           private WfNodeOpSign(String code, String name) {
               this.code = code;
               this.name = name;
           }

           public String getName(){
               return  this.name;
           }
           @Override
           public String toString() {
               return code;
           }
       };
	/**
	 * 流程节点-意见-公共
	 */
	 enum WfNodeOpResult{

           PASS("201", "同意"),
           REFUSE("202", "不同意");

           private final String code;

           private final String name;

           private WfNodeOpResult(String code, String name) {
               this.code = code;
               this.name = name;
           }

           public String getName(){
               return  this.name;
           }
           @Override
           public String toString() {
               return code;
           }
       };
	/**
	 * 流程节点-意见-会签节点
	 */
	 enum WfNodeOpResultSign{

           PASS("201", "同意"),
           REFUSE("202", "不同意"),
           CONDITION_APPROVE("203", "有条件同意"),
           GIVEUP("204", "弃权");

           private final String code;

           private final String name;

           private WfNodeOpResultSign(String code, String name) {
               this.code = code;
               this.name = name;
           }

           public String getName(){
               return  this.name;
           }
           @Override
           public String toString() {
               return code;
           }
       };
	/**
	 * 流程节点-人员类型
	 */
	 enum WfNodeUserType{

           USER("SysUser", "用户"),
           POSITION("SysPosition", "岗位"),
           ROLE("SysRole", "角色"),
           ORG("SysOrg", "机构"),
           DEPT("SysDept", "部门"),
           GROUP("SysGroup", "群组");

           private final String code;

           private final String name;

           private WfNodeUserType(String code, String name) {
               this.code = code;
               this.name = name;
           }

           public String getName(){
               return  this.name;
           }
           @Override
           public String toString() {
               return code;
           }
       };
	/**
	 * 流程节点-抽取方式
	 */
	 enum WfNodeExtract{

           NOT_EXTRACT("0", "不抽取"),
           EXTRACT("1", "抽取");

           private final String code;

           private final String name;

           private WfNodeExtract(String code, String name) {
               this.code = code;
               this.name = name;
           }

           public String getName(){
               return  this.name;
           }
           @Override
           public String toString() {
               return code;
           }
       };
	/**
	 * 流程节点-表单变量
	 */
	 enum WfStartUserVariable{

           START_USER("START_USER", "发起用户"),
           START_POSITION("START_POSITION", "发起岗位"),
           START_ROLE("START_ROLE", "发起角色"),
           START_ORG("START_ORG", "发起机构"),
           START_DEPT("START_DEPT", "发起部门"),
           START_GROUP("START_GROUP", "发起群组");

           private final String code;

           private final String name;

           private WfStartUserVariable(String code, String name) {
               this.code = code;
               this.name = name;
           }

           public String getName(){
               return  this.name;
           }
           @Override
           public String toString() {
               return code;
           }
       };
	/**
	 * 流程节点-选人模式
	 */
	 enum WfNodeAssigneeType{

           AUTO("1", "系统分配"),
           USER_SELECT("2", "人工选择");

           private final String code;

           private final String name;

           private WfNodeAssigneeType(String code, String name) {
               this.code = code;
               this.name = name;
           }

           public String getName(){
               return  this.name;
           }
           @Override
           public String toString() {
               return code;
           }
       };
	/**
	 * 流程节点-决策方式-会签节点
	 */
	 enum WfSignDecideType{

           PASS("1", "通过"),
           REFUSE("2", "拒绝");

           private final String code;

           private final String name;

           private WfSignDecideType(String code, String name) {
               this.code = code;
               this.name = name;
           }

           public String getName(){
               return  this.name;
           }
           @Override
           public String toString() {
               return code;
           }
       };
	/**
	 * 流程节点-投票方式-会签节点
	 */
	 enum WfSignVoteType{

           ABSOLUTE_VOTES("1", "绝对票数"),
           PERCENT("2", "百分比");

           private final String code;

           private final String name;

           private WfSignVoteType(String code, String name) {
               this.code = code;
               this.name = name;
           }

           public String getName(){
               return  this.name;
           }
           @Override
           public String toString() {
               return code;
           }
       };
	/**
	 * 流程节点-消息发送-时间类型
	 */
	 enum WfMessageDateType{

           WORKING_DAY("1", "工作日"),
           CALENDAR_DAY("2", "日历日");

           private final String code;

           private final String name;

           private WfMessageDateType(String code, String name) {
               this.code = code;
               this.name = name;
           }

           public String getName(){
               return  this.name;
           }
           @Override
           public String toString() {
               return code;
           }
       };
	/**
	 * 流程节点-消息发送-相对动作
	 */
	 enum WfTaskRelativeAction{

           CREATE("1", "创建"),
           COMPLETE("2", "完成");

           private final String code;

           private final String name;

           private WfTaskRelativeAction(String code, String name) {
               this.code = code;
               this.name = name;
           }

           public String getName(){
               return  this.name;
           }
           @Override
           public String toString() {
               return code;
           }
       };
	/**
	 * 流程实例-实例状态
	 */
	 enum WfProcessStatus{

           DRAFT("0", "草稿"),
           APPROVE("1", "审批中"),
           END("2", "结束"),
           REJECT("3", "驳回"),
           RETRIEVE("4", "追回"),
           SUSPENDED("5", "挂起"),
           STOP("6", "终止");

           private final String code;

           private final String name;

           private WfProcessStatus(String code, String name) {
               this.code = code;
               this.name = name;
           }

           public String getName(){
               return  this.name;
           }
           @Override
           public String toString() {
               return code;
           }
       };
	/**
	 * 逾期-任务状态
	 */
	 enum WfTaskStatus{

           UNCLAIM("1", "未签收"),
           UNHANDLE("2", "未处理"),
           HANDLED("3", "已处理"),
           SUSPENDED("4", "挂起"),
           CANCEL("5", "取消");

           private final String code;

           private final String name;

           private WfTaskStatus(String code, String name) {
               this.code = code;
               this.name = name;
           }

           public String getName(){
               return  this.name;
           }
           @Override
           public String toString() {
               return code;
           }
       };
	/**
	 * 驳回流程-选择审批条件
	 */
	 enum WfChooseAssigneeCondition{

           AUTO("1", "系统自动"),
           HAND("2", "人工选择"),
           NO_LAST_SIGN_TASK("3", "人工选择-会签节点非最后一个节点审批"),
           END_EVENT("4", "结束节点"),
           ASSIST_TASK("5", "协办任务");

           private final String code;

           private final String name;

           private WfChooseAssigneeCondition(String code, String name) {
               this.code = code;
               this.name = name;
           }

           public String getName(){
               return  this.name;
           }
           @Override
           public String toString() {
               return code;
           }
       };
	/**
	 * 驳回流程-消息通知任务状态
	 */
	 enum WfNoticeTaskStatus{
           TODO("1", "待办"),
           DONE("2", "已办"),
           CLAIM("3", "签收"),
           TRANSFER("4", "转办"),
           ASSIST("5", "协办"),
           CANCEL_TRANSFER("6","取消转办"),
           CANCEL_ASSIST("7","取消协办"),
           RECOVER("8","追回"),
           AGENT("10","代理");
           private final String code;

           private final String name;

           private WfNoticeTaskStatus(String code, String name) {
               this.code = code;
               this.name = name;
           }

           public String getName(){
               return  this.name;
           }
           @Override
           public String toString() {
               return code;
           }
       };
	/**
	 * 流程代理-代理状态
	 */
	 enum WfInsStatus{
           TO_START("0", "未开始"),
           RUNNING("1", "进行中"),
           END("2", "结束"),
           STOP("3", "终止"),
           DELETED("4", "进行中"),;

           private final String code;

           private final String name;

           private WfInsStatus(String code, String name) {
               this.code = code;
               this.name = name;
           }

           public String getName(){
               return  this.name;
           }
           @Override
           public String toString() {
               return code;
           }
       };
	/**
	 * 流程代理-代理的终止状态
	 */
	 enum WfAgentTerminationStatus{

           NO("1", "未终止"),
           YES("0", "已终止");

           private final String code;

           private final String name;

           private WfAgentTerminationStatus(String code, String name) {
               this.code = code;
               this.name = name;
           }

           public String getName(){
               return  this.name;
           }
           @Override
           public String toString() {
               return code;
           }
       };
	/**
	 * 流程代理-代理类别
	 */
	 enum WfAgentAuthType{

           ALL("1", "全部代理"),
           PART("0", "部分代理");

           private final String code;

           private final String name;

           private WfAgentAuthType(String code, String name) {
               this.code = code;
               this.name = name;
           }

           public String getName(){
               return  this.name;
           }
           @Override
           public String toString() {
               return code;
           }
       };
	/**
	 * 流程分管授权-流程日志
	 */
	 enum WfProcessOperLogType{
           START_PROCESS("startProcess", "启动"),
           END_PROCESS("endProcess", "结束"),
           SUSPEND_PROCESS("suspendProcess", "挂起"),
           ACTIVATE_PROCESS("activateProcess", "激活"),
           TERMINATE_PROCESS("terminateProcess", "终止"),
           RETRIEVE_PROCESS("retrieveProcess", "追回"),
           CLAIM_TASK("claimTask", "签收"),
           UNCLAIM_TASK("unclaimTask", "取消签收"),
           COMPLETE_TASK("completeTask", "提交"),
           REJECT_TASK("rejectTask", "驳回"),
           REJECT_TO_STARTTASK("rejectToStartTask", "驳回至发起人"),
           TRANSFER_TASK("transferTask", "转办"),
           UNTRANSFER_TASK("untransferTask", "取消转办"),
           ASSIST_TASK("assistTask", "协办"),
           UNASSIST_TASK("unassistTask", "取消协办"),
           ADDSIGN_TASK("addSignTask", "加签"),
           ADD_AGENT("addAgent", "新增代理"),
           STOP_AGENT("stopAgent", "终止代理"),
           DELETE_AGENT("deleteAgent", "删除代理"),
           ARRIVE("arrive", "到达"),
           REMINDER("reminder", "催办"),
           ADD_PARTIAL_AGENT("addPartialAgent", "新增权限代理"),
           STOP_PARTIAL_AGENT("stopPartialAgent", "权限代理终止"),
           AGENT("agent", "代理"),
           UNAGENT("unAgent", "取消代理");

           private final String code;

           private final String name;

           private WfProcessOperLogType(String code, String name) {
               this.code = code;
               this.name = name;
           }

           public String getName(){
               return  this.name;
           }
           @Override
           public String toString() {
               return code;
           }
       };
	/**
	 * 流程定义-启用状态
	 */
	 enum WfDefActiveStatus{

           ENABLE("1", "启用"),
           DISABLE("0", "禁用");

           private final String code;

           private final String name;

           private WfDefActiveStatus(String code, String name) {
               this.code = code;
               this.name = name;
           }

           public String getName(){
               return  this.name;
           }
           @Override
           public String toString() {
               return code;
           }
       };
	/**
	 * 流程定义-发布状态
	 */
	 enum WfDefDeployStatus{

           UNDEPLOY("0", "未发布"),
           DEPLOYED("1", "发布");

           private final String code;

           private final String name;

           private WfDefDeployStatus(String code, String name) {
               this.code = code;
               this.name = name;
           }

           public String getName(){
               return  this.name;
           }
           @Override
           public String toString() {
               return code;
           }
       };
	/**
	 * 流程定义-是否主版本
	 */
	 enum WfProcDefIsMain{

           MAIN("1", "主版本"),
           SUB("0", "子版本");

           private final String code;

           private final String name;

           private WfProcDefIsMain(String code, String name) {
               this.code = code;
               this.name = name;
           }

           public String getName(){
               return  this.name;
           }
           @Override
           public String toString() {
               return code;
           }
       };
	/**
	 * 流程定义-版本号
	 */
	 enum WfProcDefVersion{
           PUBLISHED("1", "草稿"),
           DRAFT("0", "草稿");

           private final String code;

           private final String name;

           private WfProcDefVersion(String code, String name) {
               this.code = code;
               this.name = name;
           }

           public String getName(){
               return  this.name;
           }
           @Override
           public String toString() {
               return code;
           }
       };
	/**
	 * 流程定义-参数配置
	 */
	 enum WfProcessDefinitionVarType{

           NOTIFICATION("wfNotifications", "流程消息"),
           TRANSPOND("wfTranspond","流程转发抄送");

           private final String code;

           private final String name;

           private WfProcessDefinitionVarType(String code, String name) {
               this.code = code;
               this.name = name;
           }

           public String getName(){
               return  this.name;
           }
           @Override
           public String toString() {
               return code;
           }
       };
	/**
	 * 流程定义-流程定义配置
	 */
	 enum WfProcDefConfig{

           WF_USER_CONDITION("wfUserCondition", "节点审批人设置"),
           WF_NODE_OPERATION("wfNodeOperation", "节点操作权限设置"),
           WF_NODE_REMINDER("wfNodeReminder", "节点任务催办设置"),
           WF_NODE_MESSAGE("wfNodeMessage", "节点消息发送设置"),
           WF_NODE_SIGN("wfNodeSign", "节点会签设置"),
           WF_PROC_DEF_AUTHORITY("wfProcDefAuthority", "流程发起权限"),
           WF_NODE_TRANSPOND("wfNodeTranspond", "节点抄送设置");

           private final String code;

           private final String name;

           private WfProcDefConfig(String code, String name) {
               this.code = code;
               this.name = name;
           }

           public String getName(){
               return  this.name;
           }
           @Override
           public String toString() {
               return code;
           }
       };
	/**
	 * 流程定义-流程定义导入错误信息
	 */
	 enum WfProcDefImportResultMsg{

           NO_KEY("noKey", "该流程定义没有key"),
           NO_ACTIVITY_XML("noActivityXml", "该流程定义没有activity原生流程定义文件"),
           NO_NODE_SET("noNodeSet", "该流程定义没有节点信息"),
           SUCCESS("success", "成功"),
           FAILURE("failure", "失败");

           private final String code;

           private final String name;

           private WfProcDefImportResultMsg(String code, String name) {
               this.code = code;
               this.name = name;
           }

           public String getName(){
               return  this.name;
           }
           @Override
           public String toString() {
               return code;
           }
       };
	/**
	 * 流程定义-流程分类
	 */
	 enum WfProcessCategory{

           DEFAULT("default", "默认分类");

           private final String code;

           private final String name;

           private WfProcessCategory(String code, String name) {
               this.code = code;
               this.name = name;
           }

           public String getName(){
               return  this.name;
           }
           @Override
           public String toString() {
               return code;
           }
       };
	/**
	 * 流转任务-任务类型
	 */
	 enum WfDelegateType{

           AGENT("1", "代理任务"),
           TRANSFER("2", "转办任务"),
           ASSIST("3", "协办任务");

           private final String code;

           private final String name;

           private WfDelegateType(String code, String name) {
               this.code = code;
               this.name = name;
           }

           public String getName(){
               return  this.name;
           }
           @Override
           public String toString() {
               return code;
           }
       };
    /**
	 * 流程变量-流程变量名
	 */
	 enum WfVariableKey{

           IS_AGENT("isAgent", "是否代理变量名"),
           MESSAGE_SEND_TYPE("message_send_type", "消息发送方式"),
           MESSGAE_TITLE("message_title", "消息标题"),
           ASSIGNEE("assignee", "指派人"),
           SELECT_ASSIGNEE("select_assignee", "选择的指派人"),
           SELECT_ASSIGNEE_TASK_KEY("select_assignee_task_key", "人工选择节点的taskKey"),
           ADD_SIGN("add_sign", "加签任务"),
           REJECT("reject", "驳回产生的任务"),
           FIRST_NODE("firstNode", "第一个节点"),
           BOM("bom", "bom"),
           IS_NEXT_NODE("isNextNode","会签节点是否结束"),
           ASSIST_FEEDBACK("assistFeedback", "协办任务反馈意见"),
           END_OF_FIRST_NODE_REJECT("end_of_first_node_reject", "驳回到第一节点结束"),
           END_OF_REJECT_TO_START("end_of_reject_to_start", "驳回至发起人结束"),
           RETRIEVE_PROCESS("retrieve_process", "追回流程"),
           STOP_PROCESS("stop_process", "终止流程");

           private final String code;

           private final String name;

           private WfVariableKey(String code, String name) {
               this.code = code;
               this.name = name;
           }

           public String getName(){
               return  this.name;
           }
           @Override
           public String toString() {
               return code;
           }
       };
	/**
	 * 流程变量-流程变量值
	 */
	 enum WfVariableValue{

           IS_AGENT_YES("1", "是否代理变量值——是");

           private final String code;

           private final String name;

           private WfVariableValue(String code, String name) {
               this.code = code;
               this.name = name;
           }

           public String getName(){
               return  this.name;
           }
           @Override
           public String toString() {
               return code;
           }
       };
	/**
	 * 流程变量-流程操作
	 */
	 enum WfProcessOp{

           ACTIVE("active", "激活"),
           SUSPEND("suspend", "挂起");

           private final String code;

           private final String name;

           private WfProcessOp(String code, String name) {
               this.code = code;
               this.name = name;
           }

           public String getName(){
               return  this.name;
           }
           @Override
           public String toString() {
               return code;
           }
       };
	/**
	 * 流程消息-流程模板
	 */
	 enum WfTemplate{

           NODE("node", "节点"),
           SUBJECT("subject", "标题"),
           OPER_USER("operUser", "操作人"),
           FROM_USER("fromUser", "原处理人"),
           TO_USER("toUser", "现处理人"),
           PERIOD("period", "时间段"),
           OPINION("opinion", "操作"),
           REASON("reason", "原因"),
           TIME("time", "时间"),
           PROCESS_DEFINITION("processDefinition", "流程定义名称"),
           SERVER_ROOT_URL("server_root_url", "服务器url"),
           ID("id", "id"),
           REJECT_NODE("rejectNode", "驳回节点名称"),
           ACTION("action", "执行动作");

           private final String code;

           private final String name;

           private WfTemplate(String code, String name) {
               this.code = code;
               this.name = name;
           }

           public String getName(){
               return  this.name;
           }
           @Override
           public String toString() {
               return code;
           }
       };
	/**
	 * 流程消息-流程模板类型
	 */
	 enum WfTemplateType{

           LOG("1", "日志"),
           MESSAGE("2", "消息"),
           APPROVAL_OPINION("3", "审批意见");

           private final String code;

           private final String name;

           private WfTemplateType(String code, String name) {
               this.code = code;
               this.name = name;
           }

           public String getName(){
               return  this.name;
           }
           @Override
           public String toString() {
               return code;
           }
       };
	/**
	 * 流程消息-流程消息类型
	 */
	 enum WfMessageType{

           ARRIVE("arrive", "任务到达"),
           WF_MESSAGE("wfMessage", "流程业务消息"),
           REMINDER("reminder", "任务催办消息");

           private final String code;

           private final String name;

           private WfMessageType(String code, String name) {
               this.code = code;
               this.name = name;
           }

           public String getName(){
               return  this.name;
           }
           @Override
           public String toString() {
               return code;
           }
       };
	/**
	 * 请假流程-实例优先级
	 */
	 enum WfProcessPriority{

           ORDINARY("3", "普通"),
           EMERGENCY("2", "紧急"),
           EXTRA_URGENT("1", "特急");

           private final String code;

           private final String name;

           private WfProcessPriority(String code, String name) {
               this.code = code;
               this.name = name;
           }

           public String getName(){
               return  this.name;
           }
           @Override
           public String toString() {
               return code;
           }
       };
	/**
	 * 流程催办-催办消息执行动作
	 */
	 enum WfReminderAction {

           NO_ACTION("0", "无动作"),
           PASS_ACTION("1", "执行同意操作"),
           REFUSE_ACTION("2", "执行不同意操作"),
           REJECT_ACTION("3", "执行驳回操作"),
           REJECT_PROCESS_ACTION("4", "执行驳回流程操作");

           private final String code;

           private final String name;

           private WfReminderAction(String code, String name) {
               this.code = code;
               this.name = name;
           }

           public String getName(){
               return  this.name;
           }
           @Override
           public String toString() {
               return code;
           }
       };

     enum Separator {
         BPMN_Suffix("bpmn", "bpmn后缀"),
         POINT(".", "符号");

         private final String code;
         private final String name;

         private Separator(String code, String name) {
             this.code = code;
             this.name = name;
         }

         public String getName(){
             return  this.name;
         }
         @Override
         public String toString() {
             return code;
         }
     }


}
