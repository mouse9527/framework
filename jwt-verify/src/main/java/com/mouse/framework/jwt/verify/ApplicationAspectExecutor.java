package com.mouse.framework.jwt.verify;

import com.mouse.framework.security.authorization.AuthorizationService;

import java.util.Arrays;

public class ApplicationAspectExecutor {
    private final AuthorizationService authorizationService;

    public ApplicationAspectExecutor(AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }

    public void execute(Boolean requiredLogged, String[] requiredAuthorities) {
        if (requiredLogged) authorizationService.requireLogged();

        if (isRequiredAuthorities(requiredAuthorities))
            authorizationService.requireAuthorities(requiredAuthorities);
    }

    private boolean isRequiredAuthorities(String[] requiredAuthorities) {
        return requiredAuthorities != null
                && requiredAuthorities.length > 0
                && !Arrays.equals(new String[]{""}, requiredAuthorities);
    }
}
