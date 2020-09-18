package com.fk.activiti.model;

/**
 * 用户实体类
 *
 */
public class ActivitiUser {
	/**
	 * 编号
	 */
	private String id;

	/**
	 * 主机构
	 */
	private String orgId;

	/**
	 * 主部门
	 */
	private String deptId;

	/**
	 * 姓名
	 */
	private String name;

	/**
	 * 帐号
	 */
	private String account;

	/**
	 * 性别
	 */
	private String sex;

	/**
	 * 邮箱
	 */
	private String email;

	/**
	 * 地址
	 */
	private String address;

	/**
	 * 手机号码
	 */
	private String mobile;

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
