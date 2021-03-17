package com.mouse.framework.jwt.verify.config;

import lombok.Generated;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Optional;

@Getter
@Setter
@Generated
@ConfigurationProperties("security.jwt")
public class JWTVerifyProperties {
    private static final String VERIFY_ALGORITHM = "SHA1WithRSA";
    private static final String DECRYPT_TRANSFORMATION = "RSA";
    private String verifyKey;
    private String verifyAlgorithm;
    private String decryptKey;
    private String decryptTransformation;

    public String getVerifyAlgorithm() {
        return Optional.ofNullable(verifyAlgorithm).orElse(VERIFY_ALGORITHM);
    }

    public String getDecryptTransformation() {
        return Optional.ofNullable(decryptTransformation).orElse(DECRYPT_TRANSFORMATION);
    }
}
