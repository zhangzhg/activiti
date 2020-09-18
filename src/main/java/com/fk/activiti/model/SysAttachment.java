package com.fk.activiti.model;

import com.fk.activiti.domain.BaseModel;
import com.fk.common.util.FileUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;

/**
 * 系统附件模型
 * 
 * @author lizhuofeng
 *
 */
@Entity
@Table(name = "sys_attachment")
public class SysAttachment extends BaseModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7607485310306029216L;

	/**
	 * 附件名称
	 */
	@Column
	private String name;

	/**
	 * 附件后缀
	 */
	@Column
	private String type;

	/**
	 * 保存路径
	 */
	@Column
	private String path;

	/**
	 * 附件大小
	 */
	@Column
	private long size;

	/**
	 * 所属模块
	 */
	@Column
	private String module;

	/**
	 * 关联子模块对象类型
	 */
	@Column(name="sub_module")
	private String subModule;

	/**
	 * 创建人
	 */
	@Column(name="creator_id")
	private String creatorId;

	/**
	 * 创建时间
	 */
	@Column(name="created_time")
	private Date createdTime;

	/**
	 * 文件大小（带单位）
	 */
	@Transient
	private String fileSizeWithUnit;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getSubModule() {
		return subModule;
	}

	public void setSubModule(String subModule) {
		this.subModule = subModule;
	}

	public String getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public String getFileSizeWithUnit() {
		return FileUtils.getSize((double) this.getSize());
	}

	public void setFileSizeWithUnit(String fileSizeWithUnit) {
		this.fileSizeWithUnit = fileSizeWithUnit;
	}

	public String getFullName() {
		return name + "." + type;
	}
	
}
