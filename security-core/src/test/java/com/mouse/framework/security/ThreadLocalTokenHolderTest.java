package com.mouse.framework.security;

import com.mouse.framework.domain.core.Token;
import com.mouse.framework.domain.core.TokenHolder;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class ThreadLocalTokenHolderTest {

    @Test
    void should_be_able_to_set_token() {
        TokenHolder tokenHolder = new ThreadLocalTokenHolder();
        Token token = mock(Token.class);

        tokenHolder.refresh(token);

        assertThat(tokenHolder.get()).isEqualTo(token);

        tokenHolder.clean();
    }
}
