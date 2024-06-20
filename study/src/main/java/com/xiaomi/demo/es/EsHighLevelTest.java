package com.xiaomi.demo.es;


import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 * @Author: liuchiyun
 * @Date: 2024/6/20
 */
@Slf4j
public class EsHighLevelTest {
    private RestHighLevelClient client;

    @Before
    public void before() {
        RestClientBuilder httpClientBuilder = RestClient.builder(new HttpHost("localhost", 9200));
        client = new RestHighLevelClient(httpClientBuilder);
    }

    @Test
    public void testGetIndices() throws IOException {
        GetIndexResponse response = client.indices().get(new GetIndexRequest("movies"), RequestOptions.DEFAULT);
        log.info("resp:{}", response.getMappings());
    }

    @Test
    public void getMovies() throws IOException {
        GetResponse response = client.get(new GetRequest("movies", "1"), RequestOptions.DEFAULT);
        log.info("resp:{}", response);
    }
}
