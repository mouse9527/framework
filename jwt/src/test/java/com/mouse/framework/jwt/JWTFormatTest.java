package com.mouse.framework.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mouse.framework.domain.core.AuthoritiesSet;
import com.mouse.framework.domain.core.SequenceSetter;
import com.mouse.framework.domain.core.User;
import com.mouse.framework.test.TestJsonObject;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Base64;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class JWTFormatTest {

    @Test
    void should_be_able_to_parse_jwt_token_to_string() {
        SequenceSetter.set(() -> 1L);
        User user = mock(User.class);
        given(user.getId()).willReturn("mock-user-id");
        given(user.getUsername()).willReturn("mock-username");
        AuthoritiesSet authorities = new AuthoritiesSet(() -> "authority-1");
        Instant iat = Instant.now();
        Instant exp = iat.plus(1, DAYS);
        JWT jwt = new JWT(user, authorities, iat, exp);
        Encryptor encryptor = it -> String.format("cip-%s", it);
        Signer signer = it -> "mock-signature".getBytes(StandardCharsets.UTF_8);
        JWTFormat jwtFormat = new JWTFormat(encryptor, signer, new ObjectMapper());

        String token = jwtFormat.format(jwt);

        assertThat(token).isNotEmpty();
        String[] split = token.split("\\.");
        assertThat(split).hasSize(3);
        TestJsonObject header = new TestJsonObject(new String(Base64.getDecoder().decode(split[0])));
        assertThat(header.strVal("$.typ")).isEqualTo("JWT");
        assertThat(header.strVal("$.alg")).isEqualTo("RS1024");
        TestJsonObject payload = new TestJsonObject(new String(Base64.getDecoder().decode(split[1])));
        assertThat(payload.strVal("$.jti")).isEqualTo("1");
        assertThat(payload.strVal("$.nam")).isEqualTo("mock-username");
        assertThat(payload.<List<String>>value("$.aut")).containsOnly("authority-1");
        assertThat(payload.intVal("$.iat")).isEqualTo(iat.getEpochSecond());
        assertThat(payload.intVal("$.exp")).isEqualTo(exp.getEpochSecond());
        assertThat(payload.strVal("$.cip")).isEqualTo("cip-mock-user-id");
        assertThat(split[2]).isEqualTo(Base64.getEncoder().encodeToString("mock-signature".getBytes(StandardCharsets.UTF_8)));
    }
}
