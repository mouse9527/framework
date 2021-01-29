package com.mouse.framework.test.mongo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;

import javax.annotation.Resource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@SpringBootTest
@EnableEmbeddedMongoDB
class EmbeddedMongoDBTest {
    @Resource
    private MongoTemplate mongoTemplate;

    @Test
    void should_be_able_to_create_mongo_replica_set() {
        TestEntity entity = new TestEntity();
        entity.id = "test-id";
        entity.name = "test-name";
        mongoTemplate.save(entity, "test-entries");

        TestEntity fromMongo = mongoTemplate.findOne(query(where("_id").is("test-id")), TestEntity.class, "test-entries");

        assertThat(fromMongo).isNotNull();
        assertThat(fromMongo.id).isEqualTo(entity.id);
        assertThat(fromMongo.name).isEqualTo(entity.name);
    }

    static class TestEntity {
        private String id;
        private String name;
    }
}
