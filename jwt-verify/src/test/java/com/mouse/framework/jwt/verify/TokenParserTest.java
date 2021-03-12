package com.mouse.framework.jwt.verify;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mouse.framework.domain.core.*;
import com.mouse.framework.jwt.JWT;
import com.mouse.framework.jwt.sign.JWTFormat;
import com.mouse.framework.jwt.sign.RSAEncryptor;
import com.mouse.framework.jwt.sign.RSASigner;
import com.mouse.framework.security.TokenFormat;
import com.mouse.framework.security.TokenParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class TokenParserTest {

    private static final String MOCK_USER_ID = "mock-user-id";
    private static final String MOCK_USERNAME = "mock-username";
    private TokenParser tokenParser;
    private KeyPair keyPair;

    private static Stream<String> illegalJwt() {
        return Stream.of(null, "", "x.x", "xx.xx.xx", "x.x.x.x");
    }

    @BeforeEach
    void setUp() throws NoSuchAlgorithmException {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(1024);
        keyPair = generator.generateKeyPair();
        tokenParser = new JWTTokenParser(new RSADecryptor(keyPair.getPublic()), new ObjectMapper());
    }

    @Test
    void should_be_able_to_parse_token_from_string() {
        AuthoritiesSet authorities = new AuthoritiesSet(new Authority("authority-1"));
        Instant exp = Instant.parse("2021-03-12T00:00:00Z");
        Instant iat = Instant.parse("2021-03-11T00:00:00Z");
        SequenceSetter.set(() -> 1L);
        TokenFormat tokenFormat = new JWTFormat(new RSAEncryptor(keyPair.getPrivate()), new RSASigner(keyPair.getPrivate()), new ObjectMapper());
        User user = mock(User.class);
        given(user.getId()).willReturn(MOCK_USER_ID);
        given(user.getUsername()).willReturn(MOCK_USERNAME);
        String text = tokenFormat.format(new JWT(user, authorities, iat, exp));

        Token token = tokenParser.parse(text);

        assertThat(token).isNotNull();
        assertThat(token.getId()).isEqualTo("1");
        assertThat(token.getUser().getId()).isEqualTo(MOCK_USER_ID);
        assertThat(token.getUser().getUsername()).isEqualTo(MOCK_USERNAME);
        assertThat(token.getExpirationTime()).isEqualTo(exp);
        assertThat(token.getIssuedAt()).isEqualTo(iat);
        assertThat(token.getAuthorities()).isEqualTo(authorities);
    }

    @ParameterizedTest
    @MethodSource("illegalJwt")
    void should_be_able_to_raise_exception_with_illegal_jwt_text(String illegalJwt) {
        Throwable throwable = catchThrowable(() -> tokenParser.parse(illegalJwt));

        assertThat(throwable).isNotNull();
        assertThat(throwable).isInstanceOf(IllegalTokenException.class);
        assertThat(throwable).hasMessage("error.illegal-token");
    }
}
