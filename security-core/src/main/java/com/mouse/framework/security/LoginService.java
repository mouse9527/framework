package com.mouse.framework.security;

import com.mouse.framework.domain.core.Token;
import com.mouse.framework.domain.core.User;

import java.util.Set;

public class LoginService {
    private final Set<AuthenticationService> authenticationServices;
    private final AuthorizationService authorizationService;
    private final TokenService tokenService;

    public LoginService(Set<AuthenticationService> authenticationServices, AuthorizationService authorizationService, TokenService tokenService) {
        this.authenticationServices = authenticationServices;
        this.authorizationService = authorizationService;
        this.tokenService = tokenService;
    }

    public Token login(LoginCommand command) {
        User user = findAuthenticationService(command).authenticate(command);
        AuthoritiesSet authorities = authorizationService.authorize(user);
        return tokenService.allocate(user, authorities);
    }

    private AuthenticationService findAuthenticationService(LoginCommand command) {
        return authenticationServices.stream()
                .filter(it -> it.isSupport(command))
                .findFirst()
                .orElseThrow(UnsupportedLoginTypeException::new);
    }
}
