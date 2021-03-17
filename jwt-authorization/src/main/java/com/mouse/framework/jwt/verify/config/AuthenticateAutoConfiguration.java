package com.mouse.framework.jwt.verify.config;

import com.mouse.framework.domain.core.ContextHolder;
import com.mouse.framework.domain.core.ContextSetter;
import com.mouse.framework.jwt.verify.ApplicationAspect;
import com.mouse.framework.jwt.verify.ApplicationAspectExecutor;
import com.mouse.framework.security.authorization.AuthorizationService;
import com.mouse.framework.security.authorization.ThreadLocalTokenHolder;
import com.mouse.framework.security.authorization.TokenHolder;
import lombok.Generated;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Generated
@EnableAspectJAutoProxy
@Configuration(proxyBeanMethods = false)
@ConditionalOnProperty(name = "security.jwt.enable-authenticate", havingValue = "true")
public class AuthenticateAutoConfiguration {

    @Bean
    public ApplicationAspect applicationAspect(ApplicationAspectExecutor applicationAspectExecutor) {
        return new ApplicationAspect(applicationAspectExecutor);
    }

    @Bean
    public ApplicationAspectExecutor applicationAspectExecutor(AuthorizationService authorizationService) {
        return new ApplicationAspectExecutor(authorizationService);
    }

    @Bean
    public AuthorizationService authenticationService(TokenHolder tokenHolder) {
        return new AuthorizationService(tokenHolder);
    }

    @Bean
    @ConditionalOnMissingBean({TokenHolder.class, ContextHolder.class})
    public ThreadLocalTokenHolder threadLocalTokenHolder() {
        ThreadLocalTokenHolder tokenHolder = new ThreadLocalTokenHolder();
        ContextSetter.set(tokenHolder);
        return tokenHolder;
    }
}
