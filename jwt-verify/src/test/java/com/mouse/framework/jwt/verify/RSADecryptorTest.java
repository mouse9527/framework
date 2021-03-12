package com.mouse.framework.jwt.verify;

import org.junit.jupiter.api.Test;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.util.Base64;

import static org.assertj.core.api.Assertions.assertThat;

class RSADecryptorTest {
    private static final String RAW = "xxx12fghjklkbjvhgjhk123";

    @Test
    void should_be_able_to_decrypt_encrypted_data() throws Exception {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(1024);
        KeyPair keyPair = generator.generateKeyPair();
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, keyPair.getPrivate());
        String encrypted = Base64.getEncoder().encodeToString(cipher.doFinal(RAW.getBytes(StandardCharsets.UTF_8)));
        Decryptor decryptor = new RSADecryptor(keyPair.getPublic());

        String raw = decryptor.decrypt(encrypted);

        assertThat(raw).isEqualTo(RAW);
    }
}
