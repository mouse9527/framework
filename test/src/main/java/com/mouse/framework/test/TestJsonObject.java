package com.mouse.framework.test;

import com.jayway.jsonpath.JsonPath;

import java.util.Objects;

public class TestJsonObject {
    private final String json;

    public TestJsonObject(String json) {
        this.json = json;
    }

    public String strVal(String jsonPath) {
        return JsonPath.compile(jsonPath).read(json);
    }

    public Integer intVal(String jsonPath) {
        return JsonPath.compile(jsonPath).read(json);
    }

    public Boolean booleanVal(String jsonPath) {
        return JsonPath.compile(jsonPath).read(json);
    }

    public Double doubleVal(String jsonPath) {
        return JsonPath.compile(jsonPath).read(json);
    }

    public Boolean has(String jsonPath) {
        try {
            Object object = JsonPath.compile(jsonPath).read(json);
            return Objects.nonNull(object);
        } catch (Exception e) {
            return false;
        }
    }

    public <T> T value(String jsonPath) {
        return JsonPath.compile(jsonPath).read(json);
    }
}