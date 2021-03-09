package com.mouse.users.signer;

public class Header {
    private final String typ;
    private final String alg;

    public Header(String typ, String alg) {
        this.typ = typ;
        this.alg = alg;
    }

    public String getTyp() {
        return typ;
    }

    public String getAlg() {
        return alg;
    }
}
