package com.mouse.framework.test;

import lombok.Generated;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;

@Generated
@Configuration
@EnableAutoConfiguration
@AutoConfigureBefore(name = "org.springframework.boot.autoconfigure.data.mongo.MongoDatabaseFactoryDependentConfiguration")
public class EmbeddedMongoDBConfiguration {
    @Value("${application.test.embedded-mongo.image:mongo:4.4.0}")
    private String mongoDockerImageName;

    @Bean
    public MongoDatabaseFactory mongoDatabaseFactory(EmbeddedMongoDB embeddedMongoDB) {
        return embeddedMongoDB.getMongoDatabaseFactory();
    }

    @Bean(destroyMethod = "stop")
    public EmbeddedMongoDB embeddedMongoDB() {
        return EmbeddedMongoDB.getInstance(mongoDockerImageName);
    }
}
