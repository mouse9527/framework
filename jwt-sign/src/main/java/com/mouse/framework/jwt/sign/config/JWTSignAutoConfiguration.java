package com.mouse.framework.jwt.sign.config;

import com.mouse.framework.jwt.sign.*;
import com.mouse.framework.security.AuthenticationService;
import com.mouse.framework.security.AuthorizationService;
import com.mouse.framework.security.LoginService;
import com.mouse.framework.security.TokenAllocator;
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
    public Signer signer(PrivateKeyReader signKeyReader) {
        return new RsaSigner(signKeyReader.read());
    }

    @Bean
    @ConditionalOnMissingBean(Encryptor.class)
    public Encryptor encryptor(PrivateKeyReader encryptorKeyReader) {
        return new RsaEncryptor(encryptorKeyReader.read());
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
    public LoginService loginService(Set<AuthenticationService> authenticationServices,
                                     AuthorizationService authorizationService,
                                     TokenAllocator tokenAllocator) {
        return new LoginService(authenticationServices, authorizationService, tokenAllocator);
    }
}
