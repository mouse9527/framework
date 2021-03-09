package com.mouse.users.signer;

import java.util.Collection;

public class Payload {
    private final Long iat;
    private final Long exp;
    private final String jti;
    private final Collection<String> aut;
    private final String nam;
    private final String cip;

    public Payload(Long iat, Long exp, String jti, Collection<String> aut, String nam, String cip) {
        this.iat = iat;
        this.exp = exp;
        this.jti = jti;
        this.aut = aut;
        this.nam = nam;
        this.cip = cip;
    }

    public static PayloadBuilder builder() {
        return new PayloadBuilder();
    }

    public Long getIat() {
        return iat;
    }

    public Long getExp() {
        return exp;
    }

    public String getJti() {
        return jti;
    }

    public Collection<String> getAut() {
        return aut;
    }

    public String getNam() {
        return nam;
    }

    public String getCip() {
        return cip;
    }

    public static class PayloadBuilder {
        private long iat;
        private long exp;
        private String jti;
        private Collection<String> aut;
        private String nam;
        private String cip;

        public PayloadBuilder issuedAt(long iat) {
            this.iat = iat;
            return this;
        }

        public PayloadBuilder expirationTime(long exp) {
            this.exp = exp;
            return this;
        }

        public PayloadBuilder jwtId(String jti) {
            this.jti = jti;
            return this;
        }

        public PayloadBuilder authorities(Collection<String> aut) {
            this.aut = aut;
            return this;
        }

        public PayloadBuilder name(String nam) {
            this.nam = nam;
            return this;
        }

        public PayloadBuilder ciphertext(String cip) {
            this.cip = cip;
            return this;
        }

        public Payload build(Encryptor encryptor) {
            return new Payload(iat, exp, jti, aut, nam, encryptor.encrypt(cip));
        }
    }
}
