package com.mouse.framework.security;

import com.mouse.framework.domain.core.AuthoritiesSet;
import com.mouse.framework.domain.core.Context;
import com.mouse.framework.domain.core.User;

import java.time.Instant;

public interface Token {
    String getId();

    User getUser();

    Instant getIssuedAt();

    Instant getExpirationTime();

    AuthoritiesSet getAuthorities();

    default Boolean isEffective() {
        return Context.now().isBefore(getExpirationTime());
    }
}
