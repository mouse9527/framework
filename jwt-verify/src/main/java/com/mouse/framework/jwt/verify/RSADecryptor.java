package com.mouse.framework.jwt.verify;

import com.mouse.framework.jwt.JWTException;
import lombok.Generated;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.util.Base64;

public class RSADecryptor implements Decryptor {
    private final Cipher cipher;

    public RSADecryptor(PublicKey publicKey, String transformation) {
        this.cipher = init(publicKey, transformation);
    }

    @Generated
    private Cipher init(PublicKey publicKey, String transformation) {
        try {
            Cipher cipher = Cipher.getInstance(transformation);
            cipher.init(Cipher.DECRYPT_MODE, publicKey);
            return cipher;
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException e) {
            throw new JWTException(e);
        }
    }

    @Override
    public String decrypt(String encrypted) {
        return new String(decrypt(Base64.getDecoder().decode(encrypted)));
    }

    @Generated
    private byte[] decrypt(byte[] bytes) {
        try {
            synchronized (cipher) {
                return cipher.doFinal(bytes);
            }
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            throw new JWTException(e);
        }
    }
}
