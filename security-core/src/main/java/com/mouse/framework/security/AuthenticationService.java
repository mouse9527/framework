package com.mouse.framework.security;

import com.mouse.framework.domain.core.User;

public interface AuthenticationService {
    User authenticate(LoginCommand command) throws AuthenticationException;
}
