package com.mouse.framework.jwt;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class JWTTest {
    public static final String HEADER_STR = "{\"typ\": \"JWT\",\"alg\": \"HS256\"}";
    public static final String PAYLOAD_STR = "{\"iat\": 1615269085,\"exp\": 1615269085,\"jti\": \"123456\"}";
    public static final String SIGNATURE = "mock-signature";
    public static final String RAW = String.format("%s.%s", Base64.getEncoder().encodeToString(HEADER_STR.getBytes(StandardCharsets.UTF_8)), Base64.getEncoder().encodeToString(PAYLOAD_STR.getBytes(StandardCharsets.UTF_8)));
    private Signer signer;
    private Mapper mapper;

    @BeforeEach
    void setUp() {
        signer = mock(Signer.class);
        given(signer.sign(RAW)).willReturn(SIGNATURE.getBytes(StandardCharsets.UTF_8));

        mapper = mock(Mapper.class);
    }

    @Test
    void should_be_able_to_build_jwt() {
        Encryptor encryptor = mock(Encryptor.class);
        Payload payload = Payload.builder().build(encryptor);
        Header header = new Header("JWT", "HS256");
        given(mapper.writeToBytes(header)).willReturn(HEADER_STR.getBytes(StandardCharsets.UTF_8));
        given(mapper.writeToBytes(payload)).willReturn(PAYLOAD_STR.getBytes(StandardCharsets.UTF_8));

        JWT jwt = JWT.builder(mapper)
                .header(header)
                .payload(payload)
                .sign(signer);

        assertJWT(jwt);
    }

    private void assertJWT(JWT jwt) {
        assertThat(jwt.toString()).isNotEmpty();
        String[] split = jwt.toString().split("\\.");
        assertThat(split).hasSize(3);
        assertThat(split[0]).isEqualTo(Base64.getEncoder().encodeToString(HEADER_STR.getBytes(StandardCharsets.UTF_8)));
        assertThat(split[1]).isEqualTo(Base64.getEncoder().encodeToString(PAYLOAD_STR.getBytes(StandardCharsets.UTF_8)));
        assertThat(split[2]).isEqualTo(Base64.getEncoder().encodeToString(SIGNATURE.getBytes(StandardCharsets.UTF_8)));
        assertThat(split[0]).isEqualTo(jwt.getHeader());
        assertThat(split[1]).isEqualTo(jwt.getPayload());
        assertThat(split[2]).isEqualTo(jwt.getSignature());
    }

    @Test
    void should_be_able_to_parse_from_jwt_string() {
        String text = String.format("%s.%s", RAW, Base64.getEncoder().encodeToString(SIGNATURE.getBytes(StandardCharsets.UTF_8)));

        JWT jwt = JWT.parse(text);

        assertJWT(jwt);
    }
}
