package com.mouse.framework.test;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

public class TestJsonObjectTest {
    @Test
    void should_be_able_to_parse_json_string() {
        String body = "{\"a\": \"a\", \"b\": 1, \"c\": true, \"d\": 1.2, \"e\": {\"a\": 1}, \"t\": \"2021-01-27T12:00:00.00Z\", \"l\": 10000000000}";
        TestJsonObject json = new TestJsonObject(body);

        assertThat(json.strVal("$.a")).isEqualTo("a");
        assertThat(json.intVal("$.b")).isEqualTo(1);
        assertThat(json.booleanVal("$.c")).isTrue();
        assertThat(json.doubleVal("$.d")).isEqualTo(1.2);
        assertThat(json.decimalVal("$.d")).isEqualByComparingTo(new BigDecimal("1.2"));
        assertThat(json.intVal("$.e.a")).isEqualTo(1);
        assertThat(json.has("$.a")).isTrue();
        assertThat(json.has("$.g")).isFalse();
        assertThat(json.<Integer>value("$.b")).isEqualTo(1);
        assertThat(json.instantVal("$.t")).isEqualTo(Instant.parse("2021-01-27T12:00:00.00Z"));
        assertThat(json.instantVal("$.g")).isNull();
        assertThat(json.raw()).isEqualTo(body);
        assertThat(json.longVal("$.l")).isEqualTo(10000000000L);
    }
}
