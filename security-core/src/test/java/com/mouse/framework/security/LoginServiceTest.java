package com.mouse.framework.security;

import com.mouse.framework.domain.core.AuthoritiesSet;
import com.mouse.framework.domain.core.Token;
import com.mouse.framework.domain.core.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class LoginServiceTest {
    private LoginService loginService;
    private AuthenticationService authenticationService;
    private AuthorizationService authorizationService;
    private TokenAllocator tokenAllocator;

    @BeforeEach
    void setUp() {
        authenticationService = mock(AuthenticationService.class);
        given(authenticationService.isSupport(any())).willReturn(true);
        authorizationService = mock(AuthorizationService.class);
        tokenAllocator = mock(TokenAllocator.class);
        loginService = new LoginService(Collections.singleton(authenticationService), authorizationService, tokenAllocator);
    }

    @Test
    void should_be_able_to_login_with_command() {
        LoginCommand command = mock(LoginCommand.class);
        User user = mock(User.class);
        given(authenticationService.authenticate(command)).willReturn(user);
        AuthoritiesSet authorities = new AuthoritiesSet(() -> "authority-1");
        given(authorizationService.authorize(user, command)).willReturn(authorities);
        Token token = mock(Token.class);
        given(tokenAllocator.allocate(user, authorities, command)).willReturn(token);

        assertThat(loginService.login(command)).isEqualTo(token);
    }

    @Test
    void should_be_able_to_raise_exception_when_failed_to_authenticate() {
        LoginCommand loginCommand = mock(LoginCommand.class);
        given(authenticationService.authenticate(loginCommand)).willThrow(new UsernameNotFoundException());

        Throwable throwable = catchThrowable(() -> loginService.login(loginCommand));

        assertThat(throwable).isInstanceOf(AuthenticationException.class);
    }

    @Test
    void should_be_able_to_raise_exception_when_no_support_authentication_service() {
        AuthenticationService authenticationService = mock(AuthenticationService.class);
        LoginCommand command = mock(LoginCommand.class);
        given(authenticationService.isSupport(command)).willReturn(false);
        LoginService loginService = new LoginService(Collections.singleton(authenticationService), authorizationService, tokenAllocator);

        Throwable throwable = catchThrowable(() -> loginService.login(command));

        assertThat(throwable).isInstanceOf(UnsupportedLoginTypeException.class);
        assertThat(throwable).hasMessage("error.unsupported-login-type");
    }
}
