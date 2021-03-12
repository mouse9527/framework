package com.mouse.framework.jwt.verify;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mouse.framework.domain.core.*;
import com.mouse.framework.jwt.JWT;
import com.mouse.framework.jwt.sign.JWTFormat;
import com.mouse.framework.jwt.sign.RsaEncryptor;
import com.mouse.framework.jwt.sign.RsaSigner;
import com.mouse.framework.security.TokenFormat;
import com.mouse.framework.security.TokenParser;
import org.junit.jupiter.api.Test;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class TokenParserTest {

    @Test
    void should_be_able_to_parse_token_from_string() throws NoSuchAlgorithmException {
        AuthoritiesSet authorities = new AuthoritiesSet(new Authority("authority-1"));
        Instant exp = Instant.parse("2021-03-12T00:00:00Z");
        Instant iat = Instant.parse("2021-03-11T00:00:00Z");
        SequenceSetter.set(() -> 1L);
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(1024);
        KeyPair keyPair = generator.generateKeyPair();
        TokenFormat tokenFormat = new JWTFormat(new RsaEncryptor(keyPair.getPrivate()), new RsaSigner(keyPair.getPrivate()), new ObjectMapper());
        User user = mock(User.class);
        given(user.getId()).willReturn("mock-user-id");
        given(user.getUsername()).willReturn("mock-username");
        String text = tokenFormat.format(new JWT(user, authorities, iat, exp));
        TokenParser tokenParser = new JWTTokenParser(new RSADecryptor(keyPair.getPublic()), new ObjectMapper());

        Token token = tokenParser.parse(text);

        assertThat(token).isNotNull();
        assertThat(token.getId()).isEqualTo("1");
        assertThat(token.getUser().getId()).isEqualTo("mock-user-id");
        assertThat(token.getUser().getUsername()).isEqualTo("mock-username");
        assertThat(token.getExpirationTime()).isEqualTo(exp);
        assertThat(token.getIssuedAt()).isEqualTo(iat);
        assertThat(token.getAuthorities()).isEqualTo(authorities);
    }

}
