package com.mouse.framework.test.mongo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

@ExtendWith(MongoExtension.class)
public class MongoExtensionTest {

    @Test
    void should_be_able_to_create_mongo_container_once() {
        List<EmbeddedMongoDB> mongoDBs = EmbeddedMongoDB.getInstances();

        assertThat(mongoDBs).hasSize(1);
        Throwable throwable = catchThrowable(() -> new MongoTemplate(mongoDBs.get(0).getMongoDatabaseFactory()).getCollection("test"));
        assertThat(throwable).isNull();
    }
}

@ExtendWith(MongoExtension.class)
class MongoExtensionRepeatTest {

    @Test
    void should_be_able_to_create_mongo_container_once() {
        List<EmbeddedMongoDB> mongoDBs = EmbeddedMongoDB.getInstances();

        assertThat(mongoDBs).hasSize(1);
        Throwable throwable = catchThrowable(() -> new MongoTemplate(mongoDBs.get(0).getMongoDatabaseFactory()).getCollection("test"));
        assertThat(throwable).isNull();
    }
}
