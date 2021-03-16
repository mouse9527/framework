package com.mouse.framework.application;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Component
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface QueryApplication {
    String[] value() default "";

    String[] requireAuthorities() default "";

    boolean requireLogged() default false;
}
