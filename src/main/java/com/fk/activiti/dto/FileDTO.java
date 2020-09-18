package com.fk.activiti.dto;

/**
 * 功能描述：用于二进制上传文件使用
 */
public class FileDTO {

    /**
     * 文件名称
     */
    private String name;
    /**
     * 文件内容
     */
    private String content;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
