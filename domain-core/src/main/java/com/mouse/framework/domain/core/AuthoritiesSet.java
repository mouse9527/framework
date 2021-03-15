package com.mouse.framework.domain.core;

import com.google.common.collect.Sets;
import lombok.EqualsAndHashCode;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@EqualsAndHashCode
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
        Set<Authority> current = Sets.newHashSet(getAuthorities());
        current.addAll(authoritiesSet.getAuthorities());
        return new AuthoritiesSet(current);
    }

    public Set<Authority> getAuthorities() {
        return Optional.ofNullable(authorities).orElseGet(HashSet::new);
    }

    public Boolean contains(String... authorities) {
        Set<String> authorityValues = getAuthorities().stream()
                .map(Authority::getAuthority)
                .collect(Collectors.toSet());
        return Stream.of(authorities).allMatch(authorityValues::contains);
    }
}
