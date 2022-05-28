package com.xiaomi.demo.base;

import com.netflix.appinfo.ApplicationInfoManager;
import com.netflix.appinfo.EurekaInstanceConfig;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.appinfo.MyDataCenterInstanceConfig;
import com.netflix.appinfo.providers.EurekaConfigBasedInstanceInfoProvider;
import com.netflix.config.DynamicPropertyFactory;
import com.netflix.discovery.DefaultEurekaClientConfig;
import com.netflix.discovery.DiscoveryClient;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.EurekaClientConfig;
import com.netflix.eureka.EurekaBootStrap;
import io.netty.channel.epoll.EpollSocketChannel;
import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayDeque;
import java.util.Date;
import java.util.Deque;
import java.util.logging.SimpleFormatter;

/**
 * @Author：liuchiyun
 * @Date: 2021/4/30
 */
public class TestDate {
  public static ApplicationInfoManager applicationInfoManager;
  public static EurekaClient eurekaClient;
    private static synchronized ApplicationInfoManager initializeApplicationInfoManager(EurekaInstanceConfig instanceConfig) {
        if (applicationInfoManager == null) {
            InstanceInfo instanceInfo = new EurekaConfigBasedInstanceInfoProvider(instanceConfig).get();
            applicationInfoManager = new ApplicationInfoManager(instanceConfig, instanceInfo);
        }

        return applicationInfoManager;
    }

    private static synchronized EurekaClient initializeEurekaClient(ApplicationInfoManager applicationInfoManager, EurekaClientConfig clientConfig) {
        if (eurekaClient == null) {
            eurekaClient = new DiscoveryClient(applicationInfoManager, clientConfig);
        }

        return eurekaClient;
    }

    @Test
    public  void test(){
        DynamicPropertyFactory configInstance = com.netflix.config.DynamicPropertyFactory.getInstance();
        ApplicationInfoManager applicationInfoManager = initializeApplicationInfoManager(new MyDataCenterInstanceConfig());
        EurekaClient eurekaClient = initializeEurekaClient(applicationInfoManager, new DefaultEurekaClientConfig());
        System.out.println(eurekaClient.getApplications().getRegisteredApplications());
    }
}
