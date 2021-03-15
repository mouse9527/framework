package com.mouse.framework.security;

import com.mouse.framework.domain.core.Token;
import com.mouse.framework.domain.core.TokenHolder;
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
}
