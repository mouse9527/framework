package com.mouse.framework.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mouse.framework.domain.core.AuthoritiesSet;
import com.mouse.framework.domain.core.SequenceSetter;
import com.mouse.framework.domain.core.Token;
import com.mouse.framework.domain.core.User;
import com.mouse.framework.security.TokenFormat;
import com.mouse.framework.test.TestJsonObject;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Base64;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class JWTFormatTest {
    private static final String MOCK_USER_ID = "mock-user-id";
    private static final String ENCRYPTED = String.format("cip-%s", MOCK_USER_ID);
    private static final String MOCK_SIGNATURE = "mock-signature";
    private static final String MOCK_USERNAME = "mock-username";
    private static final String AUTHORITY_1 = "authority-1";
    private static final String RS_1024 = "RS1024";

    @Test
    void should_be_able_to_parse_jwt_token_to_string() {
        SequenceSetter.set(() -> 1L);
        User user = mock(User.class);
        given(user.getId()).willReturn(MOCK_USER_ID);
        given(user.getUsername()).willReturn(MOCK_USERNAME);
        AuthoritiesSet authorities = new AuthoritiesSet(() -> AUTHORITY_1);
        Instant iat = Instant.now();
        Instant exp = iat.plus(1, DAYS);
        Token jwt = new JWT(user, authorities, iat, exp);
        Encryptor encryptor = it -> ENCRYPTED;
        Signer signer = mock(Signer.class);
        given(signer.sign(any())).willReturn(MOCK_SIGNATURE.getBytes(StandardCharsets.UTF_8));
        given(signer.getType()).willReturn(RS_1024);
        TokenFormat jwtFormat = new JWTFormat(encryptor, signer, new ObjectMapper());

        String token = jwtFormat.format(jwt);

        assertThat(token).isNotEmpty();
        String[] split = token.split("\\.");
        assertThat(split).hasSize(3);
        TestJsonObject header = new TestJsonObject(new String(Base64.getDecoder().decode(split[0])));
        assertThat(header.strVal("$.typ")).isEqualTo("JWT");
        assertThat(header.strVal("$.alg")).isEqualTo(RS_1024);
        TestJsonObject payload = new TestJsonObject(new String(Base64.getDecoder().decode(split[1])));
        assertThat(payload.strVal("$.jti")).isEqualTo("1");
        assertThat(payload.strVal("$.nam")).isEqualTo(MOCK_USERNAME);
        assertThat(payload.<List<String>>value("$.aut")).containsOnly(AUTHORITY_1);
        assertThat(payload.intVal("$.iat")).isEqualTo(iat.getEpochSecond());
        assertThat(payload.intVal("$.exp")).isEqualTo(exp.getEpochSecond());
        assertThat(payload.strVal("$.cip")).isEqualTo(ENCRYPTED);
        assertThat(split[2]).isEqualTo(Base64.getEncoder().encodeToString(MOCK_SIGNATURE.getBytes(StandardCharsets.UTF_8)));
    }
}
