package com.mouse.framework.sequence.snowflake;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration(proxyBeanMethods = false)
@ConditionalOnMissingBean(WorkerIdAllocator.class)
@EnableConfigurationProperties({SnowFlakeProperties.class, WorkerIdProperties.class})
public class WorkerAllocatorAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(WorkerIdAllocator.class)
    @SuppressWarnings({"rawtypes", "unchecked", "SpringJavaInjectionPointsAutowiringInspection"})
    @ConditionalOnProperty(name = "sequence.snowflake.worker-id.allocator-type", havingValue = "redis")
    public WorkerIdAllocator workerIdAllocator(RedisTemplate redisTemplate, SnowFlakeProperties snowFlakeProperties, WorkerIdProperties properties) {
        return new RedisWorkerIdAllocator(redisTemplate, snowFlakeProperties.getMaxWorkerId(), properties);
    }

    //FIXME: export bean with name workerIdAllocator
    @Bean
    @ConditionalOnMissingBean(WorkerIdAllocator.class)
    @ConditionalOnProperty(name = "sequence.snowflake.worker-id.allocator-type", havingValue = "fixed")
    public WorkerIdAllocator fixedWorkerIdAllocator(WorkerIdProperties properties) {
        return new FixedWorkerIdAllocator(properties);
    }
}
