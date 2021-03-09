package com.mouse.framework.jwt;

public interface Signer {
    byte[] sign(String data);
}
