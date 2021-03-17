package com.mouse.framework.security;

import com.mouse.framework.domain.core.AuthoritiesSet;
import com.mouse.framework.domain.core.User;

import java.util.Set;

public class LoginService {
    private final Set<IdentificationService> identificationServices;
    private final AuthorizationService authorizationService;
    private final TokenAllocator tokenAllocator;

    public LoginService(Set<IdentificationService> identificationServices, AuthorizationService authorizationService, TokenAllocator tokenAllocator) {
        this.identificationServices = identificationServices;
        this.authorizationService = authorizationService;
        this.tokenAllocator = tokenAllocator;
    }

    public Token login(LoginCommand command) {
        User user = findAuthenticationService(command).identify(command);
        AuthoritiesSet authorities = authorizationService.authorize(user, command);
        return tokenAllocator.allocate(user, authorities, command);
    }

    private IdentificationService findAuthenticationService(LoginCommand command) {
        return identificationServices.stream()
                .filter(it -> it.isSupport(command))
                .findFirst()
                .orElseThrow(UnsupportedLoginTypeException::new);
    }
}
