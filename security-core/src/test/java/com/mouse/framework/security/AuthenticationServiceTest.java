package com.mouse.framework.security;

import com.mouse.framework.domain.core.AuthoritiesSet;
import com.mouse.framework.domain.core.Authority;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class AuthenticationServiceTest {

    private AuthenticationService authenticationService;
    private TokenHolder tokenHolder;

    @BeforeEach
    void setUp() {
        tokenHolder = mock(TokenHolder.class);
        authenticationService = new AuthenticationService(tokenHolder);
    }

    @Test
    void should_be_able_to_require_logged() {
        given(tokenHolder.get()).willReturn(Optional.of(mock(Token.class)));

        Throwable throwable = catchThrowable(authenticationService::requireLogged);

        assertThat(throwable).isNull();
    }

    @Test
    void should_be_able_to_raise_exception_when_not_login() {
        given(tokenHolder.get()).willReturn(Optional.empty());

        Throwable throwable = catchThrowable(authenticationService::requireLogged);

        assertThat(throwable).isNotNull();
        assertThat(throwable).isInstanceOf(IllegalTokenException.class);
        assertThat(throwable).hasMessage("error.illegal-token");
    }

    @Test
    void should_be_able_to_authenticate_authorities() {
        Token token = mock(Token.class);
        given(token.getAuthorities()).willReturn(new AuthoritiesSet(new Authority("authority-1"), new Authority("authority-2")));
        given(tokenHolder.get()).willReturn(Optional.of(token));

        Throwable throwable = catchThrowable(() -> authenticationService.requireAuthorities("authority-1", "authority-2"));

        assertThat(throwable).isNull();

        Throwable forbidden = catchThrowable(() -> authenticationService.requireAuthorities("authority-1", "authority-3"));
        assertThat(forbidden).isNotNull();
        assertThat(forbidden).isInstanceOf(AuthenticationException.class);
        assertThat(forbidden).hasMessage("error.failed-to-authentication");

        given(tokenHolder.get()).willReturn(Optional.empty());

        Throwable unLogin = catchThrowable(() -> authenticationService.requireAuthorities("authority-1"));

        assertThat(unLogin).isNotNull();
        assertThat(unLogin).isInstanceOf(IllegalTokenException.class);
        assertThat(unLogin).hasMessage("error.illegal-token");
    }

}
