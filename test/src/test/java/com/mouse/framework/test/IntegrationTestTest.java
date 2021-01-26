package com.mouse.framework.test;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@EnableTestClient
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(IntegrationTestTest.TestController.class)
public class IntegrationTestTest {
    @Resource
    private TestClient testClient;

    @Test
    void should_be_able_get_rest_response() {
        TestResponse response = testClient.get("/test?a={a}&b={b}", "a", "b");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getRaw()).isNotNull();
        TestJsonObject body = response.getBody();
        assertThat(body.strVal("$.param.a")).isEqualTo("a");
        assertThat(body.strVal("$.param.b")).isEqualTo("b");
    }

    @RestController
    static class TestController {
        @RequestMapping("/test")
        public Object get(@RequestParam(required = false) Map<String, Object> param,
                          @RequestBody(required = false) Map<String, Object> body) {
            Map<String, Object> result = new HashMap<>();
            result.put("param", param);
            result.put("body", body);
            return result;
        }
    }
}
