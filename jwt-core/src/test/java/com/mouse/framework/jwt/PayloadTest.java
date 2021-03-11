package com.mouse.framework.jwt;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Collections;

import static java.time.temporal.ChronoUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThat;

class PayloadTest {
    private static final String AUTHORITY_1 = "authority-1";
    private static final String MOCK_NAME = "mock-name";
    private static final String MOCK_CIP = "mock-cip";
    private static final String MOCK_JTI = "mock-jti";

    @Test
    void should_be_able_to_build() {
        Instant iat = Instant.now();
        Instant exp = iat.plus(1, SECONDS);
        Payload payload = Payload.builder()
                .jwtId(MOCK_JTI)
                .ciphertext(MOCK_CIP)
                .name(MOCK_NAME)
                .authorities(Collections.singleton(AUTHORITY_1))
                .issuedAt(iat.getEpochSecond())
                .expirationTime(exp.getEpochSecond())
                .build();

        assertThat(payload.getJti()).isEqualTo(MOCK_JTI);
        assertThat(payload.getCip()).isEqualTo(MOCK_CIP);
        assertThat(payload.getNam()).isEqualTo(MOCK_NAME);
        assertThat(payload.getAut()).containsOnly(AUTHORITY_1);
        assertThat(payload.getIat()).isEqualTo(iat.getEpochSecond());
        assertThat(payload.getExp()).isEqualTo(exp.getEpochSecond());
    }
}
