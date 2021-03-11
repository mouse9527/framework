package com.mouse.framework.domain.core;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AuthorityTest {

    @Test
    void should_be_able_to_equally() {
        Authority one = new Authority("authority-1");
        Authority two = new Authority("authority-1");

        assertThat(one).isEqualTo(two);
        assertThat(one).isNotEqualTo(null);
        assertThat(one).isNotEqualTo(new Authority(null));
        assertThat(one).isNotEqualTo("a");
        assertThat(one).isNotEqualTo(new Authority("authority-2"));
    }
}
