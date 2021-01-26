package com.mouse.framework.test;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(TestClient.class)
@Documented
public @interface EnableTestClient {
}
