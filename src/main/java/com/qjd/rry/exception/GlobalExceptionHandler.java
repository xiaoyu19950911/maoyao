package com.qjd.rry.exception;

import com.qjd.rry.enums.ResultEnums;
import com.qjd.rry.utils.Result;
import com.qjd.rry.utils.ResultUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/**
 * @Author: xiaoyu
 * @Descripstion:
 * @Date:Created in 2018/2/2 10:29
 * @Modified By:
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private final static Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * @desctiption: 处理所有业务异常
     * @param e
     * @return
     */
    @ExceptionHandler(CommunalException.class)
    @ResponseBody
    public Result handleCommunalException(CommunalException e) {
        LOGGER.error(e.getMessage(), e);
        return ResultUtils.error(e.getCode(), e.getMessage());
    }

    /**
     * @desctiption: 处理所有未知异常
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result handleException(Exception e){
        LOGGER.error(e.getMessage(),e);
        return ResultUtils.error(ResultEnums.UNKNOW_ERROR);
    }

    /**
     * @desctiption: 处理权限不足
     * @param e
     * @return
     */
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseBody
    public Result securityException(AccessDeniedException e){
        LOGGER.error(e.getMessage(),e);
        return ResultUtils.error(ResultEnums.NO_PERMISSION.getCode(),ResultEnums.NO_PERMISSION.getMsg());
    }

    /**
     * @desctiption: 处理参数错误
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseBody
    public Result methodArgumentException(MethodArgumentTypeMismatchException e){
        LOGGER.error(e.getMessage(),e);
        return ResultUtils.error(ResultEnums.PARAMETERS_ERROR.getCode(),ResultEnums.PARAMETERS_ERROR.getMsg());
    }

    /**
     * @desctiption: 处理密码错误
     * @param e
     * @return
     */
    @ExceptionHandler(BadCredentialsException.class)
    @ResponseBody
    public Result methodArgumentException(BadCredentialsException e){
        LOGGER.error(e.getMessage(),e);
        return ResultUtils.error(-1,"密码错误！");
    }

    /**
     * @desctiption: 处理账号异常
     * @param e
     * @return
     */
    @ExceptionHandler(AccountException.class)
    @ResponseBody
    public Result accountException(AccountException e){
        LOGGER.error(e.getMessage(),e);
        return ResultUtils.error(e.getCode(),e.getMessage());
    }
}