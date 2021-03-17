package com.mouse.framework.security.authentication;

import com.mouse.framework.domain.core.User;
import com.mouse.framework.security.SecurityException;

public interface IdentificationService {
    User identify(LoginCommand command) throws SecurityException;

    Boolean isSupport(LoginCommand command);
}
