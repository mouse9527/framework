package com.mouse.framework.security;

import com.mouse.framework.domain.core.Token;
import com.mouse.framework.domain.core.User;

public interface TokenService {
    Token allocate(User user, AuthoritiesSet authoritiesSet);
}
