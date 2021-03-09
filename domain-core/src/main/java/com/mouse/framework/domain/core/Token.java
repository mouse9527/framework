package com.mouse.framework.domain.core;

import java.time.Instant;
import java.util.Collection;

public interface Token {
    String getId();

    User getUser();

    Instant getIssuedAt();

    Instant getExpirationTime();

    Collection<? extends Authority> getAuthorities();
}
