package com.mouse.framework.jwt.signer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mouse.framework.jwt.Header;
import com.mouse.framework.jwt.Mapper;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

public class JacksonMapperTest {
    @Test
    void should_be_able_to_be_able_to_write_to_bytes() {
        Mapper mapper = new JacksonMapper(new ObjectMapper());

        assertThat(mapper.writeToBytes(new Header("JWT", "HS256"))).isEqualTo("{\"typ\":\"JWT\",\"alg\":\"HS256\"}".getBytes(StandardCharsets.UTF_8));
    }
}
