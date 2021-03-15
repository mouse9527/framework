package com.mouse.framework.security.application;

public @interface Authentication {
    String[] value() default "";

    boolean logged() default false;
}
