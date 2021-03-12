package com.mouse.framework.jwt.verify;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mouse.framework.domain.core.Token;
import com.mouse.framework.jwt.JWT;
import com.mouse.framework.jwt.Payload;
import com.mouse.framework.security.TokenParser;

import java.util.Base64;

public class JWTTokenParser implements TokenParser {
    private static final int JWT_LENGTH = 3;
    private final Decryptor decryptor;
    private final ObjectMapper objectMapper;
    private final Base64.Decoder decoder;

    public JWTTokenParser(Decryptor decryptor, ObjectMapper objectMapper) {
        this.decryptor = decryptor;
        this.objectMapper = objectMapper;
        this.decoder = Base64.getDecoder();
    }

    @Override
    public Token parse(String text) {
        Payload payload = parsePayload(text);
        return new JWT(payload, decryptor.decrypt(payload.getCip()));
    }

    private Payload parsePayload(String text) {
        try {
            String[] split = text.split("\\.");
            if (split.length != JWT_LENGTH) throw new IllegalTokenException();
            return objectMapper.readValue(decoder.decode(split[1]), Payload.class);
        } catch (Exception e) {
            throw new IllegalTokenException(e);
        }
    }
}
