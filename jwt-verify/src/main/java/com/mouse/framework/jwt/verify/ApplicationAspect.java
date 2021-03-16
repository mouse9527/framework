package com.mouse.framework.jwt.verify;

import com.mouse.framework.application.CommandApplication;
import com.mouse.framework.application.QueryApplication;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class ApplicationAspect {
    private final ApplicationAspectExecutor applicationAspectExecutor;

    public ApplicationAspect(ApplicationAspectExecutor applicationAspectExecutor) {
        this.applicationAspectExecutor = applicationAspectExecutor;
    }

    @Pointcut("within(com.mouse..application..*)")
    public void inApplication() {
    }

    @Pointcut("execution(public * *(..))")
    public void anyPublicMethod() {
    }

    @Before("inApplication() && anyPublicMethod() && @within(annotation)")
    public void beforeQuery(QueryApplication annotation) {
        applicationAspectExecutor.execute(annotation.requireLogged(), getAuthorities(annotation.requireAuthorities()));
    }

    private String[] getAuthorities(String[] authorities) {
        if (Arrays.equals(authorities, new String[]{""})) {
            return null;
        }
        return authorities;
    }

    @Before("inApplication() && anyPublicMethod() && @within(annotation)")
    public void beforeCommand(CommandApplication annotation) {
        applicationAspectExecutor.execute(annotation.requireLogged(), getAuthorities(annotation.requireAuthorities()));
    }
}
