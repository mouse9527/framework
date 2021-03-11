package com.mouse.framework.jwt;

import lombok.Getter;

@Getter
public class Header {
    private final String typ;
    private final String alg;

    public Header(String typ, String alg) {
        this.typ = typ;
        this.alg = alg;
    }
}
