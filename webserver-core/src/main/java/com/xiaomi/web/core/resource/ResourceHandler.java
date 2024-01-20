package com.xiaomi.web.core.resource;

import com.xiaomi.web.core.constant.CharsetProperties;
import com.xiaomi.web.core.exception.RequestParseException;
import com.xiaomi.web.core.exception.ResourceNotFoundException;
import com.xiaomi.web.core.exception.ServletException;
import com.xiaomi.web.core.exception.handler.ExceptionHandler;
import com.xiaomi.web.core.network.wrapper.nio.NioSocketWrapper;
import com.xiaomi.web.core.request.Request;
import com.xiaomi.web.core.response.Response;
import com.xiaomi.web.core.template.TemplateResolver;
import com.xiaomi.web.core.util.IOUtil;
import com.xiaomi.web.core.util.MimeTypeUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * Created by SinjinSong on 2017/7/20.
 * 用于处理静态资源
 */
@Slf4j
public class ResourceHandler {
    private ExceptionHandler exceptionHandler;

    public ResourceHandler(ExceptionHandler exceptionHandler) {
        this.exceptionHandler = exceptionHandler;
    }

    public void handle(Request request, Response response, NioSocketWrapper socketWrapper) {
        String url = request.getUrl();
        try {
            if (ResourceHandler.class.getResource(url) == null) {
                log.info("找不到该资源:{}", url);
                throw new ResourceNotFoundException();
            }
            byte[] body = IOUtil.getBytesFromFile(url);
            if (url.endsWith(".html")) {
                body = TemplateResolver
                        .resolve(new String(body, CharsetProperties.UTF_8_CHARSET), request)
                        .getBytes(CharsetProperties.UTF_8_CHARSET);
            }
            response.setContentType(MimeTypeUtil.getTypes(url));
            response.setBody(body);
        } catch (IOException e) {
            exceptionHandler.handle(new RequestParseException(), response, socketWrapper);
        } catch (ServletException e) {
            exceptionHandler.handle(e, response, socketWrapper);
        }
    }
}