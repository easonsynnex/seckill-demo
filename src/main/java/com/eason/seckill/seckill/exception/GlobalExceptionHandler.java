package com.eason.seckill.seckill.exception;

import com.eason.seckill.seckill.result.CodeMsg;
import com.eason.seckill.seckill.result.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: eason
 * @Date: Created in 20:13 2020/2/27
 * @Description:
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    private final static Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(GlobalException.class)
    public Result<String> exceptionHandler(HttpServletRequest request, GlobalException e){
        LOGGER.error("GlobalException:", e);

        return Result.error(e.getCodeMsg());
    }
    @ExceptionHandler(BindException.class)
    public Result<String> exceptionHandler(HttpServletRequest request, BindException e){
        LOGGER.error("BindException:", e);

        return Result.error(CodeMsg.ARGUMENT_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public Result<String> exceptionHandler(HttpServletRequest request, Exception e){
        LOGGER.error("Exception:", e);
        return Result.error(CodeMsg.SERVER_ERROR);
    }
}
