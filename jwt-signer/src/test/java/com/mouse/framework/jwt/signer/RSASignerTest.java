package com.mouse.framework.jwt.signer;

import com.mouse.framework.jwt.Signer;
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

class RSASignerTest {

    private Signer signer;

    @BeforeEach
    void setUp() throws NoSuchAlgorithmException {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(1024);
        KeyPair keyPair = generator.generateKeyPair();
        signer = new RSASigner(keyPair.getPrivate());
    }

    @Test
    void should_be_able_to_sign_data() {
        byte[] signedData = signer.sign("raw");

        assertThat(signedData).isNotEmpty();
        assertThat(signedData).isNotEqualTo("raw".getBytes(StandardCharsets.UTF_8));
    }

    @Test
    void should_be_able_to_sign_with_multithreading_is_the_same_as_single_threading() throws InterruptedException {
        Map<String, byte[]> data = new ConcurrentHashMap<>();
        int count = 100;
        CountDownLatch countDownLatch = new CountDownLatch(count);

        List<Thread> threads = IntStream.range(0, count).mapToObj(i -> {
            String signData = String.format("data-%d", i);
            return new Thread(() -> {
                data.put(signData, signer.sign(signData));
                countDownLatch.countDown();
            });
        }).collect(Collectors.toList());
        threads.forEach(Thread::start);
        countDownLatch.await();

        assertThat(data.keySet().stream()).allMatch(key -> Arrays.equals(data.get(key), signer.sign(key)));
    }
}
