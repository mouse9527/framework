package com.mouse.framework.test.mongo;

import com.github.silaev.mongodb.replicaset.MongoDbReplicaSet;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public final class EmbeddedMongoDB {
    private static final AtomicInteger START_TIMES = new AtomicInteger();
    private static final Map<String, EmbeddedMongoDB> CACHE = new ConcurrentHashMap<>();
    private final MongoDbReplicaSet mongoDbReplicaSet;

    private EmbeddedMongoDB(String image) {
        EmbeddedMongoDB.START_TIMES.incrementAndGet();
        mongoDbReplicaSet = MongoDbReplicaSet.builder()
                .mongoDockerImageName(image)
                .build();
    }

    public static EmbeddedMongoDB getInstance(String image) {
        EmbeddedMongoDB embeddedMongoDB = EmbeddedMongoDB.CACHE.get(image);
        if (embeddedMongoDB == null) {
            embeddedMongoDB = new EmbeddedMongoDB(image);
            CACHE.put(image, embeddedMongoDB);
            embeddedMongoDB.start();
        }
        return embeddedMongoDB;
    }

    public static Integer startTimes() {
        return START_TIMES.get();
    }

    private void start() {
        mongoDbReplicaSet.start();
        mongoDbReplicaSet.waitForMaster();
    }

    public String getReplicaSetUrl() {
        return mongoDbReplicaSet.getReplicaSetUrl();
    }
}
