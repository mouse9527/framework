package com.mouse.framework.security.authorization;

import com.mouse.framework.security.SecurityException;

public class AccessDeniedException extends SecurityException {
    public AccessDeniedException(String message) {
        super(message);
    }

    public AccessDeniedException(String message, Throwable cause) {
        super(message, cause);
    }
}
