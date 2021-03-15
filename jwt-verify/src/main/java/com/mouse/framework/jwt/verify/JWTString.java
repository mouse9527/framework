package com.mouse.framework.jwt.verify;

import com.google.common.base.Strings;
import com.mouse.framework.security.IllegalTokenException;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class JWTString {
    private static final int TOKEN_LENGH = 3;
    private final String header;
    private final String payload;
    private final String signature;
    private final byte[] signatureBytes;

    public JWTString(String text) {
        if (Strings.isNullOrEmpty(text)) {
            throw new IllegalTokenException();
        }
        String[] split = text.split("\\.");
        if (split.length != TOKEN_LENGH) {
            throw new IllegalTokenException();
        }
        this.header = split[0];
        this.payload = split[1];
        this.signature = split[2];
        try {
            this.signatureBytes = Base64.getDecoder().decode(this.signature);
        } catch (Exception e) {
            throw new IllegalTokenException(e);
        }
    }

    public String getHeader() {
        return header;
    }

    public String getPayload() {
        return payload;
    }

    public String getSignature() {
        return signature;
    }

    public byte[] getSignatureBytes() {
        return signatureBytes;
    }

    public String getSignatureContext() {
        return String.format("%s.%s", header, payload);
    }

    public byte[] getSignatureContextBytes() {
        return getSignatureContext().getBytes(StandardCharsets.UTF_8);
    }
}
