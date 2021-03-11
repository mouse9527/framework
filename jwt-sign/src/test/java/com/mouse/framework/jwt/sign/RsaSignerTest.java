package com.mouse.framework.jwt.sign;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

import static org.assertj.core.api.Assertions.assertThat;

public class RsaSignerTest {
    private static final String DATA = "data";
    private Signer signer;

    @BeforeEach
    void setUp() throws NoSuchAlgorithmException {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(1024);
        KeyPair keyPair = generator.generateKeyPair();
        signer = new RsaSigner(keyPair.getPrivate());
    }

    @Test
    void should_be_able_to_sign_signature() {

        byte[] signature = signer.sign(DATA);

        assertThat(signature).isNotEmpty();
        assertThat(signature).isNotEqualTo(DATA.getBytes(StandardCharsets.UTF_8));
        assertThat(signer.getType()).isEqualTo("RS1024");
    }
}
