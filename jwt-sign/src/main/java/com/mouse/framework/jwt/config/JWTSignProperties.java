package com.mouse.framework.jwt.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties("security.jwt.sign")
public class JWTSignProperties {
    private String signKey;

    private String encryptKey;
}
