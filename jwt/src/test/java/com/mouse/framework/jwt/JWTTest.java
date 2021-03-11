package com.mouse.framework.jwt;

import com.mouse.framework.domain.core.AuthoritiesSet;
import com.mouse.framework.domain.core.SequenceSetter;
import com.mouse.framework.domain.core.Token;
import com.mouse.framework.domain.core.User;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static java.time.temporal.ChronoUnit.DAYS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class JWTTest {

    @Test
    void should_be_able_to_sign_jwt() {
        SequenceSetter.set(() -> 1L);
        User user = mock(User.class);
        given(user.getId()).willReturn("mock-user-id");
        given(user.getUsername()).willReturn("mock-username");
        AuthoritiesSet authorities = new AuthoritiesSet(() -> "authority-1");
        Instant iat = Instant.now();
        Instant exp = iat.plus(1, DAYS);

        Token token = new JWT(user, authorities, iat, exp);

        assertThat(token.getId()).isEqualTo("1");
        assertThat(token.getUser()).isEqualTo(user);
        assertThat(token.getAuthorities()).isEqualTo(authorities);
        assertThat(token.getIssuedAt()).isEqualTo(iat);
        assertThat(token.getExpirationTime()).isEqualTo(exp);
        assertThat(token.isEffective()).isTrue();
    }
}
