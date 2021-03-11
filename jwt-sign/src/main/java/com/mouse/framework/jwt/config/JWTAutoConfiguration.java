package com.mouse.framework.jwt.config;

import com.mouse.framework.jwt.sign.Signer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(JWTSignProperties.class)
public class JWTAutoConfiguration {
    @Bean
    public Signer signer() {
        return null;
    }
}
