package com.mouse.framework.sequence.snowflake;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties({SnowFlakeProperties.class, WorkerIdProperties.class})
@ConditionalOnMissingBean(WorkerIdAllocator.class)
public class WorkerAllocatorAutoConfiguration {

    @Bean
    @SuppressWarnings({"rawtypes", "unchecked", "SpringJavaInjectionPointsAutowiringInspection"})
    @ConditionalOnProperty(value = "sequence.snowflake.worker-id.allocator-type", havingValue = "redis")
    public WorkerIdAllocator workerIdAllocator(RedisTemplate redisTemplate, SnowFlakeProperties snowFlakeProperties, WorkerIdProperties properties) {
        return new RedisWorkerIdAllocator(redisTemplate, snowFlakeProperties.getMaxWorkerId(), properties);
    }
}
