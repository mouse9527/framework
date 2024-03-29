package com.mouse.framework.jwt.verify;

import com.google.common.base.Strings;
import com.mouse.framework.jwt.JWTException;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class JWTString {
    private static final int JWT_LENGTH = 3;
    private final String header;
    private final String payload;
    private final String signature;
    private final byte[] signatureBytes;

    public JWTString(String text) {
        if (Strings.isNullOrEmpty(text)) {
            throw new JWTException("error.illegal-token");
        }
        String[] split = text.split("\\.");
        if (split.length != JWT_LENGTH) {
            throw new JWTException("error.illegal-token");
        }
        this.header = split[0];
        this.payload = split[1];
        this.signature = split[2];
        try {
            this.signatureBytes = Base64.getDecoder().decode(this.signature);
        } catch (Exception e) {
            throw new JWTException("error.illegal-token", e);
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
