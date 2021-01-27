package com.mouse.framework.test;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

// TODO remove it
public class TestResponse {
    private final ResponseEntity<String> responseEntity;
    private final TestJsonObject body;

    public TestResponse(ResponseEntity<String> responseEntity) {
        this.responseEntity = responseEntity;
        this.body = new TestJsonObject(responseEntity.getBody());
    }

    public HttpStatus getStatusCode() {
        return responseEntity.getStatusCode();
    }

    public TestJsonObject getBody() {
        return body;
    }

    public ResponseEntity<String> getRaw() {
        return responseEntity;
    }
}
