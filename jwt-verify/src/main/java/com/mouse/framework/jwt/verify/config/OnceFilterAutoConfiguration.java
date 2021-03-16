package com.mouse.framework.jwt.verify.config;

import com.mouse.framework.domain.core.TokenHolder;
import com.mouse.framework.jwt.verify.filter.TokenLoaderFilter;
import com.mouse.framework.security.TokenParser;
import lombok.Generated;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.OncePerRequestFilter;

@Generated
@Configuration(proxyBeanMethods = false)
@ConditionalOnProperty(name = "security.jwt.enable-web-token-filter", havingValue = "true")
public class OnceFilterAutoConfiguration {

    @Bean
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public OncePerRequestFilter oncePerRequestFilter(TokenParser tokenParser, TokenHolder tokenHolder) {
        return new TokenLoaderFilter(tokenParser, tokenHolder);
    }
}
