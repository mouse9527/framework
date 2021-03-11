package com.mouse.framework.security;

import com.mouse.framework.domain.core.AuthoritiesSet;
import com.mouse.framework.domain.core.Token;
import com.mouse.framework.domain.core.User;

import java.util.Set;

public class LoginService {
    private final Set<AuthenticationService> authenticationServices;
    private final AuthorizationService authorizationService;
    private final TokenAllocator tokenAllocator;

    public LoginService(Set<AuthenticationService> authenticationServices, AuthorizationService authorizationService, TokenAllocator tokenAllocator) {
        this.authenticationServices = authenticationServices;
        this.authorizationService = authorizationService;
        this.tokenAllocator = tokenAllocator;
    }

    public Token login(LoginCommand command) {
        User user = findAuthenticationService(command).authenticate(command);
        AuthoritiesSet authorities = authorizationService.authorize(user, command);
        return tokenAllocator.allocate(user, authorities, command);
    }

    private AuthenticationService findAuthenticationService(LoginCommand command) {
        return authenticationServices.stream()
                .filter(it -> it.isSupport(command))
                .findFirst()
                .orElseThrow(UnsupportedLoginTypeException::new);
    }
}
