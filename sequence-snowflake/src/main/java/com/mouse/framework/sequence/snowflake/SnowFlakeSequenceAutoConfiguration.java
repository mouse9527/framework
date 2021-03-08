package com.mouse.framework.sequence.snowflake;

import com.mouse.framework.domain.core.SequenceService;
import com.mouse.framework.domain.core.SequenceSetter;
import lombok.Generated;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
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
@AutoConfigureAfter(WorkerAllocatorAutoConfiguration.class)
public class SnowFlakeSequenceAutoConfiguration {

    @Bean
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public SequenceService workerIdAllocator(WorkerIdAllocator workerIdAllocator, SnowFlakeProperties properties) {
        SequenceService sequenceService = new SnowFlakeSequenceService(workerIdAllocator.allocate(), properties);
        SequenceSetter.set(sequenceService);
        return sequenceService;
    }
}
