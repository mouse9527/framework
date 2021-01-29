package com.mouse.framework.test.mongo;

import lombok.Generated;
import lombok.Setter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
@ConditionalOnClass(MongoTemplate.class)
@AutoConfigureBefore(MongoAutoConfiguration.class)
@EnableConfigurationProperties(EmbeddedMongoDBConfiguration.EmbeddedMongoDBProperties.class)
@ConditionalOnProperty(value = "test.embedded.mongodb.enable", havingValue = "true")
public class EmbeddedMongoDBConfiguration {

    @Bean
    @Primary
    public MongoProperties mongoProperties(EmbeddedMongoDBProperties properties) {
        MongoProperties mongoProperties = new MongoProperties();
        EmbeddedMongoDB embeddedMongoDB = EmbeddedMongoDB.getInstance(properties.getImage());
        mongoProperties.setUri(embeddedMongoDB.getReplicaSetUrl());
        return mongoProperties;
    }

    @Setter
    @Generated
    @ConfigurationProperties(prefix = "test.embedded.mongodb")
    public static class EmbeddedMongoDBProperties {
        public static final String DEFAULT_IMAGE = "mongo:4.4";

        private String image;

        public String getImage() {
            return image == null ? DEFAULT_IMAGE : image;
        }
    }
}

