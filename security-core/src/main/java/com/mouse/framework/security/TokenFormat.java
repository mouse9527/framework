package com.mouse.framework.security;

import com.mouse.framework.domain.core.Token;

public interface TokenFormat {
    String format(Token token);
}
