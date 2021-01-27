package com.mouse.framework.test;

import org.springframework.http.HttpHeaders;

public interface HeaderMocker {
    HttpHeaders getHeader();

    void clean();
}
