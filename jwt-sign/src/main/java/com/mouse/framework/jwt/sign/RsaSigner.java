package com.mouse.framework.jwt.sign;

import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;

public class RsaSigner implements Signer {
    private final Signature signer;
    private final String type;

    public RsaSigner(PrivateKey privateKey) {
        try {
            signer = Signature.getInstance("SHA1WithRSA");
            signer.initSign(privateKey);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
        this.type = String.format("RS%d", ((RSAPrivateKey) privateKey).getModulus().bitLength());
    }

    @Override
    public byte[] sign(String data) {
        try {
            signer.update(data.getBytes(StandardCharsets.UTF_8));
            return signer.sign();
        } catch (SignatureException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getType() {
        return type;
    }
}
