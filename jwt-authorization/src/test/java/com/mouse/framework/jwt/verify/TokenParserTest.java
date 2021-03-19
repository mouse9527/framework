package com.mouse.framework.jwt.verify;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mouse.framework.domain.core.*;
import com.mouse.framework.jwt.JWT;
import com.mouse.framework.security.authentication.jwt.JWTFormat;
import com.mouse.framework.security.authentication.jwt.RSAEncryptor;
import com.mouse.framework.security.authentication.jwt.RSASigner;
import com.mouse.framework.security.Token;
import com.mouse.framework.security.authentication.TokenFormat;
import com.mouse.framework.security.authorization.TokenParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.Base64;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class TokenParserTest {
    private static final String MOCK_USER_ID = "mock-user-id";
    private static final String MOCK_USERNAME = "mock-username";
    private TokenParser tokenParser;
    private KeyPair keyPair;
    private Verifier verifier;

    private static Stream<String> illegalJWTs() {
        String illegalToken = String.format("illegal.xxx.%s", Base64.getEncoder().encodeToString("token".getBytes(StandardCharsets.UTF_8)));
        return Stream.of(null, "", "x", "x.x", "x.x.x.x", "x.x.x", illegalToken);
    }

    @BeforeEach
    void setUp() throws NoSuchAlgorithmException {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(1024);
        keyPair = generator.generateKeyPair();
        verifier = mock(Verifier.class);
        given(verifier.verify(any(JWTString.class))).willReturn(true);
        tokenParser = new JWTTokenParser(new RSADecryptor(keyPair.getPublic(), "RSA"), verifier, new ObjectMapper());
    }

    @Test
    void should_be_able_to_parse_token_from_string() {
        AuthoritiesSet authorities = new AuthoritiesSet(() -> "authority-1");
        Instant exp = Instant.parse("2021-03-12T00:00:00Z");
        Instant iat = Instant.parse("2021-03-11T00:00:00Z");
        SequenceSetter.set(new FixedSequenceService("mock-id"));
        TokenFormat tokenFormat = new JWTFormat(new RSAEncryptor(keyPair.getPrivate(), "RSA"), new RSASigner(keyPair.getPrivate(), "SHA1WithRSA"), new ObjectMapper());
        User user = mock(User.class);
        given(user.getId()).willReturn(MOCK_USER_ID);
        given(user.getUsername()).willReturn(MOCK_USERNAME);
        String text = tokenFormat.format(new JWT(user, authorities, iat, exp));

        Optional<Token> optional = tokenParser.parse(text);

        assertThat(optional).isPresent();
        Token token = optional.get();
        assertThat(token.getId()).isEqualTo("mock-id");
        assertThat(token.getUser().getId()).isEqualTo(MOCK_USER_ID);
        assertThat(token.getUser().getUsername()).isEqualTo(MOCK_USERNAME);
        assertThat(token.getExpirationTime()).isEqualTo(exp);
        assertThat(token.getIssuedAt()).isEqualTo(iat);
        assertThat(token.getAuthorities().getAuthorities()).containsOnly("authority-1");
    }

    @ParameterizedTest
    @MethodSource("illegalJWTs")
    void should_be_able_to_get_empty_when_failed_to_verify(String illegalToken) {
        given(verifier.verify(any())).willReturn(false);

        Optional<Token> optional = tokenParser.parse(illegalToken);

        assertThat(optional).isEmpty();
    }
}
