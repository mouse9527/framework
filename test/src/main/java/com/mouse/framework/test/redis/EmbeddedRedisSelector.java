package com.mouse.framework.test.redis;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

public class EmbeddedRedisSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[]{EmbeddedRedisProperties.class.getName(), EmbeddedRedis.class.getName()};
    }
}
