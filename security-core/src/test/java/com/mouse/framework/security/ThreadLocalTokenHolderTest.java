package com.mouse.framework.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class ThreadLocalTokenHolderTest {
    private TokenHolder tokenHolder;

    @BeforeEach
    void setUp() {
        tokenHolder = new ThreadLocalTokenHolder();
    }

    @Test
    void should_be_able_to_set_token() {
        Token token = mock(Token.class);

        tokenHolder.refresh(token);

        assertThat(tokenHolder.get()).isEqualTo(Optional.of(token));

        tokenHolder.clean();
    }

    @Test
    void should_be_able_to_holder_token_in_same_thread() throws InterruptedException {
        Thread thread = new Thread(() -> {
            Token token = mock(Token.class);
            tokenHolder.refresh(token);
        });

        thread.start();

        thread.join();
        assertThat(tokenHolder.get()).isEmpty();
    }

    @Test
    void should_be_able_to_clean_holder() {
        tokenHolder.refresh(mock(Token.class));

        tokenHolder.clean();

        assertThat(tokenHolder.get()).isEmpty();
    }
}
