package com.mouse.framework.jwt;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

class PublicKeyReaderTest {
    @Test
    void should_be_able_to_read_private_key() throws IOException {
        InputStream inputStream = Objects.requireNonNull(this.getClass().getClassLoader().getResource("publicKey.pem")).openStream();

        PublicKeyReader privateKeyReader = new FilePublicKeyReader(inputStream);

        assertThat(privateKeyReader.read()).isNotNull();
    }

}
