package com.mouse.framework.domain.core;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class AuthoritiesSetTest {
    @Test
    void should_be_able_to_merge_correctly() {
        Authority authorityOne = () -> "authority-1";
        Authority authorityTwo = () -> "authority-2";
        Authority authorityThree = () -> "authority-3";
        AuthoritiesSet authoritiesSet = new AuthoritiesSet(authorityOne, authorityTwo);
        AuthoritiesSet authoritiesSet1 = new AuthoritiesSet(authorityOne, authorityThree);

        AuthoritiesSet result = authoritiesSet.merge(authoritiesSet1);

        assertThat(authoritiesSet.getAuthorities()).isEqualTo(Set.of(authorityOne, authorityTwo));
        assertThat(result.getAuthorities()).isEqualTo(Set.of(authorityOne, authorityTwo, authorityThree));
        assertThat(authoritiesSet1.getAuthorities()).isEqualTo(Set.of(authorityOne, authorityThree));
    }

    @Test
    void should_be_able_to_contains_authorities() {
        AuthoritiesSet authorities = new AuthoritiesSet(() -> "authority-1", () -> "authority-2");

        assertThat(authorities.contains("authority-1")).isTrue();
        assertThat(authorities.contains("authority-4")).isFalse();
        assertThat(authorities.contains("authority-1", "authority-2")).isTrue();
        assertThat(authorities.contains("authority-1", "authority-4")).isFalse();
    }
}
