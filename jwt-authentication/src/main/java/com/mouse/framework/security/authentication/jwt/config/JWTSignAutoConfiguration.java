package com.mouse.framework.security.authentication.jwt.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mouse.framework.security.authentication.*;
import com.mouse.framework.security.authentication.jwt.*;
import lombok.Generated;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

@Generated
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(JWTSignProperties.class)
public class JWTSignAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(Signer.class)
    public Signer signer(PrivateKeyReader signKeyReader, JWTSignProperties properties) {
        return new RSASigner(signKeyReader.read(), properties.getSignAlgorithm());
    }

    @Bean
    @ConditionalOnMissingBean(Encryptor.class)
    public Encryptor encryptor(PrivateKeyReader encryptorKeyReader, JWTSignProperties properties) {
        return new RSAEncryptor(encryptorKeyReader.read(), properties.getEncryptTransformation());
    }

    @Bean
    @ConditionalOnMissingBean(name = "signKeyReader")
    public PrivateKeyReader signKeyReader(JWTSignProperties properties) throws IOException {
        return createPrivateKeyReader(properties.getSignKey());
    }

    @Bean
    @ConditionalOnMissingBean(name = "encryptorKeyReader")
    public PrivateKeyReader encryptorKeyReader(JWTSignProperties properties) throws IOException {
        return createPrivateKeyReader(properties.getEncryptKey());
    }

    private PrivateKeyReader createPrivateKeyReader(String path) throws IOException {
        try (InputStream inputStream = ResourceUtils.getURL(path).openStream()) {
            return new FilePrivateKeyReader(inputStream);
        }
    }

    @Bean
    @ConditionalOnMissingBean(LoginService.class)
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public LoginService loginService(Set<IdentificationService> identificationServices,
                                     AuthenticationProvider authorizationService,
                                     TokenAllocator tokenAllocator) {
        return new LoginService(identificationServices, authorizationService, tokenAllocator);
    }

    @Bean
    @ConditionalOnMissingBean(TokenFormat.class)
    public TokenFormat tokenFormat(Encryptor encryptor, Signer signer, ObjectMapper objectmapper) {
        return new JWTFormat(encryptor, signer, objectmapper);
    }

    @Bean
    @ConditionalOnMissingBean(ObjectMapper.class)
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
