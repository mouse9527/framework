package com.mouse.framework.jwt;

import com.mouse.framework.domain.core.AuthoritiesSet;
import com.mouse.framework.domain.core.Sequence;
import com.mouse.framework.domain.core.Token;
import com.mouse.framework.domain.core.User;
import lombok.Getter;

import java.time.Instant;

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
}
