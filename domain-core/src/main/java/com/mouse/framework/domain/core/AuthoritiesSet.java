package com.mouse.framework.domain.core;

import com.google.common.collect.Sets;
import lombok.Generated;

import java.util.HashSet;
import java.util.Objects;
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
        Set<Authority> current = Sets.newHashSet(getAuthorities());
        current.addAll(authoritiesSet.getAuthorities());
        return new AuthoritiesSet(current);
    }

    public Set<Authority> getAuthorities() {
        return Optional.ofNullable(authorities).orElseGet(HashSet::new);
    }

    public Set<String> getValues() {
        return getAuthorities().stream()
                .map(Authority::getAuthority)
                .collect(Collectors.toSet());
    }

    public Boolean contains(String... authorities) {
        return Stream.of(authorities).allMatch(getValues()::contains);
    }

    @Generated
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthoritiesSet that = (AuthoritiesSet) o;
        return Objects.equals(getValues(), that.getValues());
    }

    @Generated
    @Override
    public int hashCode() {
        return Objects.hash(getValues());
    }
}
