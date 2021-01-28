package com.mouse.framework.sequence.core;

import com.mouse.framework.sequence.core.snowflake.SnowFlakeProperties;
import com.mouse.framework.sequence.core.snowflake.SnowFlakeSequenceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

class SequenceServiceTest {
    private SequenceService sequenceService;

    @BeforeEach
    void setUp() {
        SnowFlakeProperties properties = new SnowFlakeProperties();
        sequenceService = new SnowFlakeSequenceService(1L, properties);
    }

    @Test
    void should_be_able_to_create_next_seq() {
        Long id = sequenceService.next();

        assertThat(id).isNotNull();
        assertThat(id).isGreaterThan(0);
    }

    @Test
    @Timeout(1)
    void should_be_able_to_create_many_in_second() throws InterruptedException {
        Map<Long, Integer> ids = new ConcurrentHashMap<>();
        int count = 10000;
        CountDownLatch countDown = new CountDownLatch(count);

        IntStream.range(0, count).forEach(i -> new Thread(() -> {
            Long id = sequenceService.next();
            ids.put(id, i);
            countDown.countDown();
        }).start());
        countDown.await();

        assertThat(ids.keySet()).hasSize(count);
    }
}
