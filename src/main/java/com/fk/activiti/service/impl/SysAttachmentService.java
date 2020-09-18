package com.fk.activiti.service.impl;

import com.fk.activiti.dto.FileDTO;
import com.fk.activiti.model.SysAttachment;
import com.fk.activiti.repository.SysAttachmentRepository;
import com.fk.activiti.service.ISysAttachmentService;
import com.fk.common.constant.ErrorCode;
import com.fk.common.exception.BusinessException;
import com.fk.common.util.DownLoadUtils;
import com.fk.common.util.FileUtils;
import com.fk.common.util.SessionUtils;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 系统文件接口实现
 */
@Service
public class SysAttachmentService implements ISysAttachmentService {
	private Logger logger = LoggerFactory.getLogger(SysAttachmentService.class);

	@Autowired
	SysAttachmentRepository repository;

	@Value("${upload.path}")
	String uploadPath;

	@Override
	public void deleteById(String id) {
		SysAttachment sysAttachment = repository.findOne(id);
		File file = new File(sysAttachment.getPath());
		// 删除服务器中的文件
		if (file.exists()) {
			file.delete();
		}
		repository.deleteById(id);
	}

	@Override
	public SysAttachment upload(SysAttachment sysAttachment, MultipartFile mFile) {
		String fileName = mFile.getOriginalFilename(); // 上传的文件名称
		String suffix = FileUtils.getSuffix(fileName);
		String model = sysAttachment.getModule();
		String subModel = sysAttachment.getSubModule();
		String path = FileUtils.getUploadPath(uploadPath, fileName, model, subModel);
		File file = new File(path);
        try {
			if (!file.exists()) {
				file.createNewFile();
			}
			// 这个地方是个bug般的存在，参数用file不行，要绝对路径
            mFile.transferTo(file.getAbsoluteFile());
        } catch (IOException e) {
            throw new BusinessException(ErrorCode.File.uploadFailed); // 文件写入磁盘失败
        }
		sysAttachment.setName(fileName);
		sysAttachment.setType(suffix);
		sysAttachment.setPath(path);
		sysAttachment.setSize(mFile.getSize());
		sysAttachment.setCreatorId(SessionUtils.getCurrentUserId());
		sysAttachment.setCreatedTime(new Date());
		return repository.save(sysAttachment);
	}

    @Override
    public SysAttachment upload(SysAttachment sysAttachment, FileDTO fileDTO){
        String fileName = fileDTO.getName(); // 上传的文件名称
        String suffix = FileUtils.getSuffix(fileName);
		String model = sysAttachment.getModule();
		String subModel = sysAttachment.getSubModule();
        String path = FileUtils.getUploadPath(uploadPath, fileName, model, subModel);

        File file = new File(path);
        file.getParentFile().mkdirs();
        FileOutputStream fileOutputStream = null;
        long size;
        try {
            file.createNewFile();
            fileOutputStream = new FileOutputStream(file);
            byte[] bytes = Base64.decodeBase64(fileDTO.getContent());
            fileOutputStream.write(bytes);
            size = bytes.length;
        } catch (IOException e) {
            throw new BusinessException(ErrorCode.File.uploadFailed); // 文件写入磁盘失败
        }finally {
            try {
                fileOutputStream.close();
            } catch (IOException e) {
           		logger.error(e.getMessage(), e);
            }
        }
        sysAttachment.setName(fileName);
        sysAttachment.setType(suffix);
        sysAttachment.setPath(path);
        sysAttachment.setSize(size);
        sysAttachment.setCreatorId(SessionUtils.getCurrentUserId());
        sysAttachment.setCreatedTime(new Date());
        return repository.save(sysAttachment);
    }

	@Override
	public List<SysAttachment> upload(String module, String subModule, MultipartFile[] multipartFiles) {
		List<SysAttachment> sysAttachments = new ArrayList<>();
		for (MultipartFile multipartFile : multipartFiles) {
			SysAttachment sysAttachment = new SysAttachment();
			sysAttachment.setModule(module);
			sysAttachment.setSubModule(subModule);
			upload(sysAttachment, multipartFile);
			sysAttachments.add(sysAttachment);
		}
		return sysAttachments;
	}

	@Override
	public void download(String id, HttpServletRequest request,
			HttpServletResponse response) {
		SysAttachment sysAttachment = repository.findOne(id);
		String name = sysAttachment.getName();
		String path = sysAttachment.getPath();
		try {
			DownLoadUtils.downLoadFile(request, response, path, name);
		} catch (IOException e) {
			throw new BusinessException(ErrorCode.File.downloadFailed); // 文件下载失败
		}
	}

	@Override
	public void createCheckName(List<SysAttachment> sysAttachments) {
		if (CollectionUtils.isNotEmpty(sysAttachments) && sysAttachments.size() == 1) {
			return;
		}
		if (CollectionUtils.isEmpty(sysAttachments)) {
			return;
		}

		Map<String, List<SysAttachment>> fullname2SysAttachment = sysAttachments.stream()
				.collect(Collectors.groupingBy(SysAttachment::getFullName));
		fullname2SysAttachment.forEach((key, value) -> {
			if (value.size() > 1) {
				sysAttachments.forEach(sysAttachment -> {
					File file = new File(sysAttachment.getPath());
					// 删除服务器中的文件
					if (file.exists()) {
						file.delete();
					}
				});
				throw new BusinessException("不能上传同名附件！");
			}
		});
	}

}
