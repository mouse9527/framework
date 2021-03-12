package com.mouse.framework.jwt.verify;

import com.mouse.framework.domain.core.AuthoritiesSet;
import com.mouse.framework.domain.core.Authority;
import com.mouse.framework.domain.core.Token;
import com.mouse.framework.security.TokenParser;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

class TokenParserTest {

    @Test
    void should_be_able_to_parse_token_from_string() {
        TokenParser tokenParser = new JWTTokenParser();

        Token token = tokenParser.parse("");

        assertThat(token).isNotNull();
        assertThat(token.getId()).isEqualTo("1");
        assertThat(token.getUser().getId()).isEqualTo("mock-user-id");
        assertThat(token.getUser().getUsername()).isEqualTo("mock-username");
        assertThat(token.getExpirationTime()).isEqualTo(Instant.parse("2021-03-12T00:00:00Z"));
        assertThat(token.getIssuedAt()).isEqualTo(Instant.parse("2021-03-11T00:00:00Z"));
        assertThat(token.getAuthorities()).isEqualTo(new AuthoritiesSet(new Authority("authority-1")));
    }
}
