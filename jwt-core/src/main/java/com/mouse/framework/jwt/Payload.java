package com.mouse.framework.jwt;

import lombok.Generated;
import lombok.Getter;

import java.util.Collection;

@Getter
public class Payload {
    private Long iat;
    private Long exp;
    private String jti;
    private Collection<String> aut;
    private String nam;
    private String cip;

    @Generated
    public Payload() {
    }

    public Payload(Long iat, Long exp, String jti, Collection<String> aut, String nam, String cip) {
        this.iat = iat;
        this.exp = exp;
        this.jti = jti;
        this.aut = aut;
        this.nam = nam;
        this.cip = cip;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private long iat;
        private long exp;
        private String jti;
        private Collection<String> aut;
        private String nam;
        private String cip;

        private Builder() {
        }

        public Builder issuedAt(long iat) {
            this.iat = iat;
            return this;
        }

        public Builder expirationTime(long exp) {
            this.exp = exp;
            return this;
        }

        public Builder jwtId(String jti) {
            this.jti = jti;
            return this;
        }

        public Builder authorities(Collection<String> aut) {
            this.aut = aut;
            return this;
        }

        public Builder name(String nam) {
            this.nam = nam;
            return this;
        }

        public Builder ciphertext(String cip) {
            this.cip = cip;
            return this;
        }

        public Payload build() {
            return new Payload(iat, exp, jti, aut, nam, cip);
        }
    }
}
