package com.mouse.framework.application;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.*;

/**
 * @see Transactional
 */
@Component
@Documented
@Transactional
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface CommandApplication {
    String[] value() default "";

    String[] requireAuthorities() default "";

    boolean requireLogged() default false;
}
