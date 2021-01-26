package com.mouse.framework.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class TestClient {
    private final TestRestTemplate testRestTemplate;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public TestClient(TestRestTemplate testRestTemplate) {
        this.testRestTemplate = testRestTemplate;
    }

    public TestResponse get(String uriTemplate, Object... urlVariables) {
        return new TestResponse(execute(uriTemplate, HttpMethod.GET, Collections.emptyMap(), urlVariables));
    }

    public TestResponse post(String uriTemplate, Object body, Object... urlVariables) {
        return new TestResponse(execute(uriTemplate, HttpMethod.POST, body, urlVariables));
    }

    private ResponseEntity<String> execute(String uriTemplate, HttpMethod method, Object body, Object[] urlVariables) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Object> request = new HttpEntity<>(body, headers);
        ResponseEntity<String> responseEntity = testRestTemplate.exchange(uriTemplate, method, request, String.class, urlVariables);
        log(request, responseEntity);
        return responseEntity;
    }

    private void log(HttpEntity<Object> request, ResponseEntity<String> responseEntity) {
        logger.info("request: \n{}", request);
        logger.info("response: \n{}", responseEntity);
    }
}
