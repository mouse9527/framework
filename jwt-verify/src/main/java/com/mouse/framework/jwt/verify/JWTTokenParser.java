package com.mouse.framework.jwt.verify;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mouse.framework.domain.core.Token;
import com.mouse.framework.jwt.JWT;
import com.mouse.framework.jwt.Payload;
import com.mouse.framework.security.IllegalTokenException;
import com.mouse.framework.security.TokenParser;

import java.util.Base64;

public class JWTTokenParser implements TokenParser {
    private final Decryptor decryptor;
    private final Verifier verifier;
    private final ObjectMapper objectMapper;
    private final Base64.Decoder decoder;

    public JWTTokenParser(Decryptor decryptor, Verifier verifier, ObjectMapper objectMapper) {
        this.decryptor = decryptor;
        this.verifier = verifier;
        this.objectMapper = objectMapper;
        this.decoder = Base64.getDecoder();
    }

    @Override
    public Token parse(String text) throws IllegalTokenException {
        JWTString jwt = new JWTString(text);
        if (!verifier.verify(jwt)) {
            throw new IllegalTokenException();
        }
        Payload payload = parsePayload(jwt.getPayload());
        return new JWT(payload, decryptor.decrypt(payload.getCip()));
    }

    private Payload parsePayload(String payload) {
        try {
            return objectMapper.readValue(decoder.decode(payload), Payload.class);
        } catch (Exception e) {
            throw new IllegalTokenException(e);
        }
    }
}
