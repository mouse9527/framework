package com.mouse.framework.test;

import com.github.silaev.mongodb.replicaset.MongoDbReplicaSet;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/*
 * Open a replica set mongodb in docker
 *
 * <b>Must call {@link EmbeddedMongoDB#stop()} when the end of use</b>
 */
@Generated
public final class EmbeddedMongoDB {
    private static final Object LOCK = new Object();
    private static EmbeddedMongoDB instance;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final MongoDbReplicaSet mongoDbReplicaSet;
    private SimpleMongoClientDatabaseFactory simpleMongoClientDatabaseFactory;

    private EmbeddedMongoDB(String mongoDockerImageName) {
        mongoDbReplicaSet = MongoDbReplicaSet.builder()
                .mongoDockerImageName(mongoDockerImageName)
                .build();
        mongoDbReplicaSet.start();
        mongoDbReplicaSet.waitForMaster();
    }

    public static EmbeddedMongoDB getInstance(String mongoDockerImageName) {
        if (instance == null) {
            synchronized (LOCK) {
                if (instance == null) {
                    instance = new EmbeddedMongoDB(mongoDockerImageName);
                }
            }
        }
        return instance;
    }

    public void stop() {
        if (simpleMongoClientDatabaseFactory != null) {
            closeConnection();
            simpleMongoClientDatabaseFactory = null;
        }
        mongoDbReplicaSet.stop();
        EmbeddedMongoDB.instance = null;
    }

    private void closeConnection() {
        Class<? extends SimpleMongoClientDatabaseFactory> factoryClass = simpleMongoClientDatabaseFactory.getClass();
        try {
            Method closeClient = factoryClass.getDeclaredMethod("closeClient");
            closeClient.setAccessible(true);
            closeClient.invoke(simpleMongoClientDatabaseFactory);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            logger.error("Failed to close Mongo connection!", e);
        }
    }

    public String getReplicaSetUrl() {
        return mongoDbReplicaSet.getReplicaSetUrl();
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
}
