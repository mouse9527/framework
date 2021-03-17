package com.mouse.framework.security.authentication.jwt;

import java.security.PrivateKey;

public interface PrivateKeyReader {
    PrivateKey read();
}
