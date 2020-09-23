package com.fk.common.exception;

import com.fk.common.domain.ErrorMessage;
import com.fk.common.util.I18nUtils;
import com.fk.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartException;

import java.io.IOException;

/**
 * @Description 全局处理异常信息
 * @Data 2014/12/15 17:09
 */
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private String appNo = "000-";

    /**
     * 400-拦截所有异常
     */
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(Exception.class)
    public ErrorMessage handleAllException(Exception ex) {
        logger.error(ex.getMessage(), ex);
        return new ErrorMessage(ex.getMessage());
    }

    /**
     * 400-业务异常
     *
     * @param ex
     */
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BusinessException.class)
    public ErrorMessage handleBusinessException(BusinessException ex) throws IOException {
        logger.error(ex.getMessage(),ex);
        String errorCode =  this.appNo + ex.getType() +"100";
        if(!StringUtils.isEmpty(ex.getErrorCode())){
            errorCode = this.appNo + ex.getType() + ex.getErrorCode().getCode();
        }
        if(!StringUtils.isEmpty(ex.getData())){
            return new ErrorMessage(errorCode, ex.getMessage(),ex.getData());
        }
        else{
            return new ErrorMessage(errorCode, ex.getMessage());
        }
    }

    /**
     * 400-资源找不到异常
     *
     * @param ex
     * @return
     * @throws IOException
     */
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ErrorMessage handleEmptyResultDataAccessException(EmptyResultDataAccessException ex) throws IOException {
        logger.error(ex.getMessage(), ex);
        return new ErrorMessage(this.appNo + "B-"+"102",
                I18nUtils.getMessage("Common.resourceNotFound"));
    }

    /**
     * 系统异常
     *
     * @param ex
     * @return
     */
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = SystemException.class)
    public ErrorMessage handleSystemException(SystemException ex) {
        logger.error(ex.getMessage(), ex);
        return new ErrorMessage(ex.getMessage());
    }

    /**
     * multipartfile异常
     *
     * @param ex
     * @return
     */
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = MultipartException.class)
    public ErrorMessage handleSystemException(MultipartException ex) {
        logger.error(ex.getMessage(), ex);
        return new ErrorMessage(this.appNo + "B-201", I18nUtils.getMessage("Common.fileSizeNotAllow"));
    }

    /**
     * spring异常
     *
     * @param ex
     * @return
     */
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DataAccessException.class)
    public ErrorMessage handleDataAccessException(DataAccessException ex) {
        logger.error(ex.getMessage(),ex);
        String errorCode =  this.appNo + "B" +"-100";
        return new ErrorMessage(errorCode, "服务器异常！");
    }

    /**
     * 400-Bean validation失败,抛出异常
     *
     * @param ex
     * @return
     * @throws IOException
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessage handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) throws IOException {
        logger.error(ex.getMessage(),ex);
        return new ErrorMessage(ex.getMessage());
    }

    /**
     * 400-当Json序列化，反序列化失败的，抛出异常
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessage handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) throws IOException {
        logger.error(ex.getMessage(),ex);
        return new ErrorMessage(ex.getMessage());
    }

    /**
     * 400-参数验证异常
     *
     * @param ex
     * @return
     */
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorMessage handleIllegalArgumentException(IllegalArgumentException ex) {
        logger.error(ex.getMessage(),ex);
        return new ErrorMessage(ex.getMessage());
    }


    /**
     * 404-资源找不到异常
     *
     * @param ex
     * @return
     * @throws IOException
     */
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ErrorMessage handleNotFoundException(ResourceNotFoundException ex) throws IOException {
        logger.error(ex.getMessage(),ex);
        return new ErrorMessage(this.appNo +"B-102" , ex.getMessage());
    }
}
