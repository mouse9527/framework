package com.mouse.framework.jwt.verify;

import com.mouse.framework.jwt.JWTException;

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

    public RSADecryptor(PublicKey publicKey) {
        try {
            cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, publicKey);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException e) {
            throw new JWTException(e);
        }
    }

    @Override
    public String decrypt(String encrypted) {
        byte[] bytes = Base64.getDecoder().decode(encrypted);
        try {
            return new String(cipher.doFinal(bytes));
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            throw new JWTException(e);
        }
    }
}
