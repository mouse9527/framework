package com.mouse.framework.domain.core;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class Authority {
    private final String authority;

    public Authority(String authority) {
        this.authority = authority;
    }

    public String getAuthority() {
        return authority;
    }
}
