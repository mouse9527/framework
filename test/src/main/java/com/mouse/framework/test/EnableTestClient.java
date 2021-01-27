package com.mouse.framework.test;

import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(AutoConfigTestClientSelector.class)
public @interface EnableTestClient {
    @AliasFor("headerMockerClass")
    Class<? extends HeaderMocker> value() default HeaderMocker.class;

    @AliasFor("value")
    Class<? extends HeaderMocker> headerMockerClass() default HeaderMocker.class;

    Class<? extends HeaderGiven> headerGivenClass() default HeaderGiven.class;
}
