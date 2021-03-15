package com.mouse.framework.jwt.verify;

public interface Verifier {
    @Deprecated
    boolean verify(String text);

    boolean verify(JWTString jwt);
}
