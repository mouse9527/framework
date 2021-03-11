package com.mouse.framework.jwt;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HeaderTest {
    @Test
    void should_be_able_to_create() {
        Header header = new Header("JWT", "RS1024");

        assertThat(header.getTyp()).isEqualTo("JWT");
        assertThat(header.getAlg()).isEqualTo("RS1024");
    }
}
