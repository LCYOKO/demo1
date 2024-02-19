package com.xiaomi.demo.dubbo.test1;

import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ProtocolConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.ServiceConfig;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

public class HelloServiceImpl implements HelloService {
    @Override
    public String sayHello(String msg) {
        return msg;
    }

    public static void main(String[] args) throws Exception {
        System.setProperty("dubbo.application.logger", "slf4j");
        System.setProperty("native", "true");
        startWithExport();
//        if (isClassic(args)) {
//            startWithExport();
//        } else {
//            startWithBootstrap();
//        }
    }

    private static boolean isClassic(String[] args) {
        return args.length > 0 && "classic".equalsIgnoreCase(args[0]);
    }

    private static void startWithBootstrap() {
        ServiceConfig<HelloServiceImpl> service = new ServiceConfig<>();
        service.setInterface(HelloService.class);
        service.setRef(new HelloServiceImpl());

        DubboBootstrap bootstrap = DubboBootstrap.getInstance();

        ApplicationConfig applicationConfig = new ApplicationConfig("dubbo-demo-api-provider");
        applicationConfig.setQosEnable(false);
        applicationConfig.setCompiler("jdk");
        Map<String, String> m = new HashMap<>(1);
        m.put("proxy", "jdk");
        applicationConfig.setParameters(m);
        bootstrap.application(applicationConfig)
                .registry(new RegistryConfig("zookeeper://192.168.255.128:2181?timeout=60000"))
                .protocol(new ProtocolConfig(CommonConstants.DUBBO, -1))
                .service(service)
                .start()
                .await();
    }

    private static void startWithExport() throws InterruptedException {
        ServiceConfig<HelloServiceImpl> service = new ServiceConfig<>();
        service.setInterface(HelloService.class);
        service.setRef(new HelloServiceImpl());
        ApplicationConfig applicationConfig = new ApplicationConfig("dubbo-demo-api-provider");
        applicationConfig.setQosEnable(false);
        applicationConfig.setCompiler("jdk");
        Map<String, String> m = new HashMap<>(1);
        m.put("proxy", "jdk");
        applicationConfig.setParameters(m);
        service.setApplication(applicationConfig);
        service.setRegistry(new RegistryConfig("zookeeper://192.168.255.128:2181?timeout=60000"));
        service.export();
        new CountDownLatch(1).await();
    }
}
