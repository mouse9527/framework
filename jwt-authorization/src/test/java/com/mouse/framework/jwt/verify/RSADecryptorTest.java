package com.mouse.framework.jwt.verify;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

class RSADecryptorTest {
    private static final String RAW = "xxx12fghjklkbjvhgjhk123";
    private Cipher cipher;
    private Decryptor decryptor;

    @BeforeEach
    void setUp() throws Exception {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(1024);
        KeyPair keyPair = generator.generateKeyPair();
        decryptor = new RSADecryptor(keyPair.getPublic(), "RSA");
        cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, keyPair.getPrivate());
    }

    @Test
    void should_be_able_to_decrypt_encrypted_data() {
        String encrypted = getEncrypted(RAW);

        String raw = decryptor.decrypt(encrypted);

        assertThat(raw).isEqualTo(RAW);
    }

    private String getEncrypted(String data) {
        try {
            return Base64.getEncoder().encodeToString(cipher.doFinal(data.getBytes(StandardCharsets.UTF_8)));
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void should_be_able_to_decrypt_in_multithreading() throws InterruptedException {
        int count = 100;
        Map<String, String> data = new ConcurrentHashMap<>(count);
        CountDownLatch countDownLatch = new CountDownLatch(count);
        List<Thread> threads = IntStream.range(0, count)
                .mapToObj(i -> {
                    String key = String.format("data-%d", i);
                    return Pair.of(key, getEncrypted(key));
                }).map(pair -> new Thread(() -> {
                    String decrypt = decryptor.decrypt(pair.getValue());
                    data.put(pair.getKey(), decrypt);
                    countDownLatch.countDown();
                })).collect(Collectors.toList());

        threads.forEach(Thread::start);

        countDownLatch.await();
        assertThat(data.entrySet()).anyMatch(entry -> entry.getKey().equals(entry.getValue()));
    }
}
