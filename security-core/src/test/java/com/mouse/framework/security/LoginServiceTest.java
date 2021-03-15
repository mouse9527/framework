package com.mouse.framework.security;

import com.mouse.framework.domain.core.AuthoritiesSet;
import com.mouse.framework.domain.core.Authority;
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
    private IdentificationService identificationService;
    private AuthorizationService authorizationService;
    private TokenAllocator tokenAllocator;

    @BeforeEach
    void setUp() {
        identificationService = mock(IdentificationService.class);
        given(identificationService.isSupport(any())).willReturn(true);
        authorizationService = mock(AuthorizationService.class);
        tokenAllocator = mock(TokenAllocator.class);
        loginService = new LoginService(Collections.singleton(identificationService), authorizationService, tokenAllocator);
    }

    @Test
    void should_be_able_to_login_with_command() {
        LoginCommand command = mock(LoginCommand.class);
        User user = mock(User.class);
        given(identificationService.identify(command)).willReturn(user);
        AuthoritiesSet authorities = new AuthoritiesSet(new Authority("authority-1"));
        given(authorizationService.authorize(user, command)).willReturn(authorities);
        Token token = mock(Token.class);
        given(tokenAllocator.allocate(user, authorities, command)).willReturn(token);

        assertThat(loginService.login(command)).isEqualTo(token);
    }

    @Test
    void should_be_able_to_raise_exception_when_failed_to_authenticate() {
        LoginCommand loginCommand = mock(LoginCommand.class);
        given(identificationService.identify(loginCommand)).willThrow(new UsernameNotFoundException());

        Throwable throwable = catchThrowable(() -> loginService.login(loginCommand));

        assertThat(throwable).isInstanceOf(SecurityException.class);
    }

    @Test
    void should_be_able_to_raise_exception_when_no_support_authentication_service() {
        IdentificationService identificationService = mock(IdentificationService.class);
        LoginCommand command = mock(LoginCommand.class);
        given(identificationService.isSupport(command)).willReturn(false);
        LoginService loginService = new LoginService(Collections.singleton(identificationService), authorizationService, tokenAllocator);

        Throwable throwable = catchThrowable(() -> loginService.login(command));

        assertThat(throwable).isInstanceOf(UnsupportedLoginTypeException.class);
        assertThat(throwable).hasMessage("error.unsupported-login-type");
    }
}
