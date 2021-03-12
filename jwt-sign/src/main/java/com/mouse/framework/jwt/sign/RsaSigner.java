package com.mouse.framework.jwt.sign;

import com.mouse.framework.jwt.JWTException;
import lombok.Generated;

import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;

public class RsaSigner implements Signer {
    private final Signature signer;
    private final String type;

    public RsaSigner(PrivateKey privateKey) {
        this.signer = init(privateKey);
        this.type = String.format("RS%d", ((RSAPrivateKey) privateKey).getModulus().bitLength());
    }

    @Generated
    private Signature init(PrivateKey privateKey) {
        final Signature signer;
        try {
            signer = Signature.getInstance("SHA1WithRSA");
            signer.initSign(privateKey);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new JWTException(e);
        }
        return signer;
    }

    @Override
    public byte[] sign(String data) {
        byte[] bytes = data.getBytes(StandardCharsets.UTF_8);
        synchronized (signer) {
            return sign(bytes);
        }
    }

    @Generated
    private byte[] sign(byte[] bytes) {
        try {
            signer.update(bytes);
            return signer.sign();
        } catch (SignatureException e) {
            throw new JWTException(e);
        }
    }

    @Override
    public String getType() {
        return type;
    }
}
