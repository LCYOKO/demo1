package com.xiaomi.demo.db.es;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.admin.cluster.storedscripts.GetStoredScriptRequest;
import org.elasticsearch.action.admin.cluster.storedscripts.GetStoredScriptResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Map;

/**
 * @Author: liuchiyun
 * @Date: 2024/6/20
 */
@Slf4j
public class EsHighLevelTest {
    private RestHighLevelClient client;
    private ObjectMapper objectMapper;

    @Before
    public void before() {
        RestClientBuilder httpClientBuilder = RestClient.builder(new HttpHost("localhost", 9200));
        client = new RestHighLevelClient(httpClientBuilder);
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testGetIndices() throws IOException {
        GetIndexResponse response = client.indices().get(new GetIndexRequest("users"), RequestOptions.DEFAULT);
        log.info("resp:{}", response.getMappings().toString());
    }

    @Test
    public void testGetDoc() throws IOException {
        GetResponse response = client.get(new GetRequest("users", "1"), RequestOptions.DEFAULT);
        log.info("resp:{}", response.getSource());
    }

    @Test
    public void testCreateDoc() throws IOException {
        Map<String, Object> map = ImmutableMap.of("name", "lisi", "age", 16, "email", "123@qq.com");
        IndexRequest request = new IndexRequest();
        request.id("1");
        request.index("users").opType(DocWriteRequest.OpType.CREATE).source(map);
        IndexResponse indexResponse = client.index(request, RequestOptions.DEFAULT);
        log.info("response:{}", indexResponse.getResult());
    }

    @Test
    public void testReIndex() throws IOException {
        //ReIndex 会删除文档并新建一个，version+1
        Map<String, Object> map = ImmutableMap.of("name", "lisi", "age", 16, "email", "123@qq.com");
        IndexRequest request = new IndexRequest();
        request.id("1");
        request.index("users").opType(DocWriteRequest.OpType.INDEX).source(map);
        IndexResponse indexResponse = client.index(request, RequestOptions.DEFAULT);
        log.info("response:{}", indexResponse.getResult());
    }

    @Test
    public void testUpdate() throws IOException {
        Map<String, Object> map = ImmutableMap.of("weight", 100);
        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.id("1");
        updateRequest.index("users").doc(map);
        UpdateResponse response = client.update(updateRequest, RequestOptions.DEFAULT);
        log.info("response:{}", response.getResult());
    }

    @Test
    public void testDelete() throws IOException {
        DeleteRequest deleteRequest = new DeleteRequest();
        deleteRequest.index("users").id("2");
        DeleteResponse response = client.delete(deleteRequest, RequestOptions.DEFAULT);
        log.info("response:{}", response.getResult());
    }

    @Test
    public void testAnalyze() {
        //Simple Analyzer – 按照非字母切分（符号被过滤），小写处理
        //Stop Analyzer – 小写处理，停用词过滤（the，a，is）
        //Whitespace Analyzer – 按照空格切分，不转小写
        //Keyword Analyzer – 不分词，直接将输入当作输出
        //Patter Analyzer – 正则表达式，默认 \W+ (非字符分隔)
        //ICU 中文分词器
    }

    @Test
    public void testSearch() {

    }
}
