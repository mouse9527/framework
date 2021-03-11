package com.mouse.framework.security;

import com.mouse.framework.domain.core.AuthoritiesSet;
import com.mouse.framework.domain.core.User;

public interface AuthorizationService {
    AuthoritiesSet authorize(User user, LoginCommand command);
}
