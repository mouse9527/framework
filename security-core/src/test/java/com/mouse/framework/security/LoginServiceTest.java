package com.mouse.framework.security;

import com.mouse.framework.domain.core.Token;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class LoginServiceTest {

    @Test
    void should_be_able_to_login_with_command() {
        LoginCommand command = mock(LoginCommand.class);
        AuthenticationService authenticationService = mock(AuthenticationService.class);
        User user = mock(User.class);
        given(authenticationService.authenticate(command)).willReturn(user);
        AuthorizationService authorizationService = mock(AuthorizationService.class);
        AuthoritiesSet authorities = new AuthoritiesSet(() -> "authority-1");
        given(authorizationService.authorize(user)).willReturn(authorities);
        TokenService tokenService = mock(TokenService.class);
        Token token = mock(Token.class);
        given(tokenService.allocate(user, authorities)).willReturn(token);

        LoginService loginService = new LoginService(authenticationService, authorizationService, tokenService);

        assertThat(loginService.login(command)).isEqualTo(token);
    }
}
