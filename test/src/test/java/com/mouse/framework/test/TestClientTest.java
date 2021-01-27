package com.mouse.framework.test;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@EnableTestClient(ThreadSafeHeaderMockerGiven.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import({TestClientTest.TestController.class})
public class TestClientTest {
    @Resource
    private TestClient testClient;
    @Resource
    private HeaderGiven headerGiven;

    @Test
    void should_be_able_to_get_rest_response() {
        TestResponse response = testClient.get("/test?a={a}&b={b}", "a", "b");

        assertParam(response);
    }

    private void assertParam(TestResponse response) {
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getRaw()).isNotNull();
        TestJsonObject body = response.getBody();
        assertThat(body.strVal("$.param.a")).isEqualTo("a");
        assertThat(body.strVal("$.param.b")).isEqualTo("b");
    }

    @Test
    void should_be_able_to_post_rest_response() {
        Map<String, Object> body = new HashMap<>();
        body.put("a", "a1");

        TestResponse response = testClient.post("/test?a={a}&b={b}", body, "a", "b");

        assertParamAndBody(body, response);
    }

    @Test
    void should_be_able_to_post_rest_response_with_out_body() {
        TestResponse response = testClient.post("/test?a={a}&b={b}", "a", "b");

        assertParam(response);
    }

    private void assertParamAndBody(Map<String, Object> body, TestResponse response) {
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getRaw()).isNotNull();
        TestJsonObject responseBody = response.getBody();
        assertThat(responseBody.<Map<String, Object>>value("$.body")).isEqualTo(body);
        assertThat(responseBody.strVal("$.param.a")).isEqualTo("a");
        assertThat(responseBody.strVal("$.param.b")).isEqualTo("b");
    }

    @Test
    void should_be_able_to_put_rest_response() {
        Map<String, Object> body = new HashMap<>();
        body.put("a", "a1");

        TestResponse response = testClient.put("/test?a={a}&b={b}", body, "a", "b");

        assertParamAndBody(body, response);
    }

    @Test
    void should_be_able_to_put_rest_response_with_out_body() {
        TestResponse response = testClient.put("/test?a={a}&b={b}", "a", "b");

        assertParam(response);
    }

    @Test
    void should_be_able_to_patch_rest_response() {
        Map<String, Object> body = new HashMap<>();
        body.put("a", "a1");

        TestResponse response = testClient.patch("/test?a={a}&b={b}", body, "a", "b");

        assertParamAndBody(body, response);
    }

    @Test
    void should_be_able_to_patch_rest_response_with_out_body() {
        TestResponse response = testClient.patch("/test?a={a}&b={b}", "a", "b");

        assertParam(response);
    }

    @Test
    void should_be_able_to_delete_rest_response() {
        Map<String, Object> body = new HashMap<>();
        body.put("a", "a1");

        TestResponse response = testClient.delete("/test?a={a}&b={b}", body, "a", "b");

        assertParamAndBody(body, response);
    }

    @Test
    void should_be_able_to_delete_rest_response_with_out_body() {
        TestResponse response = testClient.delete("/test?a={a}&b={b}", "a", "b");

        assertParam(response);
    }

    @Test
    void should_be_able_to_exchange_rest_response() {
        HttpEntity<?> entity = new HttpEntity<>(Collections.emptyMap());

        TestResponse response = testClient.exchange("/test?a={a}&b={b}", HttpMethod.GET, entity, "a", "b");

        assertParam(response);
    }

    @Test
    void should_be_able_to_parse_header_with_test_client() {
        headerGiven.mockLanguage(Locale.SIMPLIFIED_CHINESE);
        headerGiven.mockToken("mock-token");

        TestResponse response = testClient.get("/header");

        assertThat(response.getBody().strVal("$.accept-language")).isEqualTo("zh-cn");
        assertThat(response.getBody().strVal("$.authorization")).isEqualTo("Bearer mock-token");
        TestResponse second = testClient.get("/header");
        assertThat(second.getBody().has("$.accept-language")).isFalse();
        assertThat(second.getBody().has("$.authorization")).isFalse();
    }

    @RestController
    static class TestController {
        @RequestMapping("/test")
        public Object test(@RequestParam(required = false) Map<String, Object> param,
                           @RequestBody(required = false) Map<String, Object> body) {
            Map<String, Object> result = new HashMap<>();
            result.put("param", param);
            result.put("body", body);
            return result;
        }

        @RequestMapping("/header")
        public Object header(@RequestHeader Map<String, Object> header) {
            return header;
        }
    }
}
