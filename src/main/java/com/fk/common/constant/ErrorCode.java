package com.fk.common.constant;


public interface ErrorCode {
    enum Bpm implements IMessage {
        /**
         * 流程基础-流程图获取失败！
         */
        diagramError(2000),
        /**
         * 流程基础-不能移动至默认分类下！
         */
        unmoveDefaultGategory(2001),
        /**
         * 流程基础-{0}:【{1}】没有配置人员！
         */
        notSetUsers(2002),
        /**
         * 流程基础-节点：{0}，没有设置执行人！
         */
        nodeNoSetAssignee(2003),
        /**
         * 流程基础-请设置会签节点:[{0}]的人员!
         */
        signnodeNoSetUsers(2004),
        /**
         * 流程基础-节点配置的人员信息已经被删除
         */
        nodeUserHasDeleted(2005),
        /**
         * 流程定义-预导入失败！
         */
        preimportFailure(2100),
        /**
         * 流程定义-流程定义文件识别失败！
         */
        fileReadFailure(2101),
        /**
         * 流程定义-解析流程文件失败！
         */
        fileConvertFailure(2102),
        /**
         * 流程定义-流程文件导入有误，请重新导入
         */
        importFailure(2103),
        /**
         * 流程定义-该流程定义没有配置发起表单formKey！
         */
        noSetFormKey(2104),
        /**
         * 流程定义-该流程定义无任务节点，请重新导入！
         */
        noUserTask(2105),
        /**
         * 流程定义-查询流程定义名称是否重名时报错！
         */
        queryRepeatnameFailure(2106),
        /**
         * 流程定义-已存在相同的流程名称！
         */
        nameIsExists(2107),
        /**
         * 流程定义-添加流程模型资源出错！
         */
        saveDefModelError(2108),
        /**
         * 流程定义-流程定义发布失败！
         */
        deployFailure(2109),
        /**
         * 流程定义-该流程定义已发布，删除失败！
         */
        deleteFailureOfDeployed(2110),
        /**
         * 流程定义-节点配置复制失败！
         */
        nodeSetCopyFailure(2111),
        /**
         * 流程定义-级联删除节点配置出错！
         */
        nodeSetDeleteError(2112),
        /**
         * 流程定义-非草稿版本流程定义不可修改！
         */
        nonDraftNotUpdate(2113),
        /**
         * 流程定义-用户没有该流程定义的权限！
         */
        noDefPermissions(2114),
        /**
         * 流程定义-解析流程发起表单失败
         */
        readStartFormFail(2115),
        /**
         * 流程定义-发布检查节点人员配置
         */
        noConfigWfNodeUser(2116),
        /**
         * 流程定义-发布检查流程操作配置
         */
        noConfigWfNodeOperation(2117),
        /**
         * 流程定义-发布检查节点人员配置和流程操作配置
         */
        noConfigWfNodeUserAndWfNodeOperation(2118),
        /**
         * 流程代理-新增代理失败，不能代理给本人！
         */
        agentErrorToSelf(2200),
        /**
         * 流程代理-新增代理失败，存在冲突的代理！
         */
        agentErrorConflict(2201),
        /**
         * 流程代理-新增代理失败，存在循环的代理！
         */
        agentErrorLoop(2202),
        /**
         * 流程节点-用户节点不能配置会签规则！
         */
        nodeNotSetSign(2301),
        /**
         * 流程节点-该流程文件无任务节点，请重新导入！
         */
        processNotHasNode(2302),
        /**
         * 发送代理消息通知失败!
         */
        messageSendFailure(2401),
        /**
         * 流程操作日志保存失败!
         */
        processLogSaveFailure(2501),
        /**
         * 流程任务操作日志保存失败!
         */
        processTaskLogSaveFailure(2502),
        /**
         * 代理任务日志保存失败!
         */
        agentTaskLogSaveFilure(2503),
        /**
         * 流程操作消息通知发送失败!
         */
        processMsgNotifySendFailure(2504),
        /**
         * 流程实例更改状态失败!
         */
        processInstUpdateFailure(2505),
        /**
         * 催办执行动作出错!
         */
        reminderDoActionError(2506),
       /**
        * 目标对象不存在
        */
        targetNotExist(2507),

        /**
         * 流程任务监听出现异常！
         */
        processListenerError(2508),

        /**
         * 导入流程定义文件出错！
         */
        importProcDefError(2509),

        /**
		 * 定时调度-定时任务创建失败
		 */
		quartzCreateFailed(2508);


        private int code;
        private String category;

        Bpm(int code){
            this.code = code;
            this.category = this.getClass().getSimpleName();
        }
        public int getCode(){
            return code;
        }
        public String getCategory(){return category;}
    };

    enum File implements IMessage {
        /**
         * 文件上传失败
         */
        uploadFailed(10001),
        /**
         * 文件下载失败
         */
        downloadFailed(1002);

        private int code;
        private String category;

        File(int code){
            this.code = code;
            this.category = this.getClass().getSimpleName();
        }
        public int getCode(){
            return code;
        }
        public String getCategory(){return category;}
    }
}
