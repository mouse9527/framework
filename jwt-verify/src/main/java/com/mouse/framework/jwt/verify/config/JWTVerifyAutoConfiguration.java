package com.mouse.framework.jwt.verify.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mouse.framework.jwt.verify.*;
import com.mouse.framework.security.TokenParser;
import lombok.Generated;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.io.InputStream;

@Generated
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(JWTVerifyProperties.class)
public class JWTVerifyAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(name = "verifyKeyReader")
    public PublicKeyReader verifyKeyReader(JWTVerifyProperties jwtVerifyProperties) throws IOException {
        return createPrivateKeyReader(jwtVerifyProperties.getVerifyKey());
    }

    @Bean
    @ConditionalOnMissingBean(Verifier.class)
    public Verifier verifier(PublicKeyReader verifyKeyReader, JWTVerifyProperties properties) {
        return new RSAVerifier(verifyKeyReader.read(), properties.getVerifyAlgorithm());
    }

    @Bean
    @ConditionalOnMissingBean(name = "decryptorKeyReader")
    public PublicKeyReader decryptorKeyReader(JWTVerifyProperties jwtVerifyProperties) throws IOException {
        return createPrivateKeyReader(jwtVerifyProperties.getDecryptKey());
    }

    @Bean
    @ConditionalOnMissingBean(Decryptor.class)
    public Decryptor decryptor(PublicKeyReader decryptorKeyReader) {
        return new RSADecryptor(decryptorKeyReader.read());
    }

    @Bean
    @ConditionalOnMissingBean(TokenParser.class)
    public TokenParser tokenParser(Decryptor decryptor, Verifier verifier, ObjectMapper objectMapper) {
        return new JWTTokenParser(decryptor, verifier, objectMapper);
    }

    @Bean
    @ConditionalOnMissingBean(ObjectMapper.class)
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    private PublicKeyReader createPrivateKeyReader(String path) throws IOException {
        try (InputStream inputStream = ResourceUtils.getURL(path).openStream()) {
            return new FilePublicKeyReader(inputStream);
        }
    }
}
