package com.mouse.framework.test.mongo;

import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class MongoExtension implements BeforeAllCallback {

    @Override
    public void beforeAll(ExtensionContext context) {
        EmbeddedMongoDB.getInstance();
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
