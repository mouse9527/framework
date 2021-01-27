package com.mouse.framework.test;

import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Locale;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@EnableTestClient(ThreadSafeHeaderMockerGiven.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import({TestClientTest.TestController.class})
public class MultithreadingMockTest {
    @Resource
    private TestClient testClient;
    @Resource
    private HeaderGiven headerGiven;

    private static Stream<Pair<Locale, String>> mockData() {
        return Stream.of(Pair.of(Locale.SIMPLIFIED_CHINESE, "1"),
                Pair.of(Locale.ENGLISH, "2"),
                Pair.of(Locale.JAPANESE, "3"),
                Pair.of(Locale.TRADITIONAL_CHINESE, "4"));
    }

    @ParameterizedTest
    @MethodSource("mockData")
    @Execution(ExecutionMode.CONCURRENT)
    void should_be_able_to_mock_in_multithreading(Pair<Locale, String> mockData) {
        headerGiven.setLanguage(mockData.getFirst());
        headerGiven.setToken(mockData.getSecond());
        headerGiven.set("x", mockData.getSecond());

        ResponseEntity<TestJsonObject> response = testClient.get("/header");

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAcceptLanguageAsLocales(Collections.singletonList(mockData.getFirst()));
        String acceptLanguage = httpHeaders.getFirst(HttpHeaders.ACCEPT_LANGUAGE);
        assertThat(response.getBody().strVal("$.accept-language")).isEqualTo(acceptLanguage);
        assertThat(response.getBody().strVal("$.authorization")).isEqualTo(String.format("Bearer %s", mockData.getSecond()));
        assertThat(response.getBody().strVal("$.x")).isEqualTo(mockData.getSecond());
    }
}
