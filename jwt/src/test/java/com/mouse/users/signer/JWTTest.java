package com.mouse.users.signer;

import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class JWTTest {
    @Test
    void should_be_able_to_build_jwt() {
        String headerStr = "{\"typ\": \"JWT\",\"alg\": \"HS256\"}";
        String payloadStr = "{\"iat\": 1615269085,\"exp\": 1615269085,\"jti\": \"123456\"}";
        String signature = "mock-signature";
        Signer signer = mock(Signer.class);
        Encryptor encryptor = mock(Encryptor.class);
        Payload payload = Payload.builder().build(encryptor);
        Header header = new Header("JWT", "HS256");
        Mapper mapper = mock(Mapper.class);
        given(mapper.writeToBytes(header)).willReturn(headerStr.getBytes(StandardCharsets.UTF_8));
        given(mapper.writeToBytes(payload)).willReturn(payloadStr.getBytes(StandardCharsets.UTF_8));
        String raw = String.format("%s.%s", Base64.getEncoder().encodeToString(headerStr.getBytes(StandardCharsets.UTF_8)), Base64.getEncoder().encodeToString(payloadStr.getBytes(StandardCharsets.UTF_8)));
        given(signer.sign(raw)).willReturn(signature.getBytes(StandardCharsets.UTF_8));

        JWT jwt = JWT.builder(mapper)
                .header(header)
                .payload(payload)
                .sign(signer);

        assertThat(jwt.toString()).isNotEmpty();
        String[] split = jwt.toString().split("\\.");
        assertThat(split).hasSize(3);
        assertThat(split[0]).isEqualTo(Base64.getEncoder().encodeToString(headerStr.getBytes(StandardCharsets.UTF_8)));
        assertThat(split[1]).isEqualTo(Base64.getEncoder().encodeToString(payloadStr.getBytes(StandardCharsets.UTF_8)));
        assertThat(split[2]).isEqualTo(Base64.getEncoder().encodeToString(signature.getBytes(StandardCharsets.UTF_8)));
    }
}
