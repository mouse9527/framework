package com.mouse.framework.jwt.sign.config;

import lombok.Generated;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@Generated
@ConfigurationProperties("security.jwt")
public class JWTSignProperties {
    private String signKey;

    private String encryptKey;
}
