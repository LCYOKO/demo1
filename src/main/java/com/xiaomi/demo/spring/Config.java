package com.xiaomi.demo.spring;

import com.xiaomi.demo.spring.mvc.LoggingRequestAndResponseFilter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

/**
 * @Author: liuchiyun
 * @Date: 2023/12/9
 */
@Configuration
public class Config {
//    @Bean
    public FilterRegistrationBean<CommonsRequestLoggingFilter> logFilterRegistration() {
        CommonsRequestLoggingFilter filter = new LoggingRequestAndResponseFilter();
        // 是否打印header中的内容，参数很多
        filter.setIncludeHeaders(true);
        // 是否打印查询字符串内容
        filter.setIncludeQueryString(true);
        // 是否打印 payLoad内容，内部使用的是ContentCachingRequestWrapper读取的request.getInputStream()，必须要先调用@RequstBody，才能取到值。
        filter.setIncludePayload(true);
        // 是否打印客户端信息（ip、session、remoteUser）
        filter.setIncludeClientInfo(true);
        // 1024字节（1kb），超出部分截取
        // 在UTF-8编码方案中，一个英文字符占用一个字节，一个汉字字符占用三个字节的空间。
        filter.setMaxPayloadLength(1024);
        // 设置 before request 日志前缀，默认为：Before request [
        filter.setBeforeMessagePrefix(LoggingRequestAndResponseFilter.REQUEST_START_PREFIX_FLAG);
        // 设置 before request 日志后缀，默认为：]
        filter.setBeforeMessageSuffix("]");
        // 设置 before request 日志前缀，默认为：After request [
        filter.setAfterMessagePrefix(LoggingRequestAndResponseFilter.REQUEST_END_PREFIX_FLAG);
        // 设置 after request 日志后缀，默认为：]
        filter.setAfterMessageSuffix("]");

        FilterRegistrationBean<CommonsRequestLoggingFilter> registration = new FilterRegistrationBean<>(filter);
        registration.addUrlPatterns("/*");
        registration.setOrder(0);
        registration.setName("commonsRequestLoggingFilter");
        return registration;
    }
}
