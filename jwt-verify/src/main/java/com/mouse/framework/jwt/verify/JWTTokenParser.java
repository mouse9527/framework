package com.mouse.framework.jwt.verify;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mouse.framework.domain.core.Token;
import com.mouse.framework.jwt.JWT;
import com.mouse.framework.jwt.JWTException;
import com.mouse.framework.jwt.Payload;
import com.mouse.framework.security.TokenParser;

import java.io.IOException;
import java.util.Base64;

public class JWTTokenParser implements TokenParser {
    private final Decryptor decryptor;
    private final ObjectMapper objectMapper;

    public JWTTokenParser(Decryptor decryptor, ObjectMapper objectMapper) {
        this.decryptor = decryptor;
        this.objectMapper = objectMapper;
    }

    @Override
    public Token parse(String text) {
        Payload payload;
        try {
            payload = objectMapper.readValue(Base64.getDecoder().decode(text.split("\\.")[1]), Payload.class);
        } catch (IOException e) {
            throw new JWTException(e);
        }
        return new JWT(payload, decryptor.decrypt(payload.getCip()));
    }
}
