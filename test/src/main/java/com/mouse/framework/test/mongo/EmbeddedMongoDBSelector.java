package com.mouse.framework.test.mongo;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

public class EmbeddedMongoDBSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[]{EmbeddedMongoDBConfiguration.class.getName()};
    }
}
