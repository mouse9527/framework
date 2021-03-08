package com.mouse.framework.sequence.snowflake;

import lombok.Generated;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.concurrent.TimeUnit;

@Getter
@Setter
@Generated
@ConfigurationProperties("sequence.snowflake.worker-id")
public class WorkerIdProperties {
    private String keyPrefix;
    private Long heartbeatIntervalSeconds;
    private Integer maxFailedTimes;

    // TODO: move it into WorkerIdUtils
    String createKey(long workerId) {
        return String.format("%s:%d", getKeyPrefix(), workerId);
    }

    long getMaxEffectiveSeconds() {
        return heartbeatIntervalSeconds * maxFailedTimes;
    }

    long getHeartBeatIntervalMillisecond() {
        return TimeUnit.SECONDS.toMillis(heartbeatIntervalSeconds);
    }
}
