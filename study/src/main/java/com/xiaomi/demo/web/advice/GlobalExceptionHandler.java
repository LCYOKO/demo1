package com.xiaomi.demo.web.advice;

import com.xiaomi.demo.web.constants.HttpCode;
import com.xiaomi.demo.web.exception.InvalidParamException;
import com.xiaomi.demo.web.result.JsonResult;
import com.xiaomi.demo.web.result.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: yukai
 * @create: 2023-11-20 19:18:51
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 业务异常拦截
     */
    @ExceptionHandler(ServiceException.class)
    public JsonResult<String> serviceExceptionHandler(ServiceException serviceException) {
        log.error("业务服务异常:", serviceException);
        return JsonResult.error(serviceException.getHttpCode(), serviceException.getMessage());
    }


    /**
     * 业务异常拦截
     */
    @ExceptionHandler(InvalidParamException.class)
    public JsonResult<String> invalidParamExceptionHandler(InvalidParamException invalidParamException) {
        log.error("业务服务异常:", invalidParamException);
        return JsonResult.error(HttpCode.PARAM_ERROR, invalidParamException.getMessage());
    }

    /**
     * 业务异常拦截
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public JsonResult<String> invalidFormatExceptionHandler(HttpMessageNotReadableException httpMessageNotReadableException) {
        log.error("业务服务异常:", httpMessageNotReadableException);
        return JsonResult.error(HttpCode.PARAM_FORMAT_ERROR);
    }

    /**
     * 业务异常拦截
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public JsonResult<String> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException methodArgumentNotValidException) {
        log.error("业务服务异常:", methodArgumentNotValidException);
        if (!methodArgumentNotValidException.getFieldErrors().isEmpty()) {
            return JsonResult.error(HttpCode.PARAM_ERROR, methodArgumentNotValidException.getFieldErrors().get(0).getDefaultMessage());
        }
        return JsonResult.error(HttpCode.PARAM_ERROR);
    }

    /**
     * 业务异常拦截
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public JsonResult<String> httpRequestMethodNotSupportedExceptionHandler(HttpRequestMethodNotSupportedException httpRequestMethodNotSupportedException) {
        log.error("业务服务异常:", httpRequestMethodNotSupportedException);
        return JsonResult.error(HttpCode.PARAM_REQUEST_METHOD_ERROR, httpRequestMethodNotSupportedException.getMessage());
    }


    @ExceptionHandler(value = {RuntimeException.class, Exception.class})
    public JsonResult<?> handleRuntimeException(HttpServletRequest request, Exception e) {
        log.error("未处理的全局异常", e);
        return JsonResult.error(HttpCode.ERROR, HttpCode.ERROR.getMsg());
    }
}
