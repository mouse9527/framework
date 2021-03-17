package com.mouse.framework.jwt.verify;

import com.mouse.framework.security.authentication.jwt.RSASigner;
import com.mouse.framework.security.authentication.jwt.Signer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
        signer = new RSASigner(keyPair.getPrivate(), "SHA1WithRSA");
        verifier = new RSAVerifier(keyPair.getPublic(), "SHA1WithRSA");
    }

    @Test
    void should_be_able_to_verify_rsa_signature() {
        JWTString jwt = getJWTString(CONTEXT);

        assertThat(verifier.verify(jwt)).isTrue();
    }

    private JWTString getJWTString(String context) {
        String signature = Base64.getEncoder().encodeToString(signer.sign(context));
        return new JWTString(String.format("%s.%s", context, signature));
    }

    @Test
    void should_be_able_to_failed_to_verify_illegal_text() {
        String signature = Base64.getEncoder().encodeToString("illegal-signature".getBytes(StandardCharsets.UTF_8));

        assertThat(verifier.verify(new JWTString(String.format("%s.%s", CONTEXT, signature)))).isFalse();
    }

    @Test
    void should_be_able_to_verify_in_multithreading() throws InterruptedException {
        int count = 100;
        List<Boolean> results = Collections.synchronizedList(new ArrayList<>(count));
        CountDownLatch countDownLatch = new CountDownLatch(count);
        List<Thread> threads = IntStream.range(0, count).mapToObj(it -> new Thread(() -> {
            results.add(verifier.verify(getJWTString(String.format("xx%s.xx%s", it, it))));
            countDownLatch.countDown();
        })).collect(Collectors.toList());

        threads.forEach(Thread::start);

        countDownLatch.await();
        assertThat(results).allMatch(Boolean::booleanValue);
    }
}
