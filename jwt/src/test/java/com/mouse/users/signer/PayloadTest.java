package com.mouse.users.signer;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Collections;

import static java.time.temporal.ChronoUnit.DAYS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class PayloadTest {

    @Test
    void should_be_able_to_create_Payload() {
        Instant iat = Instant.now();
        Instant exp = iat.plus(1, DAYS);
        Encryptor encryptor = mock(Encryptor.class);
        doReturn("mock-ciphertext").when(encryptor).encrypt("raw-ciphertext");

        Payload payload = Payload.builder()
                .issuedAt(iat.getEpochSecond())
                .expirationTime(exp.getEpochSecond())
                .jwtId("mock-jti")
                .name("mock-name")
                .authorities(Collections.singleton("mock-authority"))
                .ciphertext("raw-ciphertext")
                .build(encryptor);

        assertThat(payload.getIat()).isEqualTo(iat.getEpochSecond());
        assertThat(payload.getExp()).isEqualTo(exp.getEpochSecond());
        assertThat(payload.getJti()).isEqualTo("mock-jti");
        assertThat(payload.getAut()).isEqualTo(Collections.singleton("mock-authority"));
        assertThat(payload.getNam()).isEqualTo("mock-name");
        assertThat(payload.getCip()).isEqualTo("mock-ciphertext");
    }
}
