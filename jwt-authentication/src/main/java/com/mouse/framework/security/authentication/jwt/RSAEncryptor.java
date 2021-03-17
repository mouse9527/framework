package com.mouse.framework.security.authentication.jwt;

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

public class RSAEncryptor implements Encryptor {
    private final Cipher cipher;
    private final Base64.Encoder encoder;

    public RSAEncryptor(PrivateKey privateKey, String transformation) {
        this.cipher = init(privateKey, transformation);
        this.encoder = Base64.getEncoder();
    }

    @Generated
    private Cipher init(PrivateKey privateKey, String transformation) {
        try {
            Cipher cipher = Cipher.getInstance(transformation);
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            return cipher;
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException e) {
            throw new JWTException(e);
        }
    }

    @Override
    public String encrypt(String raw) {
        return encoder.encodeToString(syncEncrypt(raw.getBytes(StandardCharsets.UTF_8)));
    }

    @Generated
    private byte[] syncEncrypt(byte[] bytes) {
        try {
            synchronized (cipher) {
                return cipher.doFinal(bytes);
            }
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            throw new JWTException(e);
        }
    }

}
