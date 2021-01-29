package com.mouse.framework.test.mongo;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(EmbeddedMongoDBConfiguration.class)
public @interface EnableEmbeddedMongoDB {
}
