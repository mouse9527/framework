package com.mouse.framework.jwt.sign;

import java.security.PrivateKey;

public interface PrivateKeyReader {
    PrivateKey read();
}
