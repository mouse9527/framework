package com.mouse.framework.security.authentication.jwt;

public interface Encryptor {
    String encrypt(String raw);
}
