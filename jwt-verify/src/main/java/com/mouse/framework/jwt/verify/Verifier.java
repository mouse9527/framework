package com.mouse.framework.jwt.verify;

public interface Verifier {
    boolean verify(JWTString jwt);
}
