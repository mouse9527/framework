package com.mouse.framework.sequence.snowflake;

import com.mouse.framework.domain.core.SequenceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class SequenceServiceTest {
    private SequenceService sequenceService;

    private static Stream<Long> illegalWorkId() {
        return Stream.of(-1L, 1024L, 1025L);
    }

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
    void should_be_able_to_create_next_srt() {
        String id = sequenceService.nextStr();

        assertThat(id).isNotEmpty();
        assertThat(Long.valueOf(id)).isGreaterThan(0);
    }

    @Test
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

    @ParameterizedTest
    @MethodSource("illegalWorkId")
    void should_be_able_to_raise_exception_with_illegal_work_id(Long illegalWorkId) {
        Throwable throwable = catchThrowable(() -> new SnowFlakeSequenceService(illegalWorkId, new SnowFlakeProperties()));

        assertThat(throwable).isNotNull();
        assertThat(throwable).isInstanceOf(IllegalWorkerIdException.class);
        assertThat(throwable).hasMessage(String.format("Illegal workerId %d, range: [0 ~ 1023]", illegalWorkId));
    }
}
