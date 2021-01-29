package com.mouse.framework.test.mongo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class MultipleMongoTest {

    @Test
    void should_be_able_to_start_embedded_mongo_once() {
        assertThat(EmbeddedMongoDB.startTimes()).isEqualTo(1);
    }
}
