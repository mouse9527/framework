package com.mouse.framework.domain.core;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AggregationNotFoundExceptionTest {

    @Test
    void should_be_able_to_has_message() {
        AggregationNotFoundException aggregationNotFoundException = new AggregationNotFoundException("test", "test-collection");

        assertThat(aggregationNotFoundException).hasMessage("Aggregation test not found in collection test-collection");
    }
}
