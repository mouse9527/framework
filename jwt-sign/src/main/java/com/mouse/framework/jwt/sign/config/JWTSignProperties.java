package com.mouse.framework.jwt.sign.config;

import lombok.Generated;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Optional;

@Getter
@Setter
@Generated
@ConfigurationProperties("security.jwt")
public class JWTSignProperties {
    private static final String SIGN_ALGORITHM = "SHA1WithRSA";
    private String signKey;
    private String signAlgorithm;

    private String encryptKey;

    public String getSignAlgorithm() {
        return Optional.ofNullable(signAlgorithm).orElse(SIGN_ALGORITHM);
    }
}
