package com.mouse.framework.security.authentication;

import com.mouse.framework.domain.core.AuthoritiesSet;
import com.mouse.framework.domain.core.User;
import com.mouse.framework.security.Token;

public interface TokenAllocator {
    Token allocate(User user, AuthoritiesSet authoritiesSet, LoginCommand command);

    Token allocateRefreshToken(Token token, LoginCommand command);
}
