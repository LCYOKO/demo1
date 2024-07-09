package com.xiaomi.demo.filter;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.http.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * @author project
 * @date 2020/1/8 密码解密工具类
 * <p>
 * 参考 ModifyRequestBodyGatewayFilterFactory 实现
 */
@Slf4j
public class PasswordDecoderFilter implements Filter {

//    private final List<HttpMessageReader<?>> messageReaders = HandlerStrategies.withDefaults().messageReaders();

    private static final String PASSWORD = "password";

    private static final String KEY_ALGORITHM = "AES";

    private final static String PASSWORD_REGEX = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[^A-Za-z\\d]).{8,16}$";


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        // 解密生成新的报文
        String toString = IOUtils.toString(request.getInputStream(), Charset.defaultCharset());
        Map<String, String> inParamsMap = HttpUtil.decodeParamMap(toString, CharsetUtil.CHARSET_UTF_8);
        inParamsMap.put("name", "lisi");
        String params = HttpUtil.toParams(inParamsMap, Charset.defaultCharset(), true);
        filterChain.doFilter(new ModifyPasswordRequestWrapper(request, params, inParamsMap), servletResponse);
    }
}
