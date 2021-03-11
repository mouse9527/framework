package com.mouse.framework.domain.core;

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
