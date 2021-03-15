package com.mouse.framework.domain.core;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class AuthoritiesSetTest {
    @Test
    void should_be_able_to_merge_correctly() {
        Authority authorityOne = new Authority("authority-1");
        Authority authorityTwo = new Authority("authority-2");
        Authority authorityThree = new Authority("authority-3");
        AuthoritiesSet authoritiesSet = new AuthoritiesSet(authorityOne, authorityTwo);
        AuthoritiesSet authoritiesSet1 = new AuthoritiesSet(authorityOne, authorityThree);

        AuthoritiesSet result = authoritiesSet.merge(authoritiesSet1);

        assertThat(authoritiesSet.getAuthorities()).isEqualTo(Set.of(authorityOne, authorityTwo));
        assertThat(result.getAuthorities()).isEqualTo(Set.of(authorityOne, authorityTwo, authorityThree));
        assertThat(authoritiesSet1.getAuthorities()).isEqualTo(Set.of(authorityOne, authorityThree));
    }

    @Test
    void should_be_able_to_equally() {
        AuthoritiesSet one = new AuthoritiesSet(new Authority("authority-1"));
        AuthoritiesSet two = new AuthoritiesSet(new Authority("authority-1"));
        AuthoritiesSet three = new AuthoritiesSet(new Authority("authority-2"));
        AuthoritiesSet four = new AuthoritiesSet();
        AuthoritiesSet five = new AuthoritiesSet(new Authority("authority-1"), new Authority("authority-2"));

        assertThat(one).isEqualTo(two);
        assertThat(one).isNotEqualTo(three);
        assertThat(one).isNotEqualTo(four);
        assertThat(one).isNotEqualTo(five);
    }

    @Test
    void should_be_able_to_contains_authorities() {
        AuthoritiesSet authorities = new AuthoritiesSet(new Authority("authority-1"), new Authority("authority-2"));

        assertThat(authorities.contains("authority-1")).isTrue();
        assertThat(authorities.contains("authority-4")).isFalse();
        assertThat(authorities.contains("authority-1", "authority-2")).isTrue();
        assertThat(authorities.contains("authority-1", "authority-4")).isFalse();
    }
}
