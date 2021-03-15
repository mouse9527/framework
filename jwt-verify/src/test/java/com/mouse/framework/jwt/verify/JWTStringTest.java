package com.mouse.framework.jwt.verify;

import com.mouse.framework.security.IllegalTokenException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class JWTStringTest {

    private static Stream<String> illegalTexts() {
        return Stream.of(null, "", "x", "x.x", "x.x.x.x", "x.x.x", "1.1.1");
    }

    @Test
    void should_be_able_to_create_from_text() {
        Base64.Encoder encoder = Base64.getEncoder();
        String header = encoder.encodeToString("header".getBytes(StandardCharsets.UTF_8));
        String payload = encoder.encodeToString("payload".getBytes(StandardCharsets.UTF_8));
        String signature = encoder.encodeToString("signature".getBytes(StandardCharsets.UTF_8));

        JWTString jwt = new JWTString(String.format("%s.%s.%s", header, payload, signature));

        assertThat(jwt.getHeader()).isEqualTo(header);
        assertThat(jwt.getPayload()).isEqualTo(payload);
        assertThat(jwt.getSignature()).isEqualTo(signature);
        assertThat(jwt.getSignatureBytes()).isEqualTo("signature".getBytes(StandardCharsets.UTF_8));
        assertThat(jwt.getSignatureContext()).isEqualTo(String.format("%s.%s", header, payload));
        assertThat(jwt.getSignatureContextBytes()).isEqualTo(String.format("%s.%s", header, payload).getBytes(StandardCharsets.UTF_8));
    }

    @ParameterizedTest
    @MethodSource("illegalTexts")
    void should_be_able_to_raise_exception_with_illegal_text(String illegalText) {
        Throwable throwable = catchThrowable(() -> new JWTString(illegalText));

        assertThat(throwable).isNotNull();
        assertThat(throwable).isInstanceOf(IllegalTokenException.class);
        assertThat(throwable).hasMessage("error.illegal-token");
    }
}
