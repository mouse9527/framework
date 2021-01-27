package com.mouse.framework.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.Collections;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

@Component
public class TestClient {
    private final TestRestTemplate testRestTemplate;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final ThreadLocal<Locale> locale;
    private final ThreadLocal<String> token;

    public TestClient(TestRestTemplate testRestTemplate) {
        this.testRestTemplate = testRestTemplate;
        this.testRestTemplate.getRestTemplate().setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        this.locale = new ThreadLocal<>();
        this.token = new ThreadLocal<>();
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
        HttpHeaders headers = new HttpHeaders();
        Optional.ofNullable(locale.get())
                .map(Collections::singletonList)
                .ifPresent(headers::setAcceptLanguageAsLocales);
        Optional.ofNullable(token.get())
                .ifPresent(headers::setBearerAuth);
        this.locale.remove();
        this.token.remove();
        return exchange(uriTemplate, method, new HttpEntity<>(body, headers), urlVariables);
    }

    /**
     * not use {@link TestClient#mockLanguage(Locale)} and {@link TestClient#mockToken(String)}.
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

    public void mockLanguage(Locale locale) {
        this.locale.set(locale);
    }

    public void mockToken(String token) {
        this.token.set(token);
    }
}
