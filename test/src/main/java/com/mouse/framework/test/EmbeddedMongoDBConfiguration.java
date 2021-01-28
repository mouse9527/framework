package com.mouse.framework.test;

import lombok.Generated;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;

@Generated
@Configuration
@EnableAutoConfiguration
@AutoConfigureBefore(name = "org.springframework.boot.autoconfigure.data.mongo.MongoDatabaseFactoryDependentConfiguration")
@ConfigurationProperties(prefix = "application.test.embedded-mongo")
public class EmbeddedMongoDBConfiguration {
    public static final String DEFAULT_IMAGE = "mongo:4.4";
    private String image;

    @Bean
    public MongoDatabaseFactory mongoDatabaseFactory(EmbeddedMongoDB embeddedMongoDB) {
        return embeddedMongoDB.getMongoDatabaseFactory();
    }

    @Bean(destroyMethod = "stop")
    public EmbeddedMongoDB embeddedMongoDB() {
        return EmbeddedMongoDB.getInstance(getImage());
    }

    private String getImage() {
        return image == null ? DEFAULT_IMAGE : image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
