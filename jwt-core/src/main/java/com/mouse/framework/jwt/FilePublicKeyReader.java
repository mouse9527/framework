package com.mouse.framework.jwt;

import lombok.Generated;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FilePublicKeyReader implements PublicKeyReader {
    private final PublicKey publicKey;

    @Generated
    public FilePublicKeyReader(InputStream inputStream) {
        try (BufferedReader reader = getReader(inputStream)) {
            publicKey = getPrivateKey(reader.lines());
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    private BufferedReader getReader(InputStream inputStream) {
        InputStreamReader in = new InputStreamReader(inputStream);
        return new BufferedReader(in);
    }

    private PublicKey getPrivateKey(Stream<String> lines) throws NoSuchAlgorithmException, InvalidKeySpecException {
        String data = lines
                .collect(Collectors.joining())
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s", "");
        KeyFactory factory = KeyFactory.getInstance("RSA");
        return factory.generatePublic(new X509EncodedKeySpec(Base64.getDecoder().decode(data)));
    }

    @Override
    public PublicKey read() {
        return publicKey;
    }
}
