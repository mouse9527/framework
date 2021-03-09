package com.mouse.framework.security;

public interface AuthorizationService {
    AuthoritiesSet authorize(User user);
}
