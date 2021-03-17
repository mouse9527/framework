package com.mouse.framework.jwt.verify;

import com.mouse.framework.security.authorization.AuthorizationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.internal.verification.Times;

import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;

class ApplicationAspectExecutorTest {
    private ApplicationAspectExecutor executor;
    private AuthorizationService authorizationService;

    @BeforeEach
    void setUp() {
        authorizationService = mock(AuthorizationService.class);
        executor = new ApplicationAspectExecutor(authorizationService);
    }

    @Test
    void should_be_able_to_execute_with_required_logged() {
        executor.execute(true, null);

        then(authorizationService).should(new Times(1)).requireLogged();
        then(authorizationService).shouldHaveNoMoreInteractions();
    }

    @Test
    void should_be_able_to_execute_with_required_authorities() {
        String[] requiredAuthorities = {"authority-1"};
        executor.execute(false, requiredAuthorities);

        then(authorizationService).should(new Times(1)).requireAuthorities(requiredAuthorities);
        then(authorizationService).shouldHaveNoMoreInteractions();
    }

    @Test
    void should_be_able_to_do_nothing() {
        executor.execute(false, new String[]{});

        then(authorizationService).shouldHaveNoInteractions();
    }

    @Test
    void should_be_able_to_do_nothing_with_empty_authorities() {
        executor.execute(false, new String[]{""});

        then(authorizationService).shouldHaveNoInteractions();
    }
}
