package com.mouse.framework.security;

import com.mouse.framework.domain.core.Token;
import com.mouse.framework.domain.core.User;

public class LoginService {
    private final AuthenticationService authenticationService;
    private final AuthorizationService authorizationService;
    private final TokenService tokenService;

    public LoginService(AuthenticationService authenticationService, AuthorizationService authorizationService, TokenService tokenService) {
        this.authenticationService = authenticationService;
        this.authorizationService = authorizationService;
        this.tokenService = tokenService;
    }

    public Token login(LoginCommand command) {
        User user = authenticationService.authenticate(command);
        AuthoritiesSet authorities = authorizationService.authorize(user);
        return tokenService.allocate(user, authorities);
    }
}
