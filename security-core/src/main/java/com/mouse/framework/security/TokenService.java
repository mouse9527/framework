package com.mouse.framework.security;

import com.mouse.framework.domain.core.Token;

public interface TokenService {
    Token allocate(User user, AuthoritiesSet authoritiesSet);
}
