package com.mouse.framework.test.redis;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@ExtendWith(RedisExtension.class)
@Import(EmbeddedRedisSelector.class)
public @interface EnableEmbeddedRedis {
}
