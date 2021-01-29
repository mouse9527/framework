package com.mouse.framework.test.mongo;

import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class MongoExtension implements BeforeAllCallback {
    private static final ExtensionContext.Namespace NAMESPACE = ExtensionContext.Namespace.create(MongoExtension.class);

    @Override
    public void beforeAll(ExtensionContext context) {
        ExtensionContext.Store store = context.getRoot().getStore(NAMESPACE);
        store.getOrComputeIfAbsent(EmbeddedMongoDB.class, v -> EmbeddedMongoDB.getInstance());
        registerCloser(context);
    }

    private void registerCloser(ExtensionContext context) {
        context.getRoot()
                .getStore(ExtensionContext.Namespace.GLOBAL)
                .getOrComputeIfAbsent(EmbeddedMongoDBCloser.class, v -> new EmbeddedMongoDBCloser());
    }

    private static class EmbeddedMongoDBCloser implements ExtensionContext.Store.CloseableResource {
        @Override
        public void close() {
            EmbeddedMongoDB.close();
        }
    }
}
