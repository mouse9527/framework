package com.mouse.framework.security.authentication.jwt;

public interface Signer {
    byte[] sign(String data);

    String getType();
}
