package com.mouse.framework.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

import java.net.URI;
import java.util.Collections;
import java.util.Map;

public class TestClient {
    private final TestRestTemplate testRestTemplate;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final HeaderMocker headerMocker;

    public TestClient(TestRestTemplate testRestTemplate, HeaderMocker headerMocker) {
        this.testRestTemplate = testRestTemplate;
        this.headerMocker = headerMocker;
        this.testRestTemplate.getRestTemplate().setRequestFactory(new HttpComponentsClientHttpRequestFactory());
    }

    public TestResponse get(String uriTemplate, Object... urlVariables) {
        return execute(uriTemplate, HttpMethod.GET, Collections.emptyMap(), urlVariables);
    }

    public TestResponse post(String uriTemplate, Map<String, Object> body, Object... urlVariables) {
        return execute(uriTemplate, HttpMethod.POST, body, urlVariables);
    }

    public TestResponse post(String uriTemplate, Object... urlVariables) {
        return post(uriTemplate, Collections.emptyMap(), urlVariables);
    }

    public TestResponse put(String uriTemplate, Map<String, Object> body, Object... urlVariables) {
        return execute(uriTemplate, HttpMethod.PUT, body, urlVariables);
    }

    public TestResponse put(String uriTemplate, Object... urlVariables) {
        return put(uriTemplate, Collections.emptyMap(), urlVariables);
    }

    public TestResponse patch(String uriTemplate, Map<String, Object> body, Object... urlVariables) {
        return execute(uriTemplate, HttpMethod.PATCH, body, urlVariables);
    }

    public TestResponse patch(String uriTemplate, Object... urlVariables) {
        return patch(uriTemplate, Collections.emptyMap(), urlVariables);
    }

    public TestResponse delete(String uriTemplate, Map<String, Object> body, Object... urlVariables) {
        return execute(uriTemplate, HttpMethod.DELETE, body, urlVariables);
    }

    public TestResponse delete(String uriTemplate, Object... urlVariables) {
        return delete(uriTemplate, Collections.emptyMap(), urlVariables);
    }

    public TestResponse execute(String uriTemplate, HttpMethod method, Map<String, Object> body, Object[] urlVariables) {
        HttpHeaders header = headerMocker.getHeader();
        headerMocker.clean();
        return exchange(uriTemplate, method, new HttpEntity<>(body, header), urlVariables);
    }

    /**
     * not use {@link HeaderMocker}.
     *
     * @param uriTemplate  uriTemplate {@link org.springframework.web.util.UriTemplateHandler}
     * @param method       {@link HttpMethod}
     * @param entity       {@link HttpEntity}
     * @param urlVariables argument of uriTemplate {@link org.springframework.web.util.UriTemplateHandler}
     * @return {@link TestResponse}
     */
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
