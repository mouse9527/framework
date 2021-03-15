package com.mouse.framework.security;

import com.mouse.framework.domain.core.User;

public interface IdentificationService {
    User identify(LoginCommand command) throws SecurityException;

    Boolean isSupport(LoginCommand command);
}
