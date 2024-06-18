package com.xiaomi.demo.spring.mvc;


import com.xiaomi.common.web.constants.HttpCode;
import com.xiaomi.common.web.exception.InvalidParamException;
import com.xiaomi.common.web.result.JsonResult;
import com.xiaomi.common.web.result.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 业务异常拦截
     */
    @ExceptionHandler(ServiceException.class)
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public JsonResult<String> serviceExceptionHandler(ServiceException serviceException) {
        log.error("业务服务异常:", serviceException);
        return JsonResult.error(serviceException.getHttpCode(), serviceException.getMessage());
    }


    /**
     * 业务异常拦截
     */
    @ExceptionHandler(InvalidParamException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public JsonResult<String> invalidParamExceptionHandler(InvalidParamException invalidParamException) {
        log.error("业务服务异常:", invalidParamException);
        return JsonResult.error(HttpCode.PARAM_ERROR, invalidParamException.getMessage());
    }

    /**
     * 业务异常拦截
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public JsonResult<String> invalidFormatExceptionHandler(HttpMessageNotReadableException httpMessageNotReadableException) {
        log.error("业务服务异常:", httpMessageNotReadableException);
        return JsonResult.error(HttpCode.PARAM_FORMAT_ERROR);
    }

    /**
     * 业务异常拦截
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public JsonResult<String> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException methodArgumentNotValidException) {
        log.error("业务服务异常:", methodArgumentNotValidException);
        if (!methodArgumentNotValidException.getFieldErrors().isEmpty()) {
            return JsonResult.error(HttpCode.PARAM_ERROR, methodArgumentNotValidException.getFieldErrors().get(0).getDefaultMessage());
        }
        return JsonResult.error(HttpCode.PARAM_ERROR);
    }

    @ExceptionHandler
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public JsonResult<?> paramError(BindException ex) {
        log.error("paramError:{}", ex.getAllErrors());
        return JsonResult.error();
    }

    @ExceptionHandler
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public JsonResult<Void> paramHasError(ConstraintViolationException ex) {
        log.error("paramError", ex);
        return JsonResult.error();
    }

    /**
     * 业务异常拦截
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(code = HttpStatus.METHOD_NOT_ALLOWED)
    public JsonResult<String> httpRequestMethodNotSupportedExceptionHandler(HttpRequestMethodNotSupportedException httpRequestMethodNotSupportedException) {
        log.error("业务服务异常:", httpRequestMethodNotSupportedException);
        return JsonResult.error(HttpCode.PARAM_REQUEST_METHOD_ERROR, httpRequestMethodNotSupportedException.getMessage());
    }

    @ExceptionHandler(value = {RuntimeException.class, Exception.class})
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public JsonResult<?> handleRuntimeException(HttpServletRequest request, HandlerMethod method, Exception e) {
        log.error("未处理的全局异常,method:{}", method.getMethod().toGenericString(), e);
        return JsonResult.error(HttpCode.ERROR, HttpCode.ERROR.getMsg());
    }
}
