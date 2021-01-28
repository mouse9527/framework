package com.mouse.framework.test.redis;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(EmbeddedRedisConfiguration.class)
public @interface EnableEmbeddedRedis {
}
