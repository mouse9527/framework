package com.mouse.framework.jwt.verify;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class JWTString {
    private final String header;
    private final String payload;
    private final String signature;
    private final Base64.Decoder decoder;

    public JWTString(String text) {
        String[] split = text.split("\\.");
        this.header = split[0];
        this.payload = split[1];
        this.signature = split[2];
        this.decoder = Base64.getDecoder();
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
        return decoder.decode(this.signature);
    }

    public String getSignatureContext() {
        return String.format("%s.%s", header, payload);
    }

    public byte[] getSignatureContextBytes() {
        return getSignatureContext().getBytes(StandardCharsets.UTF_8);
    }
}
