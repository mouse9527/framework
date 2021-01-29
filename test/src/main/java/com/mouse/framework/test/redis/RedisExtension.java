package com.mouse.framework.test.redis;

import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class RedisExtension implements BeforeAllCallback {
    @Override
    public void beforeAll(ExtensionContext context) {
        EmbeddedRedis.getInstance();
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
