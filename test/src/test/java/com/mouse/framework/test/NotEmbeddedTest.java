package com.mouse.framework.test;

import com.mouse.framework.test.mongo.EmbeddedMongoDB;
import com.mouse.framework.test.redis.EmbeddedRedis;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import javax.annotation.Resource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

@SpringBootTest
class NotEmbeddedTest {
    @Resource
    private ApplicationContext applicationContext;

    @Test
    void should_be_able_to_not_has_redis_template_bean() {
        Throwable throwable = catchThrowable(() -> applicationContext.getBean(EmbeddedRedis.class));

        assertThat(throwable).isNotNull();

        Throwable mongoThrowable = catchThrowable(() -> applicationContext.getBean(EmbeddedMongoDB.class));

        assertThat(mongoThrowable).isNotNull();
    }
}
