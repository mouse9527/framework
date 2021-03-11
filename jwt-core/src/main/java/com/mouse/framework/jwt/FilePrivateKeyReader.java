package com.mouse.framework.jwt;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FilePrivateKeyReader implements PrivateKeyReader {
    private final PrivateKey privateKey;

    public FilePrivateKeyReader(InputStream inputStream) {
        try (BufferedReader reader = getReader(inputStream)) {
            privateKey = getPrivateKey(reader.lines());
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    private BufferedReader getReader(InputStream inputStream) {
        InputStreamReader in = new InputStreamReader(inputStream);
        return new BufferedReader(in);
    }

    private PrivateKey getPrivateKey(Stream<String> lines) throws NoSuchAlgorithmException, InvalidKeySpecException {
        String privateKeyString = lines
                .collect(Collectors.joining())
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replaceAll("\\s", "");
        KeyFactory factory = KeyFactory.getInstance("RSA");
        return factory.generatePrivate(new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKeyString)));
    }

    @Override
    public PrivateKey read() {
        return privateKey;
    }
}
