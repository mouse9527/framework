package com.mouse.framework.jwt.sign;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

    @Test
    void should_be_able_to_sign_in_multithreading() throws InterruptedException {
        int count = 100;
        CountDownLatch countDownLatch = new CountDownLatch(count);
        Map<String, byte[]> data = new ConcurrentHashMap<>(count);

        List<Thread> threads = IntStream.range(0, count)
                .mapToObj(it -> new Thread(() -> {
                    String key = String.format("xx%d", it);
                    byte[] signature = signer.sign(key);
                    data.put(key, signature);
                    countDownLatch.countDown();
                })).collect(Collectors.toList());

        threads.forEach(Thread::start);

        countDownLatch.await();

        assertThat(data.values()).hasSize(count);
        assertThat(data.entrySet()).allMatch(entry -> Arrays.equals(entry.getValue(), signer.sign(entry.getKey())));
    }
}
