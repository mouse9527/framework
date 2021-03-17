package com.mouse.framework.security.authentication.jwt.config;

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
    private static final String ENCRYPT_TRANSFORMATION = "RSA";
    private String signKey;
    private String signAlgorithm;

    private String encryptKey;
    private String encryptTransformation;

    public String getSignAlgorithm() {
        return Optional.ofNullable(signAlgorithm).orElse(SIGN_ALGORITHM);
    }

    public String getEncryptTransformation() {
        return Optional.ofNullable(encryptTransformation).orElse(ENCRYPT_TRANSFORMATION);
    }
}
