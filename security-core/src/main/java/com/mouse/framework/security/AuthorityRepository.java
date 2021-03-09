package com.mouse.framework.security;

import com.mouse.framework.domain.core.User;

public interface AuthorityRepository {
    AuthoritiesSet loadByUser(User user);
}
