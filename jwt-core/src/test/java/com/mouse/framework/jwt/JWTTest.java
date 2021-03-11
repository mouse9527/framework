package com.mouse.framework.jwt;

import com.mouse.framework.domain.core.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Collections;

import static java.time.temporal.ChronoUnit.DAYS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class JWTTest {
    private static final String AUTHORITY_1 = "authority-1";
    private static final String MOCK_JTI = "1";
    private static final String MOCK_USER_ID = "mock-user-id";
    private static final String MOCK_USERNAME = "mock-username";
    private Instant iat;
    private Instant exp;

    @BeforeEach
    void setUp() {
        iat = Instant.ofEpochSecond(Instant.now().getEpochSecond());
        exp = iat.plus(1, DAYS);
    }

    @Test
    void should_be_able_to_create() {
        SequenceSetter.set(() -> 1L);
        User user = mock(User.class);
        given(user.getId()).willReturn(MOCK_USER_ID);
        given(user.getUsername()).willReturn(MOCK_USERNAME);

        JWT jwt = new JWT(user, new AuthoritiesSet(new Authority(AUTHORITY_1)), iat, exp);

        assertJWT(jwt);
    }

    @Test
    void should_be_able_to_create_from_payload() {
        Payload payload = new Payload(iat.getEpochSecond(), exp.getEpochSecond(), MOCK_JTI, Collections.singleton(AUTHORITY_1), MOCK_USERNAME, MOCK_USER_ID);

        Token jwt = new JWT(payload);

        assertJWT(jwt);
    }

    private void assertJWT(Token jwt) {
        assertThat(jwt.getId()).isEqualTo(MOCK_JTI);
        assertThat(jwt.getAuthorities()).isEqualTo(new AuthoritiesSet(new Authority(AUTHORITY_1)));
        assertThat(jwt.getIssuedAt()).isEqualTo(iat);
        assertThat(jwt.getExpirationTime()).isEqualTo(exp);
        assertThat(jwt.getUser().getId()).isEqualTo(MOCK_USER_ID);
        assertThat(jwt.getUser().getUsername()).isEqualTo(MOCK_USERNAME);
        assertThat(jwt.isEffective()).isTrue();
    }
}
