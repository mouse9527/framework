package com.mouse.framework.test.redis;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({EmbeddedRedisSelector.class, EmbeddedRedisPropertiesConfiguration.class})
public @interface EnableEmbeddedRedis {
}
