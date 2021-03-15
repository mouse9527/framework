package com.mouse.framework.jwt.verify;

import com.mouse.framework.jwt.sign.RSASigner;
import com.mouse.framework.jwt.sign.Signer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import static org.assertj.core.api.Assertions.assertThat;

public class RSAVerifierTest {

    private static final String CONTEXT = "xxx.xxxx";
    private Verifier verifier;
    private Signer signer;

    @BeforeEach
    void setUp() throws NoSuchAlgorithmException {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(1024);
        KeyPair keyPair = generator.generateKeyPair();
        signer = new RSASigner(keyPair.getPrivate());
        verifier = new RSAVerifier(keyPair.getPublic());
    }

    @Test
    void should_be_able_to_verify_rsa_signature() {
        String signature = Base64.getEncoder().encodeToString(signer.sign(CONTEXT));

        assertThat(verifier.verify(new JWTString(String.format("%s.%s", CONTEXT, signature)))).isTrue();
    }

    @Test
    void should_be_able_to_failed_to_verify_illegal_text() {
        String signature = Base64.getEncoder().encodeToString("illegal-signature".getBytes(StandardCharsets.UTF_8));

        assertThat(verifier.verify(new JWTString(String.format("%s.%s", CONTEXT, signature)))).isFalse();
    }
}
