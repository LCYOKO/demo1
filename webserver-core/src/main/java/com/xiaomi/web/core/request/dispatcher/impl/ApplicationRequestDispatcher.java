package com.xiaomi.web.core.request.dispatcher.impl;


import com.xiaomi.web.core.constant.CharsetProperties;
import com.xiaomi.web.core.exception.ResourceNotFoundException;
import com.xiaomi.web.core.exception.base.ServletException;
import com.xiaomi.web.core.request.Request;
import com.xiaomi.web.core.request.dispatcher.RequestDispatcher;
import com.xiaomi.web.core.resource.ResourceHandler;
import com.xiaomi.web.core.response.Response;
import com.xiaomi.web.core.template.TemplateResolver;
import com.xiaomi.web.core.util.IOUtil;
import com.xiaomi.web.core.util.MimeTypeUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * Created by SinjinSong on 2017/7/21.
 * 请求转发器
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class ApplicationRequestDispatcher implements RequestDispatcher {
    private String url;
    
    @Override
    public void forward(Request request, Response response) throws ServletException, IOException {
        if (ResourceHandler.class.getResource(url) == null) {
            throw new ResourceNotFoundException();
        }
        log.info("forward至 {} 页面",url);
        String body = TemplateResolver.resolve(new String(IOUtil.getBytesFromFile(url), CharsetProperties.UTF_8_CHARSET),request);
        response.setContentType(MimeTypeUtil.getTypes(url));
        response.setBody(body.getBytes(CharsetProperties.UTF_8_CHARSET));
    }
}
