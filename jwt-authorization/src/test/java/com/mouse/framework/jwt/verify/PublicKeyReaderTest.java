package com.mouse.framework.jwt.verify;

import org.junit.jupiter.api.Test;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

class PublicKeyReaderTest {
    @Test
    void should_be_able_to_read_private_key() throws IOException {
        InputStream inputStream = ResourceUtils.getURL("classpath:publickey.pem").openStream();

        PublicKeyReader privateKeyReader = new FilePublicKeyReader(inputStream);

        assertThat(privateKeyReader.read()).isNotNull();
    }

}
