package com.mouse.framework.security;

public interface User {
    String getId();

    String getUsername();

    String getPassword();

    boolean isEnabled();
}
