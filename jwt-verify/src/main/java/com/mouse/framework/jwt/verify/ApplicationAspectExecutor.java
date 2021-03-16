package com.mouse.framework.jwt.verify;

import com.mouse.framework.security.AuthenticationService;

public class ApplicationAspectExecutor {

    private final AuthenticationService authenticationService;

    public ApplicationAspectExecutor(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    public void execute(Boolean requiredLogged, String[] requiredAuthorities) {
        if (requiredLogged) authenticationService.requireLogged();

        if (requiredAuthorities != null && requiredAuthorities.length > 0)
            authenticationService.requireAuthorities(requiredAuthorities);
    }
}
