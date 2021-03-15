package com.mouse.framework.jwt.verify;

import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static org.assertj.core.api.Assertions.assertThat;

class JWTStringTest {

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
}
