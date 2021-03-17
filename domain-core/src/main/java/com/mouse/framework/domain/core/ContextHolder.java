package com.mouse.framework.domain.core;

import java.util.Optional;

public interface ContextHolder {
    Optional<User> getUser();
}
