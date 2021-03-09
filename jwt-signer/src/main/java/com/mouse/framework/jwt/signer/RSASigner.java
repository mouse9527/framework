package com.mouse.framework.jwt.signer;

import com.mouse.framework.jwt.Signer;

import java.nio.charset.StandardCharsets;
import java.security.*;

public class RSASigner implements Signer {
    private final Signature signer;

    public RSASigner(PrivateKey privateKey) {
        try {
            signer = Signature.getInstance("SHA1WithRSA");
            signer.initSign(privateKey);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new IllegalArgumentException("Failed init RSASigner", e);
        }
    }

    @Override
    public byte[] sign(String data) {
        try {
            signer.update(data.getBytes(StandardCharsets.UTF_8));
            return signer.sign();
        } catch (SignatureException e) {
            // do nothing
            throw new RuntimeException("Failed sign with RSASigner", e);
        }
    }
}
