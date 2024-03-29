package com.mouse.framework.security.authentication;

import com.mouse.framework.domain.core.AuthoritiesSet;
import com.mouse.framework.domain.core.User;
import com.mouse.framework.security.*;

import java.util.Set;

public class LoginService {
    private final Set<IdentificationService> identificationServices;
    private final AuthenticationProvider authenticationProvider;
    private final TokenAllocator tokenAllocator;

    public LoginService(Set<IdentificationService> identificationServices,
                        AuthenticationProvider authenticationProvider,
                        TokenAllocator tokenAllocator) {
        this.identificationServices = identificationServices;
        this.authenticationProvider = authenticationProvider;
        this.tokenAllocator = tokenAllocator;
    }

    public Token login(LoginCommand command) {
        User user = findAuthenticationService(command).identify(command);
        AuthoritiesSet authorities = authenticationProvider.authenticate(user, command);
        return tokenAllocator.allocate(user, authorities, command);
    }

    private IdentificationService findAuthenticationService(LoginCommand command) {
        return identificationServices.stream()
                .filter(it -> it.isSupport(command))
                .findFirst()
                .orElseThrow(UnsupportedLoginTypeException::new);
    }
}
