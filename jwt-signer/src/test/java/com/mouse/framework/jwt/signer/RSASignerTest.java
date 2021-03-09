package com.mouse.framework.jwt.signer;

import com.mouse.framework.jwt.Signer;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

import static org.assertj.core.api.Assertions.assertThat;

class RSASignerTest {
    @Test
    void should_be_able_to_sign_data() throws NoSuchAlgorithmException {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(1024);
        KeyPair keyPair = generator.generateKeyPair();
        Signer signer = new RSASigner(keyPair.getPrivate());

        byte[] signedData = signer.sign("raw");

        assertThat(signedData).isNotEmpty();
        assertThat(signedData).isNotEqualTo("raw".getBytes(StandardCharsets.UTF_8));
    }
}
