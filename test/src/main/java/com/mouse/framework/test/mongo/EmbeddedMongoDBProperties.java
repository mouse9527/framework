package com.mouse.framework.test.mongo;

import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Setter
@ConfigurationProperties(prefix = "test.embedded.mongodb")
public class EmbeddedMongoDBProperties {
    public static final String DEFAULT_IMAGE = "mongo:4.4";

    private String image;

    public String getImage() {
        return image == null ? DEFAULT_IMAGE : image;
    }
}
