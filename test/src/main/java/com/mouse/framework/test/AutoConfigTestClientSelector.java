package com.mouse.framework.test;

import lombok.Generated;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Map;

@Generated
public class AutoConfigTestClientSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        Map<String, Object> annotationAttributes = importingClassMetadata.getAnnotationAttributes(EnableTestClient.class.getName(), false);
        AnnotationAttributes attributes = AnnotationAttributes.fromMap(annotationAttributes);
        if (attributes == null) throw new IllegalArgumentException("Failed to enable TestClient");

        Class<?> headerMockerClass = attributes.getClass("value");
        if (headerMockerClass.equals(HeaderMocker.class))
            throw new IllegalArgumentException("Please set EnableTestClient.headerMockerClass!");
        Class<?> headerGivenClass = attributes.getClass("headerGivenClass");
        if (headerGivenClass.equals(HeaderGiven.class)) {
            return new String[]{headerMockerClass.getName(), TestClient.class.getName()};
        }
        return new String[]{headerMockerClass.getName(), headerGivenClass.getName(), TestClient.class.getName()};
    }
}
