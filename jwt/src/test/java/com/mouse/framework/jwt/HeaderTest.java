package com.mouse.framework.jwt;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class HeaderTest {
    @Test
    void should_be_able_to_crete_header() {
        Header header = new Header("JWT", "HS256");

        assertThat(header.getTyp()).isEqualTo("JWT");
        assertThat(header.getAlg()).isEqualTo("HS256");
    }
}
