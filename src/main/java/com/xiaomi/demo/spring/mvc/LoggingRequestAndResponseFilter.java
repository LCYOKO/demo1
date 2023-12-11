package com.xiaomi.demo.spring.mvc;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.MDC;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.Set;

/**
 * @Author: liuchiyun
 * @Date: 2023/12/9
 */
@Slf4j
public class LoggingRequestAndResponseFilter extends CommonsRequestLoggingFilter {
    /**
     * 接口请求 开始 日志前缀标识，目的是为了提高每次判断时的性能
     */
    public final static String REQUEST_START_PREFIX_FLAG = "0";
    /**
     * 接口请求 开始 日志前缀
     */
    private final static String REQUEST_START_PREFIX = "### request start[";
    /**
     * 接口请求 结束 日志前缀标识，目的是为了提高每次判断时的性能
     */
    public final static String REQUEST_END_PREFIX_FLAG = "1";
    /**
     * 接口请求 结束 日志前缀
     */
    private final static String REQUEST_END_PREFIX = "### request end[";

    /**
     * 是否为prod环境
     */
    private static final boolean IS_PROD_EVN = true;
    /**
     * 不打印接口请求日志的url集合
     */
    private static final Set<String> EXCLUDE_HTTP_LOG_URLS = Collections.emptySet();

    static {
//        final String activeProfile = SpringUtil.getActiveProfile();
//        final OptLogProperties optLogProperties = SpringUtil.getBean(OptLogProperties.class);
//        IS_PROD_EVN = "prod".equals(activeProfile);
//        EXCLUDE_HTTP_LOG_URLS = optLogProperties.getExcludeHttpLogUrls();
    }

    /**
     * 重写父类方法：封装打印消息的格式
     */
    @Override
    protected String createMessage(HttpServletRequest request, String prefix, String suffix) {
        // 是否为不打印的url
        if (isExcludeLogUrl((request.getRequestURI()))) {
            return null;
        }

        final StringBuilder messageInfo = getMessageInfo(request, prefix, suffix);
        // 请求开始还是结束
        if (REQUEST_START_PREFIX_FLAG.equals(prefix)) {
            // 请求开始
            MDC.put("logStartTime", String.valueOf(System.currentTimeMillis()));
        } else {
            // 请求结束，记录耗时
            final Long logStartTime = parseOrDefault(MDC.get("logStartTime"), 0L);
            messageInfo.append("\r\n接口耗时: ").append(System.currentTimeMillis() - logStartTime).append("ms");
        }
        return messageInfo.toString();
    }

    private Long parseOrDefault(@Nullable String longStr, Long defaultValue) {
        return NumberUtils.isParsable(longStr) ? NumberUtils.createLong(longStr) : defaultValue;
    }

    /**
     * 重写父类方法：请求前调用逻辑
     */
    @Override
    protected void beforeRequest(HttpServletRequest request, String message) {
        // 是否为不打印的url
        if (isExcludeLogUrl((request.getRequestURI()))) {
            return;
        }
        doPrintLog(message);
    }

    /**
     * 重写父类方法：请求后调用逻辑
     */
    @Override
    protected void afterRequest(HttpServletRequest request, String message) {
        // 是否为不打印的url
        if (isExcludeLogUrl((request.getRequestURI()))) {
            return;
        }
        doPrintLog(message);
    }

    /**
     * 重写父类方法：是否打印日志
     */
    @Override
    protected boolean shouldLog(HttpServletRequest request) {
        // 父类中的逻辑是：logger.isDebugEnabled()
        return true;
    }

    /**
     * 统一封装打印的日志格式
     *
     * @param request javax.servlet.http.HttpServletRequest
     * @param prefix  打印前缀
     * @param suffix  打印后缀
     * @return 封装好的日志格式
     */
    private StringBuilder getMessageInfo(HttpServletRequest request, String prefix, String suffix) {
        StringBuilder msg = new StringBuilder();

        // 判断是 请求开始 还是 请求结束
        if (REQUEST_START_PREFIX_FLAG.equals(prefix)) {
            msg.append(REQUEST_START_PREFIX);
        } else {
            msg.append(REQUEST_END_PREFIX);
        }

        msg.append(String.format("method=%s; ", request.getMethod().toLowerCase()));
        msg.append("uri=").append(request.getRequestURI());

        // 是否有传递 查询字符串 信息
        if (isIncludeQueryString()) {
            String queryString = request.getQueryString();
            if (queryString != null) {
                msg.append('?').append(queryString);
            }
        }

        // 是否有传递 payload 信息
        if (isIncludePayload()) {
            String payload = getMessagePayload(request);
            if (payload != null) {
                msg.append("; payload=").append(payload);
            }
        }

        // 是否包含 客户端 信息
        if (isIncludeClientInfo()) {
            String client = request.getRemoteAddr();
            if (StringUtils.isNotBlank(client)) {
                msg.append("; client=").append(client);
            }
            HttpSession session = request.getSession(false);
            if (session != null) {
                msg.append("; session=").append(session.getId());
            }
            String user = request.getRemoteUser();
            if (user != null) {
                msg.append("; user=").append(user);
            }
        }

        msg.append(suffix);
        return msg;
    }

    /**
     * 具体打印的方法
     *
     * @param message 打印的消息
     */
    private void doPrintLog(String message) {
        // 生产环境打印debug级别

            log.info(message);

        // log.info(message);
    }


    private Boolean isExcludeLogUrl(String url) {
        return false;
    }
}
