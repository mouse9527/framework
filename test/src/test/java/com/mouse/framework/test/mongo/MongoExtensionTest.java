package com.mouse.framework.test.mongo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.data.mongodb.core.MongoTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

@ExtendWith(MongoExtension.class)
public class MongoExtensionTest {

    @Test
    void should_be_able_to_create_mongo_container_once() {
        assertThat(EmbeddedMongoDB.startTimes()).isEqualTo(1);
        Throwable throwable = catchThrowable(() -> new MongoTemplate(EmbeddedMongoDB.get().getMongoDatabaseFactory()).getCollection("test"));
        assertThat(throwable).isNull();
    }
}

@ExtendWith(MongoExtension.class)
class MongoExtensionRepeatTest {

    @Test
    void should_be_able_to_create_mongo_container_once() {
        assertThat(EmbeddedMongoDB.startTimes()).isEqualTo(1);
        Throwable throwable = catchThrowable(() -> new MongoTemplate(EmbeddedMongoDB.get().getMongoDatabaseFactory()).getCollection("test"));
        assertThat(throwable).isNull();
    }
}
