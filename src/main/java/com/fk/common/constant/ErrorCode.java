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

    enum Common implements IMessage {
        /**
         * 未知错误-系统异常-错误码无法识别！
         */
        invalidErrorCode(100),
        /**
         * 未知错误-系统异常-bean实例化出错！
         */
        beanInstanceError(101),
        /**
         * 系统异常-资源找不到！
         */
        resourceNotFound(102),
        /**
         * 系统异常-对象转换异常:{0}
         */
        objectTransferError(103),
        /**
         * 读写相关-文件找不到异常
         */
        fileNotFound(200),
        /**
         * 读写相关-文件超出大小限制
         */
        fileSizeNotAllow(201),
        /**
         * 读写相关-上传失败
         *
         */
        uploadFailed(202),
        /**
         * 读写相关-下载失败
         */
        downloadFailed(203),
        /**
         * 数据库相关-数据库连接失败！
         */
        dbConnectError(300),
        /**
         * 公共模块-值不允许为空，参数：{0}！
         */
        paramNotNull(1000),
        /**
         * 公共模块-参数值不允许为空！
         */
        invalidNotNull(1001),
        /**
         * 公共模块-参数值长度不合法！
         */
        invalidParamLength(1002),
        /**
         * 公共模块-参数值不匹配！{0}
         */
        paramNotMatch(1003),
        /**
         * 公共模块-非法数据格式！
         */
        invalidDataFormat(1004),
        /**
         * 公共模块-正则表达式匹配失败！{0}
         */
        regexNotMatch(1005),
        /**
         * 公共模块-文件格式不正确！
         */
        invalidFileFormat(1006),
        /**
         * 公共模块-缺少方法名参数！
         */
        invalidMethodArgument(1007),
        /**
         * 公共模块-{0}：Bean反序列化出错！
         */
        beanSerializeError(1008),
        /**
         * 公共模块-值必须为true，参数：{0}
         */
        paramIsTrue(1009),
        /**
         * 公共模块-值必须为true
         */
        invalidIsTrue(1010),
        /**
         * 公共模块-值必须为空，参数：{0}
         */
        paramIsNull(1011),
        /**
         * 公共模块-值必须为空
         */
        invalidIsNull(1012),
        /**
         * 公共模块-字符串必须有值，参数：{0}
         */
        paramHasLength(1013),
        /**
         * 公共模块-字符串必须有值
         */
        invalidHasLength(1014),
        /**
         * 公共模块-字符串String必须有有效值，参数：{0}
         */
        paramHasText(1015),
        /**
         * 公共模块-字符串String必须有有效值
         */
        invalidHasText(1016),
        /**
         * 公共模块-字符串{0}必须不包含字符串{1}
         */
        invalidNotContainArgs(1017),
        /**
         * 公共模块-字符串必须不包含子字符串
         */
        invalidNotContain(1018),
        /**
         * 公共模块-数组必须有值，参数：{0}
         */
        invalidArrayNotEmptyArgs(1019),
        /**
         * 公共模块-数组必须有值
         */
        invalidArrayNotEmpty(1020),
        /**
         * 公共模块-数组中每个项必须不为空，参数：{0}
         */
        invalidElementsNotNullArgs(1021),
        /**
         * 公共模块-数组中每个项必须不为空
         */
        invalidElementsNotNull(1022),
        /**
         * 公共模块-集合必须有值，参数{0}
         */
        invalidCollectionNotEmptyArgs(1023),
        /**
         * 公共模块-集合必须有值
         */
        invalidCollectionNotEmpty(1024),
        /**
         * 公共模块-Map必须有值，参数{0}
         */
        invalidMapNotEmptyArgs(1025),
        /**
         * 公共模块-Map必须有值
         */
        invalidMapNotEmpty(1026),
        /**
         * 公共模块-对象{1}必须属于类{0}
         */
        invalidIsInstanceOfArgs(1027),
        /**
         * 公共模块-对象必须属于类
         */
        invalidIsInstanceOf(1028),
        /**
         * 公共模块-类{1}必须为类{0}的子类
         */
        invalidIsAssignableArgs(1029),
        /**
         * 公共模块-类2必须为类1的子类
         */
        invalidIsAssignable(1030),
        /**
         * 公共模块-密码输入错误！
         */
        pwdNotMatch(1031),
        /**
         * 公共模块-两次输入不一致，请重新输入
         */
        fieldConfirmError(1032),
        /**
         * 权限模块-账号未注册！
         */
        userNotRegister(1101),
        /**
         * 权限模块-账号已锁定！
         */
        userLocked(1102),
        /**
         * 权限模块-会话已过期！
         */
        sessionTimeout(1103),
        /**
         * 权限模块-无权限操作！
         */
        notPermissions(1104),
        /**
         * 权限模块-账号或密码输入有误！
         */
        userPwdNotMatch(1100);

        private int code;

        private String category;

        Common(int code){
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
