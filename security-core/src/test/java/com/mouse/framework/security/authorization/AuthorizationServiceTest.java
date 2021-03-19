package com.mouse.framework.security.authorization;

import com.mouse.framework.domain.core.AuthoritiesSet;
import com.mouse.framework.security.Token;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class AuthorizationServiceTest {
    private static final String AUTHORITY_1 = "authority-1";
    private static final String AUTHORITY_2 = "authority-2";
    private AuthorizationService authorizationService;
    private TokenHolder tokenHolder;

    @BeforeEach
    void setUp() {
        tokenHolder = mock(TokenHolder.class);
        authorizationService = new AuthorizationService(tokenHolder);
    }

    @Test
    void should_be_able_to_require_logged() {
        given(tokenHolder.get()).willReturn(Optional.of(mock(Token.class)));

        Throwable throwable = catchThrowable(authorizationService::requireLogged);

        assertThat(throwable).isNull();
    }

    @Test
    void should_be_able_to_raise_exception_when_not_login() {
        given(tokenHolder.get()).willReturn(Optional.empty());

        Throwable throwable = catchThrowable(authorizationService::requireLogged);

        assertThat(throwable).isNotNull();
        assertThat(throwable).isInstanceOf(UnLoggedException.class);
        assertThat(throwable).hasMessage("error.un-logged");
    }

    @Test
    void should_be_able_to_authenticate_authorities() {
        Token token = mock(Token.class);
        given(token.getAuthorities()).willReturn(new AuthoritiesSet(() -> AUTHORITY_1, () -> AUTHORITY_2));
        given(tokenHolder.get()).willReturn(Optional.of(token));

        Throwable throwable = catchThrowable(() -> authorizationService.requireAuthorities(AUTHORITY_1, AUTHORITY_2));

        assertThat(throwable).isNull();

        Throwable forbidden = catchThrowable(() -> authorizationService.requireAuthorities(AUTHORITY_1, "authority-3"));
        assertThat(forbidden).isNotNull();
        assertThat(forbidden).isInstanceOf(AccessDeniedException.class);
        assertThat(forbidden).hasMessage("error.access-denied");

        given(tokenHolder.get()).willReturn(Optional.empty());

        Throwable unLogin = catchThrowable(() -> authorizationService.requireAuthorities(AUTHORITY_1));

        assertThat(unLogin).isNotNull();
        assertThat(unLogin).isInstanceOf(UnLoggedException.class);
        assertThat(unLogin).hasMessage("error.un-logged");
    }

}
