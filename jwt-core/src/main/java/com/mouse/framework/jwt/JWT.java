package com.mouse.framework.jwt;

import com.mouse.framework.domain.core.*;
import lombok.Getter;

import java.time.Instant;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
public class JWT implements Token {
    private final String id;
    private final User user;
    private final Instant issuedAt;
    private final Instant expirationTime;
    private final AuthoritiesSet authorities;

    public JWT(User user, AuthoritiesSet authorities, Instant iat, Instant exp) {
        this.id = Sequence.nextStr();
        this.user = user;
        this.issuedAt = iat;
        this.expirationTime = exp;
        this.authorities = authorities;
    }

    public JWT(Payload payload) {
        this.id = payload.getJti();
        this.user = new SimpleUser(payload.getCip(), payload.getNam());
        this.issuedAt = Instant.ofEpochSecond(payload.getIat());
        this.expirationTime = Instant.ofEpochSecond(payload.getExp());
        Set<Authority> collect = payload.getAut()
                .stream().map(Authority::new)
                .collect(Collectors.toSet());
        this.authorities = new AuthoritiesSet(collect);
    }

    private static final class SimpleUser implements User {
        private final String id;
        private final String username;

        private SimpleUser(String id, String username) {
            this.id = id;
            this.username = username;
        }

        @Override
        public String getId() {
            return id;
        }

        @Override
        public String getUsername() {
            return username;
        }
    }
}
