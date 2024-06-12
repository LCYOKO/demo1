package com.xiaomi.demo.spring.mvc;

import com.xiaomi.demo.design.User;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class UserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(User.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        User user = new User();
//        myDevice.setType(nativeWebRequest.getHeader("device.type"));
//        myDevice.setVersion(nativeWebRequest.getHeader("device.version"));
//        myDevice.setScreen(nativeWebRequest.getHeader("device.screen"));
        return user;
    }
}
