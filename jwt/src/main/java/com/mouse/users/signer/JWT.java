package com.mouse.users.signer;

import java.util.Base64;

public class JWT {
    private final String headerStr;
    private final String payloadStr;
    private final String signature;

    public JWT(String headerStr, String payloadStr, String signature) {
        this.headerStr = headerStr;
        this.payloadStr = payloadStr;
        this.signature = signature;
    }

    public static Builder builder(Mapper mapper) {
        return new Builder(mapper);
    }

    @Override
    public String toString() {
        return String.format("%s.%s.%s", headerStr, payloadStr, signature);
    }

    public static final class Builder {
        private final Mapper mapper;
        private Header header;
        private Payload payload;

        private Builder(Mapper mapper) {
            this.mapper = mapper;
        }

        public Builder header(Header header) {
            this.header = header;
            return this;
        }

        public Builder payload(Payload payload) {
            this.payload = payload;
            return this;
        }

        public JWT sign(Signer signer) {
            Base64.Encoder encoder = Base64.getEncoder();
            String headerStr = encoder.encodeToString(mapper.writeToBytes(header));
            String payloadStr = encoder.encodeToString(mapper.writeToBytes(payload));
            return new JWT(headerStr,
                    payloadStr,
                    encoder.encodeToString(signer.sign(String.format("%s.%s", headerStr, payloadStr))));
        }
    }
}
