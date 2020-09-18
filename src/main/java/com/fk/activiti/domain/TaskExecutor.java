package com.fk.activiti.domain;

import java.io.Serializable;

public class TaskExecutor implements Serializable {
	private static final long serialVersionUID = 3323857815331061751L;
	public static final String NOEXACT = "0";
	public static final String EXACT = "1";
	public static final String USER_TYPE_USER = "SysUser";
	public static final String USER_TYPE_POSITION = "SysPosition";
	public static final String USER_TYPE_ROLE = "SysRole";
	public static final String USER_TYPE_ORG = "SysOrg";
	public static final String USER_TYPE_DEPT = "SysDept";
	public static final String USER_TYPE_GROUP = "SysGroup";
	private String type = "user";
	private String executeId = "";

	private String executor = "";

	private int exactType = 0;

	private String extract;

	public String getExtract() {
		return extract;
	}

	public void setExtract(String extract) {
		this.extract = extract;
	}

	public TaskExecutor(String type, String executeId, String name, String extract) {
		this.type = type;
		this.executeId = executeId;
		this.executor = name;
		this.extract = extract;
	}

	public static TaskExecutor getTaskUser(String executeId, String name,String extract) {
		return new TaskExecutor(USER_TYPE_USER, executeId, name,extract);
	}

	public static TaskExecutor getTaskOrg(String executeId, String name,String extract) {
		return new TaskExecutor(USER_TYPE_ORG, executeId, name,extract);
	}

	public static TaskExecutor getTaskRole(String executeId, String name,String extract) {
		return new TaskExecutor(USER_TYPE_ROLE, executeId, name,extract);
	}

	public static TaskExecutor getTaskDept(String executeId, String name,String extract) {
		return new TaskExecutor(USER_TYPE_DEPT, executeId, name,extract);
	}

	public static TaskExecutor getTaskPosition(String executeId, String name,String extract) {
		return new TaskExecutor(USER_TYPE_POSITION, executeId, name,extract);
	}

	public static TaskExecutor getTaskGroup(String executeId, String name,String extract) {
		return new TaskExecutor(USER_TYPE_GROUP, executeId, name,extract);
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getExecuteId() {
		return this.executeId;
	}

	public void setExecuteId(String executeId) {
		this.executeId = executeId;
	}

	public String getExecutor() {
		return this.executor;
	}

	public void setExecutor(String executor) {
		this.executor = executor;
	}

	public int getExactType() {
		return this.exactType;
	}

	public void setExactType(int exactType) {
		this.exactType = exactType;
	}

	public boolean equals(Object obj) {
		if (!(obj instanceof TaskExecutor)) {
			return false;
		}
		TaskExecutor tmp = (TaskExecutor) obj;
		if ((this.type.equals(tmp.getType()))
				&& (this.executeId.equals(tmp.getExecuteId()))) {
			return true;
		}
		return false;
	}

	public int hashCode() {
		String tmp = this.type + this.executeId;
		return tmp.hashCode();
	}

}
