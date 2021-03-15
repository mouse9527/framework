package com.mouse.framework.jwt.verify;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mouse.framework.domain.core.Token;
import com.mouse.framework.jwt.JWT;
import com.mouse.framework.jwt.Payload;
import com.mouse.framework.security.IllegalTokenException;
import com.mouse.framework.security.TokenParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Base64;
import java.util.Optional;

public class JWTTokenParser implements TokenParser {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
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
    public Optional<Token> parse(String text) {
        JWTString jwt;
        try {
            jwt = new JWTString(text);
        } catch (Exception e) {
            logger.warn("Illegal token: {}", text, e);
            return Optional.empty();
        }
        if (!verifier.verify(jwt)) {
            return Optional.empty();
        }
        Payload payload = parsePayload(jwt.getPayload());
        return Optional.of(new JWT(payload, decryptor.decrypt(payload.getCip())));
    }

    private Payload parsePayload(String payload) {
        try {
            return objectMapper.readValue(decoder.decode(payload), Payload.class);
        } catch (Exception e) {
            logger.error("Failed to read jwt.payload", e);
            throw new IllegalTokenException(e);
        }
    }
}
