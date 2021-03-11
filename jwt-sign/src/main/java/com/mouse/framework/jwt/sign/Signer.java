package com.mouse.framework.jwt.sign;

public interface Signer {
    byte[] sign(String data);

    String getType();
}
