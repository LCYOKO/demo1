package com.xiaomi.demo.rpc.dubbo;

import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ProtocolConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;

import java.util.HashMap;
import java.util.Map;

public class Consumer {
    public static void main(String[] args) {
        System.setProperty("dubbo.application.logger", "sl4j");
        System.setProperty("native", "true");
        runWithRefer();
    }

    private static boolean isClassic(String[] args) {
        return args.length > 0 && "classic".equalsIgnoreCase(args[0]);
    }

    private static void runWithBootstrap() {
        ReferenceConfig<HelloService> reference = new ReferenceConfig<>();
        reference.setInterface(HelloService.class);
        reference.setGeneric("false");

        DubboBootstrap bootstrap = DubboBootstrap.getInstance();
        ApplicationConfig applicationConfig = new ApplicationConfig("dubbo-demo-api-consumer");
        applicationConfig.setQosEnable(false);
        applicationConfig.setCompiler("jdk");
        Map<String, String> m = new HashMap<>(1);
        m.put("proxy", "jdk");
        applicationConfig.setParameters(m);

        bootstrap.application(applicationConfig)
                .registry(new RegistryConfig("zookeeper://192.168.255.128:2181?timeout=60000"))
                .protocol(new ProtocolConfig(CommonConstants.DUBBO, -1))
                .reference(reference)
                .start();

        HelloService demoService = bootstrap.getCache().get(reference);
        String message = demoService.sayHello("123124");
        System.out.println(message);
    }

    private static void runWithRefer() {
        ReferenceConfig<HelloService> reference = new ReferenceConfig<>();
        reference.setApplication(new ApplicationConfig("dubbo-demo-api-consumer"));
        reference.setRegistry(new RegistryConfig("zookeeper://192.168.255.128:2181?timeout=60000"));
        reference.setInterface(HelloService.class);
        HelloService service = reference.get();
        String message = service.sayHello("dubbo");
        System.out.println("return value: " + message);
    }
}
