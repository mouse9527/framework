package com.mouse.framework.jwt.sign;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mouse.framework.domain.core.Authority;
import com.mouse.framework.domain.core.Token;
import com.mouse.framework.jwt.Header;
import com.mouse.framework.jwt.Payload;
import com.mouse.framework.security.TokenFormat;
import lombok.Generated;

import java.util.Base64;
import java.util.stream.Collectors;

public class JWTFormat implements TokenFormat {
    private final Encryptor encryptor;
    private final ObjectMapper objectMapper;
    private final Signer signer;
    private final Base64.Encoder encoder;

    public JWTFormat(Encryptor encryptor, Signer signer, ObjectMapper objectMapper) {
        this.encryptor = encryptor;
        this.signer = signer;
        this.objectMapper = objectMapper;
        this.encoder = Base64.getEncoder();
    }

    public String format(Token token) {
        Header header = new Header("JWT", signer.getType());
        Payload payload = Payload.builder()
                .jwtId(token.getId())
                .issuedAt(token.getIssuedAt().getEpochSecond())
                .expirationTime(token.getExpirationTime().getEpochSecond())
                .name(token.getUser().getUsername())
                .ciphertext(encryptor.encrypt(token.getUser().getId()))
                .authorities(token.getAuthorities().getAuthorities().stream().map(Authority::getAuthority).collect(Collectors.toList()))
                .build();
        String headerStr = encoder.encodeToString(writeValueAsBytes(header));
        String payloadStr = encoder.encodeToString(writeValueAsBytes(payload));
        String part = String.format("%s.%s", headerStr, payloadStr);
        String signature = encoder.encodeToString(signer.sign(part));
        return String.format("%s.%s", part, signature);
    }

    @Generated
    private byte[] writeValueAsBytes(Object value) {
        try {
            return objectMapper.writeValueAsBytes(value);
        } catch (JsonProcessingException e) {
            throw new JWTException(e);
        }
    }
}
