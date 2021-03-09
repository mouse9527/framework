package com.mouse.framework.jwt.signer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mouse.framework.jwt.Mapper;

public class JacksonMapper implements Mapper {
    public JacksonMapper(ObjectMapper objectMapper) {

    }

    @Override
    public byte[] writeToBytes(Object data) {
        return new byte[0];
    }
}
