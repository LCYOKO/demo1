package com.xiaomi.demo.db.es;


import com.google.common.collect.ImmutableMap;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.*;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Before;
import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: liuchiyun
 * @Date: 2024/6/20
 */
@Slf4j
public class EsHighLevelTest {
    private RestHighLevelClient client;
    private final String MOVIE_INDEX = "movies";

    @Before
    public void before() {
        RestClientBuilder httpClientBuilder = RestClient.builder(new HttpHost("localhost", 9200));
        client = new RestHighLevelClient(httpClientBuilder);
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
        //IK 中文分词器
    }

    @Test
    public void testMget() throws IOException {
        MultiGetRequest request = new MultiGetRequest();
        request.preference("true").add("users", "1").add("users", "2");
        MultiGetResponse response = client.mget(request, RequestOptions.DEFAULT);
        log.info("response:{}", Arrays.stream(response.getResponses()).map(MultiGetItemResponse::getResponse).collect(Collectors.toList()));
    }

    @SneakyThrows
    @Test
    public void testSearchQueryAll() {
        SearchRequest request = buildSearchRequest(MOVIE_INDEX);
        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.query(QueryBuilders.matchAllQuery());
        builder.size(10);
        builder.from(10);
        builder.sort("movieId", SortOrder.DESC);
        request.source(builder);
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        log.info("response:{}", convert(response));
    }

    @Test
    @Transactional()
    public void testSearchQuery() throws IOException {
        SearchRequest request = buildSearchRequest(MOVIE_INDEX);
        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.query(QueryBuilders.matchQuery("title", "Beautiful Mind"));
        builder.size(10);
        builder.from(10);
        builder.fetchSource("title", null);
        request.source(builder);
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        log.info("response:{}", convert(response));
    }

    @Test
    public void testSearchQueryOp() throws IOException {
        SearchRequest request = buildSearchRequest(MOVIE_INDEX);
        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.query(QueryBuilders.matchQuery("title", "Beautiful Mind").operator(Operator.AND));
        builder.fetchSource("title", null);
        request.source(builder);
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        log.info("response:{}", convert(response));
    }

    @Test
    public void testSearchQueryPhrase() throws IOException {
        SearchRequest request = buildSearchRequest(MOVIE_INDEX);
        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.query(QueryBuilders.matchPhraseQuery("title", "Beautiful Mind"));
        builder.fetchSource("title", null);
        request.source(builder);
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        log.info("response:{}", convert(response));
    }

    @Test
    public void testSearchQueryMulti() throws IOException {
        SearchRequest request = buildSearchRequest(MOVIE_INDEX);
        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.query(QueryBuilders.multiMatchQuery("Beautiful Mind", "title", "genres"));
        builder.fetchSource("title", null);
        request.source(builder);
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        log.info("response:{}", convert(response));
    }

    @Test
    public void testSearchPrefix() throws IOException {
        SearchRequest request = buildSearchRequest(MOVIE_INDEX);
        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.query(QueryBuilders.matchQuery("title", "Beautiful"));
        builder.fetchSource("title", null);
        request.source(builder);
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        log.info("response:{}", convert(response));
    }


    @Test
    public void testTermQuery() throws IOException {
        SearchRequest request = buildSearchRequest(MOVIE_INDEX);
        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.query(QueryBuilders.termQuery("title", "Beautiful"));
        builder.fetchSource("title", null);
        request.source(builder);
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        log.info("response:{}", convert(response));
    }

    @Test
    public void testCreateMapping() {
        //创建mapping
//#修改为strict
//        PUT dynamic_mapping_test/_mapping
//        {
//            "dynamic": "strict"
        //true时能写能索引， false时能写不能索引, strict时不能写
//        }
//        PUT users
//        {
//            "mappings" : {
//            "properties" : {
//                "firstName" : {
//                    "type" : "text",
        //            "analyzer": "english",
        //            "search_analyzer":"english"
//                },
//                "lastName" : {
//                    "type" : "text"
//                },
//                "mobile" : {
//                    "type" : "keyword",
//                            "null_value": "NULL"
//                }
//
//            }
//        }
//        }
    }

    private List<String> convert(SearchResponse response) {
        return Arrays.stream(response.getHits().getHits()).map(SearchHit::getSourceAsString).collect(Collectors.toList());
    }

    public SearchRequest buildSearchRequest(String index) {
        return new SearchRequest().indices(index);
    }

}
