package com.mouse.framework.test.mongo;

import com.github.silaev.mongodb.replicaset.MongoDbReplicaSet;
import com.google.common.collect.ImmutableList;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@Generated
public final class EmbeddedMongoDB {
    private static final Object LOCK = new Object();
    private static final List<EmbeddedMongoDB> INSTANCES = new ArrayList<>();
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final MongoDbReplicaSet mongoDbReplicaSet;
    private SimpleMongoClientDatabaseFactory simpleMongoClientDatabaseFactory;

    EmbeddedMongoDB(EmbeddedMongoDBProperties properties) {
        mongoDbReplicaSet = MongoDbReplicaSet.builder()
                .mongoDockerImageName(properties.getImage())
                .build();
    }

    public static List<EmbeddedMongoDB> getInstances() {
        return ImmutableList.copyOf(INSTANCES);
    }

    public static synchronized EmbeddedMongoDB create() {
        EmbeddedMongoDB mongoDB = new EmbeddedMongoDB(new EmbeddedMongoDBProperties());
        mongoDB.init();
        INSTANCES.add(mongoDB);
        return mongoDB;
    }

    public static void closeAll() {
        INSTANCES.forEach(EmbeddedMongoDB::stop);
        INSTANCES.clear();
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
