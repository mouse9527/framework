package com.mouse.framework.test.mongo;

import com.github.silaev.mongodb.replicaset.MongoDbReplicaSet;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicInteger;

@Generated
public final class EmbeddedMongoDB {
    private static final Object LOCK = new Object();
    private static final AtomicInteger START_TIMES = new AtomicInteger();
    private static EmbeddedMongoDB instance;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final MongoDbReplicaSet mongoDbReplicaSet;
    private SimpleMongoClientDatabaseFactory simpleMongoClientDatabaseFactory;

    EmbeddedMongoDB(EmbeddedMongoDBProperties properties) {
        mongoDbReplicaSet = MongoDbReplicaSet.builder()
                .mongoDockerImageName(properties.getImage())
                .build();
    }

    public static EmbeddedMongoDB getInstance() {
        START_TIMES.incrementAndGet();
        if (EmbeddedMongoDB.instance == null) {
            synchronized (EmbeddedMongoDB.class) {
                if (EmbeddedMongoDB.instance == null) {
                    EmbeddedMongoDB.instance = new EmbeddedMongoDB(new EmbeddedMongoDBProperties());
                    EmbeddedMongoDB.instance.init();
                }
            }
        }
        return EmbeddedMongoDB.instance;
    }

    public static void close() {
        EmbeddedMongoDB.instance.stop();
    }

    public static Integer startTimes() {
        return START_TIMES.get();
    }

    public static EmbeddedMongoDB get() {
        return EmbeddedMongoDB.instance;
    }

    private void stop() {
        closeConnection();
        mongoDbReplicaSet.stop();
        logger.info("EmbeddedMongoDB closed!");
    }

    private void init() {
        mongoDbReplicaSet.start();
        mongoDbReplicaSet.waitForMaster();
    }

    public MongoDatabaseFactory getMongoDatabaseFactory() {
        if (simpleMongoClientDatabaseFactory == null) {
            synchronized (LOCK) {
                if (simpleMongoClientDatabaseFactory == null) {
                    simpleMongoClientDatabaseFactory = new SimpleMongoClientDatabaseFactory(getReplicaSetUrl());
                }
            }
        }
        return simpleMongoClientDatabaseFactory;
    }

    private String getReplicaSetUrl() {
        return mongoDbReplicaSet.getReplicaSetUrl();
    }

    private void closeConnection() {
        if (simpleMongoClientDatabaseFactory == null) {
            return;
        }
        Class<? extends SimpleMongoClientDatabaseFactory> factoryClass = simpleMongoClientDatabaseFactory.getClass();
        try {
            Method closeClient = factoryClass.getDeclaredMethod("closeClient");
            closeClient.setAccessible(true);
            closeClient.invoke(simpleMongoClientDatabaseFactory);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            logger.error("Failed to close Mongo connection!", e);
        }
    }
}
