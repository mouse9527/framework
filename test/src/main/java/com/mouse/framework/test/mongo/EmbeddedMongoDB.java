package com.mouse.framework.test.mongo;

import com.github.silaev.mongodb.replicaset.MongoDbReplicaSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;

public final class EmbeddedMongoDB {
    private static final AtomicInteger START_TIMES = new AtomicInteger();
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final MongoDbReplicaSet mongoDbReplicaSet;

    EmbeddedMongoDB(EmbeddedMongoDBProperties properties) {
        EmbeddedMongoDB.START_TIMES.incrementAndGet();
        mongoDbReplicaSet = MongoDbReplicaSet.builder()
                .mongoDockerImageName(properties.getImage())
                .build();
    }

    public static Integer startTimes() {
        return START_TIMES.get();
    }

    public void stop() {
        mongoDbReplicaSet.stop();
        logger.info("EmbeddedMongoDB closed!");
    }

    public void start() {
        mongoDbReplicaSet.start();
        mongoDbReplicaSet.waitForMaster();
    }

    public String getReplicaSetUrl() {
        return mongoDbReplicaSet.getReplicaSetUrl();
    }
}
