package com.mouse.framework.security;

import java.util.Optional;

public interface UserRepository {
    Optional<User> loadByUsername(String username);
}
