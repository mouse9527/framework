package com.mouse.framework.sequence.snowflake;

import com.mouse.framework.domain.core.SequenceService;
import com.mouse.framework.domain.core.SequenceSetter;
import lombok.Generated;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Generated
@Configuration(proxyBeanMethods = false)
@ConditionalOnBean(WorkerIdAllocator.class)
@ConditionalOnMissingBean(SequenceService.class)
@EnableConfigurationProperties(SnowFlakeProperties.class)
public class SnowFlakeSequenceAutoConfiguration {
    private final WorkerIdAllocator workerIdAllocator;
    private final SnowFlakeProperties properties;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public SnowFlakeSequenceAutoConfiguration(WorkerIdAllocator workerIdAllocator, SnowFlakeProperties properties) {
        this.workerIdAllocator = workerIdAllocator;
        this.properties = properties;
    }

    @Bean
    public SequenceService workerIdAllocator() {
        SequenceService sequenceService = new SnowFlakeSequenceService(workerIdAllocator.allocate(), properties);
        SequenceSetter.set(sequenceService);
        return sequenceService;
    }
}
