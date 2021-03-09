package com.mouse.users.signer;

public interface Signer {
    byte[] sign(String data);
}
