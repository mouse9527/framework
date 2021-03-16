package com.mouse.framework.jwt.verify;

import com.mouse.framework.jwt.JWTException;
import lombok.Generated;

import java.security.*;

public class RSAVerifier implements Verifier {
    private final Signature verifier;

    public RSAVerifier(PublicKey publicKey) {
        this.verifier = init(publicKey);
    }

    @Generated
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
    public boolean verify(JWTString jwt) {
        try {
            synchronized (verifier) {
                verifier.update(jwt.getSignatureContextBytes());
                return verifier.verify(jwt.getSignatureBytes());
            }
        } catch (SignatureException e) {
            return false;
        }
    }
}
