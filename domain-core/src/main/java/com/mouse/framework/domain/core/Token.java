package com.mouse.framework.domain.core;

import java.time.Instant;
import java.util.Collection;

public interface Token {
    String getId();

    String getUsername();

    String getUserId();

    Instant getIssuedAt();

    Instant getExpirationTime();

    Collection<? extends Authority> getAuthorities();
}
