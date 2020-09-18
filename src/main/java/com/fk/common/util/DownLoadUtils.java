package com.fk.common.util;

import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;

public class DownLoadUtils {

    public static void downLoadFileByByte(HttpServletRequest request, HttpServletResponse response, byte[] b, String fileName)
            throws IOException {
        OutputStream outp = response.getOutputStream();
        if (b.length > 0) {
            response.setContentType("APPLICATION/OCTET-STREAM");
            String filedisplay = fileName;
            String agent = request.getHeader("USER-AGENT");

            if ((agent != null) && (agent.indexOf("MSIE") == -1)) {
                String enableFileName = "=?UTF-8?B?" + new String(Base64Utils.getBase64(filedisplay)) + "?=";
                response.setHeader("Content-Disposition", "attachment; filename=" + enableFileName);
            } else {
                filedisplay = URLEncoder.encode(filedisplay, "utf-8");
                response.addHeader("Content-Disposition", "attachment;filename=" + filedisplay);
            }
            outp.write(b);
        } else {
            outp.write("文件不存在!".getBytes("utf-8"));
        }
        if (outp != null) {
            outp.close();
            outp = null;
            response.flushBuffer();
        }
    }

    /**
     * 下载文件
     *
     * @param request  请求对象
     * @param response 响应对象
     * @param fullPath 下载文件路径
     * @throws IOException
     */
    public static void downLoadFile(HttpServletRequest request, HttpServletResponse response, String fullPath) throws
            IOException {
        downLoadFile(request, response, fullPath, fullPath.substring(fullPath.lastIndexOf(File.separator) + 1));
    }

    /**
     * 下载文件
     *
     * @param request  请求对象
     * @param response 响应对象
     * @param fullPath 下载文件路径
     * @param fileName 指定下载文件名称
     * @throws IOException
     */
    public static void downLoadFile(HttpServletRequest request, HttpServletResponse response, String fullPath, String fileName)
            throws IOException {
        OutputStream outp = response.getOutputStream();
        File file = new File(fullPath);
        if (file.exists()) {
            response.setContentType("APPLICATION/OCTET-STREAM");
            String agent = request.getHeader("USER-AGENT");

            if ((agent != null) && (agent.indexOf("MSIE") == -1) && (agent.indexOf("Trident") == -1)) {
                String enableFileName = "=?UTF-8?B?" + new String(Base64Utils.getBase64(fileName)) + "?=";
                response.setHeader("Content-Disposition", "attachment; filename=" + enableFileName);
            } else {
                fileName = URLEncoder.encode(fileName, "utf-8");
                response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
            }
            FileInputStream in = null;
            try {
                outp = response.getOutputStream();
                in = new FileInputStream(fullPath);
                byte[] b = new byte[1024];
                int i = 0;
                while ((i = in.read(b)) > 0) {
                    outp.write(b, 0, i);
                }
                outp.flush();
            } finally {
                if (in != null) {
                    in.close();
                    in = null;
                }
                if (outp != null) {
                    outp.close();
                    outp = null;
                    response.flushBuffer();
                }
            }
        } else {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.getOutputStream().close();
        }
    }
}
