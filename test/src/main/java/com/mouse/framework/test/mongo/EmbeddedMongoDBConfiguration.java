package com.mouse.framework.test.mongo;

import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@AutoConfigureBefore(name = "org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration")
@EnableConfigurationProperties(EmbeddedMongoDBProperties.class)
public class EmbeddedMongoDBConfiguration {

    @Bean
    @Primary
    public MongoProperties mongoProperties(EmbeddedMongoDB embeddedMongoDB) {
        MongoProperties mongoProperties = new MongoProperties();
        mongoProperties.setUri(embeddedMongoDB.getReplicaSetUrl());
        return mongoProperties;
    }

    @Bean(destroyMethod = "stop")
    public EmbeddedMongoDB embeddedMongoDB(EmbeddedMongoDBProperties properties) {
        EmbeddedMongoDB mongoDB = new EmbeddedMongoDB(properties);
        mongoDB.start();
        return mongoDB;
    }
}

