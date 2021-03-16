package com.mouse.framework.jwt.verify;

import com.mouse.framework.security.AuthenticationService;

import java.util.Arrays;

public class ApplicationAspectExecutor {

    private final AuthenticationService authenticationService;

    public ApplicationAspectExecutor(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    public void execute(Boolean requiredLogged, String[] requiredAuthorities) {
        if (requiredLogged) authenticationService.requireLogged();

        if (isRequiredAuthorities(requiredAuthorities))
            authenticationService.requireAuthorities(requiredAuthorities);
    }

    private boolean isRequiredAuthorities(String[] requiredAuthorities) {
        return requiredAuthorities != null
                && requiredAuthorities.length > 0
                && !Arrays.equals(new String[]{""}, requiredAuthorities);
    }
}
