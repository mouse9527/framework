package com.mouse.framework.security.authorization;

import com.mouse.framework.security.AuthenticationException;
import lombok.Generated;

@Generated
public class UnLoggedException extends AuthenticationException {
    public UnLoggedException(String message) {
        super(message);
    }

    public UnLoggedException(String message, Throwable cause) {
        super(message, cause);
    }
}
