package com.mouse.framework.security;

public interface AuthorityRepository {
    AuthoritiesSet loadByUser(User user);
}
