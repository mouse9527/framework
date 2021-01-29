package com.mouse.framework.test.mongo;

import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.MongoDatabaseFactory;

@AutoConfigureBefore(name = "org.springframework.boot.autoconfigure.data.mongo.MongoDatabaseFactoryDependentConfiguration")
public class EmbeddedMongoDBConfiguration {
    @Bean
    @Primary
    public MongoDatabaseFactory mongoDatabaseFactory() {
        return EmbeddedMongoDB.getInstances().get(0).getMongoDatabaseFactory();
    }
}
