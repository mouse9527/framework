package com.mouse.framework.jwt.verify;

import com.mouse.framework.jwt.JWTException;

import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Base64;

public class RSAVerifier implements Verifier {
    private final Signature verifier;

    public RSAVerifier(PublicKey publicKey) {
        this.verifier = init(publicKey);
    }

    private Signature init(PublicKey publicKey) {
        try {
            Signature signature = Signature.getInstance("SHA1WithRSA");
            signature.initVerify(publicKey);
            return signature;
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new JWTException(e);
        }
    }

    @Override
    public boolean verify(String text) {
        String[] split = text.split("\\.");
        byte[] signature = Base64.getDecoder().decode(split[2]);
        try {
            verifier.update(String.format("%s.%s", split[0], split[1]).getBytes(StandardCharsets.UTF_8));
            return verifier.verify(signature);
        } catch (SignatureException e) {
            return false;
        }
    }

    @Override
    public boolean verify(JWTString jwt) {
        try {
            verifier.update(jwt.getSignatureContextBytes());
            return verifier.verify(jwt.getSignatureBytes());
        } catch (SignatureException e) {
            return false;
        }
    }
}
