package com.mouse.framework.jwt.verify.config;

import com.mouse.framework.domain.core.ContextSetter;
import com.mouse.framework.domain.core.TokenHolder;
import com.mouse.framework.jwt.verify.ApplicationAspect;
import com.mouse.framework.jwt.verify.ApplicationAspectExecutor;
import com.mouse.framework.security.AuthenticationService;
import com.mouse.framework.security.ThreadLocalTokenHolder;
import lombok.Generated;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Generated
@EnableAspectJAutoProxy
@Configuration(proxyBeanMethods = false)
public class JWTVerifyAutoConfiguration {

    @Bean
    public ApplicationAspect applicationAspect(ApplicationAspectExecutor applicationAspectExecutor) {
        return new ApplicationAspect(applicationAspectExecutor);
    }

    @Bean
    public ApplicationAspectExecutor applicationAspectExecutor(AuthenticationService authenticationService) {
        return new ApplicationAspectExecutor(authenticationService);
    }

    @Bean
    public AuthenticationService authenticationService(TokenHolder tokenHolder) {
        return new AuthenticationService(tokenHolder);
    }

    @Bean
    @ConditionalOnMissingBean(TokenHolder.class)
    public TokenHolder tokenHolder() {
        TokenHolder tokenHolder = new ThreadLocalTokenHolder();
        ContextSetter.set(tokenHolder);
        return tokenHolder;
    }
}
