package com.mouse.framework.test.redis;

import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class RedisExtension implements BeforeAllCallback {
    private static final ExtensionContext.Namespace NAMESPACE = ExtensionContext.Namespace.create(RedisExtension.class);

    @Override
    public void beforeAll(ExtensionContext context) {
        context.getRoot().getStore(NAMESPACE)
                .getOrComputeIfAbsent(EmbeddedRedis.class, v -> EmbeddedRedis.create());

        registerCloser(context);
    }

    private void registerCloser(ExtensionContext context) {
        context.getRoot().getStore(ExtensionContext.Namespace.GLOBAL)
                .getOrComputeIfAbsent(Closer.class, v -> new Closer());
    }

    private static class Closer implements ExtensionContext.Store.CloseableResource {
        @Override
        public void close() {
            EmbeddedRedis.close();
        }
    }
}
