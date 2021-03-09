package com.mouse.framework.domain.core;

import java.util.Collection;

public interface Token {
    String getId();

    String getUsername();

    String getUserId();

    Collection<? extends Authority> getAuthorities();
}
