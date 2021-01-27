package com.mouse.framework.test;

import org.springframework.http.HttpHeaders;

import java.util.Collections;
import java.util.Locale;
import java.util.Optional;

public class ThreadSafeHeaderMockerGiven implements HeaderMocker, HeaderGiven {
    private final ThreadLocal<HttpHeaders> headers;

    public ThreadSafeHeaderMockerGiven() {
        this.headers = new ThreadLocal<>();
    }

    @Override
    public HttpHeaders getHeader() {
        return Optional.ofNullable(headers.get()).orElseGet(HttpHeaders::new);
    }

    @Override
    public void clean() {
        this.headers.remove();
    }

    @Override
    public void mockLanguage(Locale locale) {
        HttpHeaders headers = getOrDefault();
        headers.setAcceptLanguageAsLocales(Collections.singletonList(locale));
    }

    private HttpHeaders getOrDefault() {
        HttpHeaders httpHeaders = headers.get();
        if (httpHeaders == null) {
            httpHeaders = new HttpHeaders();
            headers.set(httpHeaders);
        }
        return httpHeaders;
    }

    @Override
    public void mockToken(String token) {
        getOrDefault().setBearerAuth(token);
    }

    @Override
    public void mock(String key, String value) {
        getOrDefault().set(key, value);
    }
}
