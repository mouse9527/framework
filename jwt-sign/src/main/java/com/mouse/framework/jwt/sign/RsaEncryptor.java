package com.mouse.framework.jwt.sign;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.util.Base64;

public class RsaEncryptor implements Encryptor {
    private final Cipher cipher;
    private final Base64.Encoder encoder;

    public RsaEncryptor(PrivateKey privateKey) {
        try {
            cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException e) {
            throw new JWTException(e);
        }
        this.encoder = Base64.getEncoder();
    }

    @Override
    public String encrypt(String raw) {
        byte[] src;
        try {
            synchronized (cipher) {
                src = cipher.doFinal(raw.getBytes(StandardCharsets.UTF_8));
            }
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            throw new JWTException(e);
        }
        return encoder.encodeToString(src);
    }
}
