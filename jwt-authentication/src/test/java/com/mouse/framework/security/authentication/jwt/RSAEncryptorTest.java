package com.mouse.framework.security.authentication.jwt;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

class RSAEncryptorTest {
    private RSAEncryptor encryptor;
    private Cipher decrypt;

    @BeforeEach
    void setUp() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(1024);
        KeyPair keyPair = generator.generateKeyPair();
        encryptor = new RSAEncryptor(keyPair.getPrivate(), "RSA");
        decrypt = Cipher.getInstance("RSA");
        decrypt.init(Cipher.DECRYPT_MODE, keyPair.getPublic());
    }

    @Test
    void should_be_able_to_encrypt_data() {
        String rawData = "1234567890";

        String encrypted = encryptor.encrypt(rawData);

        System.out.println(encrypted);
        assertThat(encrypted).isNotEmpty();
        assertThat(encrypted).isNotEqualTo(rawData);
        assertThat(decrypt(encrypted)).isEqualTo(rawData);
    }

    private String decrypt(String encrypted) {
        try {
            return new String(decrypt.doFinal(Base64.getDecoder().decode(encrypted)));
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void should_be_able_to_work_in_multithreading() throws InterruptedException {
        int count = 100;
        CountDownLatch countDownLatch = new CountDownLatch(count);
        Map<String, String> data = new ConcurrentHashMap<>();
        List<Thread> threads = IntStream.range(0, count).mapToObj(it -> new Thread(() -> {
            String raw = String.format("xx%d", it);
            String encrypted = encryptor.encrypt(raw);
            data.put(raw, encrypted);
            countDownLatch.countDown();
        })).collect(Collectors.toList());

        threads.forEach(Thread::start);

        countDownLatch.await();

        assertThat(data.values()).hasSize(count);
        assertThat(data.entrySet()).allMatch(entry -> entry.getKey().equals(decrypt(entry.getValue())));
    }
}
