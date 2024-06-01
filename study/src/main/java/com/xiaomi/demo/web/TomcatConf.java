package com.xiaomi.demo.web;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.Http11Nio2Protocol;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;

//@Configuration
public class TomcatConf {

    @Bean
    public TomcatServletWebServerFactory tomcatServletWebServerFactory() {
        TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
        factory.addAdditionalTomcatConnectors(http11Nio2());
        return factory;
    }

    private Connector http11Nio2() {
        Connector connector = new Connector("c");
        Http11Nio2Protocol protocolHandler = (Http11Nio2Protocol) connector.getProtocolHandler();
        protocolHandler.setMaxConnections(10000);
        // 经验 1C2G 200     4C8G 800
        protocolHandler.setMaxThreads(800);
        protocolHandler.setAcceptCount(1000);
        protocolHandler.setMinSpareThreads(100);
        protocolHandler.setKeepAliveTimeout(30000);
        protocolHandler.setMaxKeepAliveRequests(10000);
        connector.setPort(9999);
        connector.setScheme("http");
        return connector;
    }
}
