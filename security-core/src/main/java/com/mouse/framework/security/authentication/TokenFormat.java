package com.mouse.framework.security.authentication;

import com.mouse.framework.security.Token;

public interface TokenFormat {
    String format(Token token);
}
