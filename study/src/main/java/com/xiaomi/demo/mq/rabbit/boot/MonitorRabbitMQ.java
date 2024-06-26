package com.xiaomi.demo.mq.rabbit.boot;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;
import org.apache.http.HttpEntity;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author liuchiyun
 */
public class MonitorRabbitMQ {


    private static final String RABBIT_NODES_STATUS_REST_URL = "http://192.168.13.111:15672/api/nodes";

    private static final String RABBIT_USERS_REST_URL = "http://192.168.13.111:15672/api/users";

    private static final String RABBIT_QUEUE_STATUS_URL = "http://192.168.13.111:15672/api/queues";

    private static final String RABBIT_USER_NAME = "guest";
    private static final String RABBIT_USER_PWD = "guest";

    public static Map<String, ClusterStatus> fetchRabbitMQClusterStatus(String url, String username, String password) throws IOException {
        Map<String, ClusterStatus> clusterStatusMap = new HashMap<String, ClusterStatus>();
        String nodeData = getData(url, username, password);
        JsonNode jsonNode = null;
        for (JsonNode next : jsonNode) {
            ClusterStatus status = new ClusterStatus();
            status.setDiskFree(next.get("disk_free").asLong());
            status.setFdUsed(next.get("fd_used").asLong());
            status.setMemoryUsed(next.get("mem_used").asLong());
            status.setProcUsed(next.get("proc_used").asLong());
            status.setSocketUsed(next.get("sockets_used").asLong());
            clusterStatusMap.put(next.get("name").asText(), status);
        }
        return clusterStatusMap;
    }

    public static String getData(String url, String username, String password) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        UsernamePasswordCredentials creds = new UsernamePasswordCredentials(username, password);
        HttpGet httpGet = new HttpGet(url);
        httpGet.addHeader(BasicScheme.authenticate(creds, "UTF-8", false));
        httpGet.setHeader("Content-Type", "application/json");
        try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
            if (response.getStatusLine().getStatusCode() != 200) {
                System.out.println("call http api to get rabbitmq data return code: " + response.getStatusLine().getStatusCode() + ", url: " + url);
            }
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                return EntityUtils.toString(entity);
            }
        }
        return null;
    }

    @Data
    public static class User {
        private String name;
        private String tags;

        @Override
        public String toString() {
            return "User{" +
                    "name=" + name +
                    ", tags=" + tags +
                    '}';
        }
    }

    @Data
    public static class ClusterStatus {
        private long diskFree;
        private long diskLimit;
        private long fdUsed;
        private long fdTotal;
        private long socketUsed;
        private long socketTotal;
        private long memoryUsed;
        private long memoryLimit;
        private long procUsed;
        private long procTotal;

        @Override
        public String toString() {
            return "ClusterStatus{" +
                    "diskFree=" + diskFree +
                    ", diskLimit=" + diskLimit +
                    ", fdUsed=" + fdUsed +
                    ", fdTotal=" + fdTotal +
                    ", socketUsed=" + socketUsed +
                    ", socketTotal=" + socketTotal +
                    ", memoryUsed=" + memoryUsed +
                    ", memoryLimit=" + memoryLimit +
                    ", procUsed=" + procUsed +
                    ", procTotal=" + procTotal +
                    '}';
        }

    }
}
