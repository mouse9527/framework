package com.mouse.framework.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.Collections;
import java.util.Map;

@Component
public class TestClient {
    private final TestRestTemplate testRestTemplate;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public TestClient(TestRestTemplate testRestTemplate) {
        this.testRestTemplate = testRestTemplate;
        this.testRestTemplate.getRestTemplate().setRequestFactory(new HttpComponentsClientHttpRequestFactory());
    }

    public TestResponse get(String uriTemplate, Object... urlVariables) {
        return exchange(uriTemplate, HttpMethod.GET, new HttpEntity<>(Collections.emptyMap()), urlVariables);
    }

    public TestResponse post(String uriTemplate, Map<String, Object> body, Object... urlVariables) {
        return exchange(uriTemplate, HttpMethod.POST, new HttpEntity<>((Object) body), urlVariables);
    }

    public TestResponse post(String uriTemplate, Object... urlVariables) {
        return post(uriTemplate, Collections.emptyMap(), urlVariables);
    }

    public TestResponse put(String uriTemplate, Map<String, Object> body, Object... urlVariables) {
        return exchange(uriTemplate, HttpMethod.PUT, new HttpEntity<>((Object) body), urlVariables);
    }

    public TestResponse put(String uriTemplate, Object... urlVariables) {
        return put(uriTemplate, Collections.emptyMap(), urlVariables);
    }

    public TestResponse patch(String uriTemplate, Map<String, Object> body, Object... urlVariables) {
        return exchange(uriTemplate, HttpMethod.PATCH, new HttpEntity<>((Object) body), urlVariables);
    }

    public TestResponse patch(String uriTemplate, Object... urlVariables) {
        return patch(uriTemplate, Collections.emptyMap(), urlVariables);
    }

    public TestResponse delete(String uriTemplate, Map<String, Object> body, Object... urlVariables) {
        return exchange(uriTemplate, HttpMethod.DELETE, new HttpEntity<>((Object) body), urlVariables);
    }

    public TestResponse delete(String uriTemplate, Object... urlVariables) {
        return delete(uriTemplate, Collections.emptyMap(), urlVariables);
    }

    public TestResponse exchange(String uriTemplate, HttpMethod method, HttpEntity<?> entity, Object... urlVariables) {
        URI url = testRestTemplate.getRestTemplate().getUriTemplateHandler().expand(uriTemplate, urlVariables);
        RequestEntity<?> request = new RequestEntity<>(entity.getBody(), entity.getHeaders(), method, url);
        ResponseEntity<String> response = testRestTemplate.exchange(request, String.class);
        log(request, response);
        return new TestResponse(response);
    }

    private void log(RequestEntity<?> request, ResponseEntity<String> response) {
        logger.info("request: \n{}", request);
        logger.info("response: \n{}", response);
    }

}
