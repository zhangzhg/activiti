package com.fk.activiti.service;

import com.fk.activiti.dto.FileDTO;
import com.fk.activiti.model.SysAttachment;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 系统附件接口
 *
 */
public interface ISysAttachmentService {

	/**
	 * 根据ID删除，并删除服务器中的文件
	 *
	 * @param id
	 */
	void deleteById(String id);

	/**
	 * 上传附件
	 *
	 * @param sysAttachment
	 * @param mFile
	 * @return
	 */
	SysAttachment upload(SysAttachment sysAttachment, MultipartFile mFile);

    /**
     * 上传附件
     *
     * @param sysAttachment
     * @param fileDTO
     * @return
     */
    SysAttachment upload(SysAttachment sysAttachment, FileDTO fileDTO);

	/**
	 * 上传多附件
	 * @param module			模块
	 * @param subModule			子级
	 * @param multipartFiles	多附件数组
     * @return					附件DTO集合
     */
	List<SysAttachment> upload(String module, String subModule, MultipartFile[] multipartFiles);

	/**
	 * 下载附件
	 *
	 * @param id
	 * @param request
	 * @param response
	 */
	void download(String id, HttpServletRequest request,
                         HttpServletResponse response);

	/**
	 * 保存时判断重名
	 * @param sysAttachments
	 */
	void createCheckName(List<SysAttachment> sysAttachments);
	
}
