package com.mouse.framework.security.authentication.jwt;

import com.mouse.framework.jwt.JWTException;
import lombok.Generated;

import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;

public class RSASigner implements Signer {
    private final Signature signer;
    private final String type;

    public RSASigner(PrivateKey privateKey, String algorithm) {
        this.signer = init(privateKey, algorithm);
        this.type = String.format("RS%d", ((RSAPrivateKey) privateKey).getModulus().bitLength());
    }

    @Generated
    private Signature init(PrivateKey privateKey, String algorithm) {
        try {
            Signature signer = Signature.getInstance(algorithm);
            signer.initSign(privateKey);
            return signer;
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new JWTException(e);
        }
    }

    @Override
    public byte[] sign(String data) {
        return syncSign(data.getBytes(StandardCharsets.UTF_8));
    }

    @Generated
    private byte[] syncSign(byte[] bytes) {
        try {
            synchronized (signer) {
                signer.update(bytes);
                return signer.sign();
            }
        } catch (SignatureException e) {
            throw new JWTException(e);
        }
    }

    @Override
    public String getType() {
        return type;
    }
}
