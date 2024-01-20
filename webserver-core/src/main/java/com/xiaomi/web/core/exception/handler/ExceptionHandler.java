package com.xiaomi.web.core.exception.handler;


import com.xiaomi.web.core.exception.RequestInvalidException;
import com.xiaomi.web.core.exception.ServletException;
import com.xiaomi.web.core.network.wrapper.SocketWrapper;
import com.xiaomi.web.core.response.Header;
import com.xiaomi.web.core.response.Response;
import com.xiaomi.web.core.util.IOUtil;
import lombok.extern.slf4j.Slf4j;

import static com.xiaomi.web.core.constant.ContextConstant.ERROR_PAGE;

/**
 * @Author：liuchiyun 会根据异常对应的HTTP Status设置response的状态以及相应的错误页面
 */
@Slf4j
public class ExceptionHandler {

    public void handle(ServletException ex, Response response, SocketWrapper socketWrapper) {
        try {
            log.error("start handle exception. socket:{}", socketWrapper, ex);
            if (ex instanceof RequestInvalidException) {
                log.info("请求无法读取，丢弃");
                socketWrapper.close();
            } else {
                response.addHeader(new Header("Connection", "close"));
                response.setStatus(ex.getHttpStatus());
                response.setBody(IOUtil.getBytesFromFile(String.format(ERROR_PAGE, ex.getHttpStatus().getCode())));
            }
        } catch (Exception e) {
            log.error("handle exception:{} socket:{} failed", ex, socketWrapper, e);
        }
    }
}
