package com.mouse.framework.domain.core;

import com.google.common.collect.Sets;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AuthoritiesSet {
    private Set<Authority> authorities;

    protected AuthoritiesSet() {
    }

    public AuthoritiesSet(Authority... authorities) {
        this.authorities = Sets.newHashSet(authorities);
    }

    public AuthoritiesSet(Set<Authority> authorities) {
        this.authorities = authorities;
    }

    public AuthoritiesSet merge(AuthoritiesSet authoritiesSet) {
        Set<Authority> current = Sets.newHashSet(getValues());
        current.addAll(authoritiesSet.getValues());
        return new AuthoritiesSet(current);
    }

    public Set<Authority> getValues() {
        return Optional.ofNullable(authorities).orElseGet(HashSet::new);
    }

    public Set<String> getAuthorities() {
        return getValues().stream()
                .map(Authority::getAuthority)
                .collect(Collectors.toSet());
    }

    public Boolean contains(String... authorities) {
        return Stream.of(authorities).allMatch(getAuthorities()::contains);
    }
}
