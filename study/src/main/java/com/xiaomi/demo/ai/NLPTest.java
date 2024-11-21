package com.xiaomi.demo.ai;

import com.aliyun.nlp_automl20191111.Client;
import com.aliyun.nlp_automl20191111.models.CreateAsyncPredictRequest;
import com.aliyun.nlp_automl20191111.models.CreateAsyncPredictResponse;
import com.aliyun.nlp_automl20191111.models.GetAsyncPredictRequest;
import com.aliyun.teaopenapi.models.Config;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

/**
 * @Author: liuchiyun
 * @Date: 2024/11/21
 */
@Slf4j
public class NLPTest {
    private Config config;

    private Client client;

    @Before
    public void before() {
        config = new Config();
        config.setEndpoint("nlp-automl.cn-hangzhou.aliyuncs.com");
        try {
            client = new Client(config);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Test
    public void test1() throws Exception {
        Long asyncPredict = createAsyncPredict();
        Thread.sleep(5000);
        log.info("result:{}", getAsyncPredictResult(asyncPredict));
    }


    public Long createAsyncPredict() throws Exception {
        // Parameter settings for API request
        CreateAsyncPredictRequest createAsyncPredictRequest = new CreateAsyncPredictRequest();
        createAsyncPredictRequest.setServiceName("ContractNERPretrain");
        createAsyncPredictRequest.setFileUrl("https://miniossl.yhdja.com/sv-dev-storage/dev/qualitySafetyCheck/2024-11-21/5b38f4fce8e4497bab81552a46d61957.pdf?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=X0VQbE3X67VWy4H5LMOc%2F20241121%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20241121T035859Z&X-Amz-Expires=300&X-Amz-SignedHeaders=host&X-Amz-Signature=36da95eb4c675389c334a656215a5135b38bc97f88865a80c4163a3e80b709e6");
        createAsyncPredictRequest.setFileType("pdf");
        CreateAsyncPredictResponse response = client.createAsyncPredict(createAsyncPredictRequest);

        return response.getBody().getAsyncPredictId();
    }

    public com.aliyun.nlp_automl20191111.models.GetAsyncPredictResponse getAsyncPredictResult(Long asyncPredictId) throws Exception {
        // Parameter settings for API request
        GetAsyncPredictRequest getAsyncPredictRequest = new GetAsyncPredictRequest();
        getAsyncPredictRequest.setAsyncPredictId(asyncPredictId.intValue());
        return client.getAsyncPredict(getAsyncPredictRequest);
    }
}
