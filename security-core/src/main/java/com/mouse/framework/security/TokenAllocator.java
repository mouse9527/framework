package com.mouse.framework.security;

import com.mouse.framework.domain.core.AuthoritiesSet;
import com.mouse.framework.domain.core.User;

public interface TokenAllocator {
    Token allocate(User user, AuthoritiesSet authoritiesSet, LoginCommand command);

    Token allocateRefreshToken(Token token);
}
