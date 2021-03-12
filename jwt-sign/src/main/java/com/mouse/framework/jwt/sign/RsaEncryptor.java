package com.mouse.framework.jwt.sign;

import com.mouse.framework.jwt.JWTException;
import lombok.Generated;

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
        this.cipher = init(privateKey);
        this.encoder = Base64.getEncoder();
    }

    @Generated
    private Cipher init(PrivateKey privateKey) {
        final Cipher cipher;
        try {
            cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException e) {
            throw new JWTException(e);
        }
        return cipher;
    }

    @Override
    public String encrypt(String raw) {
        byte[] src;
        byte[] bytes = raw.getBytes(StandardCharsets.UTF_8);
        synchronized (cipher) {
            src = encrypt(bytes);
        }
        return encoder.encodeToString(src);
    }

    @Generated
    private byte[] encrypt(byte[] data) {
        try {
            return cipher.doFinal(data);
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            throw new JWTException(e);
        }
    }
}
