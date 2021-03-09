package com.mouse.framework.security;

public interface AuthenticationService {
    User authenticate(LoginCommand command);
}
