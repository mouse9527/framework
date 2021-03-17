package com.mouse.framework.security.authentication.jwt;

import org.junit.jupiter.api.Test;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

class PrivateKeyReaderTest {

    @Test
    void should_be_able_to_read_private_key() throws IOException {
        InputStream inputStream = ResourceUtils.getURL("classpath:privatekey.pem").openStream();

        PrivateKeyReader privateKeyReader = new FilePrivateKeyReader(inputStream);

        assertThat(privateKeyReader.read()).isNotNull();
    }
}
