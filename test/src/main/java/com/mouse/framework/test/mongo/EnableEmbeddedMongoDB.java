package com.mouse.framework.test.mongo;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@ExtendWith(MongoExtension.class)
@Import(EmbeddedMongoDBSelector.class)
public @interface EnableEmbeddedMongoDB {
}
