package com.mouse.framework.jwt.verify.config;

import lombok.Generated;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@Generated
@ConfigurationProperties("security.jwt")
public class JWTVerifyProperties {
    private String verifyKey;

    private String decryptKey;
}
