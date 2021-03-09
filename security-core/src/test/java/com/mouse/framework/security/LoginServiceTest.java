package com.mouse.framework.security;

import com.mouse.framework.domain.core.Token;
import com.mouse.framework.domain.core.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class LoginServiceTest {

    private LoginService loginService;
    private AuthenticationService authenticationService;
    private AuthorizationService authorizationService;
    private TokenService tokenService;

    @BeforeEach
    void setUp() {
        authenticationService = mock(AuthenticationService.class);
        authorizationService = mock(AuthorizationService.class);
        tokenService = mock(TokenService.class);
        loginService = new LoginService(authenticationService, authorizationService, tokenService);
    }

    @Test
    void should_be_able_to_login_with_command() {
        LoginCommand command = mock(LoginCommand.class);
        User user = mock(User.class);
        given(authenticationService.authenticate(command)).willReturn(user);
        AuthoritiesSet authorities = new AuthoritiesSet(() -> "authority-1");
        given(authorizationService.authorize(user)).willReturn(authorities);
        Token token = mock(Token.class);
        given(tokenService.allocate(user, authorities)).willReturn(token);

        assertThat(loginService.login(command)).isEqualTo(token);
    }

    @Test
    void should_be_able_to_raise_exception_when_failed_to_authenticate() {
        LoginCommand loginCommand = mock(LoginCommand.class);
        given(authenticationService.authenticate(loginCommand)).willThrow(new UsernameNotFoundException());

        Throwable throwable = catchThrowable(() -> loginService.login(loginCommand));

        assertThat(throwable).isInstanceOf(AuthenticationException.class);
    }
}
