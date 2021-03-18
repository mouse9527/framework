package com.mouse.framework.test.application;

import com.mouse.framework.application.CommandApplication;
import com.mouse.framework.application.QueryApplication;
import org.junit.jupiter.api.Test;

import static com.mouse.framework.test.application.ApplicationAssert.assertRequiredAuthorities;
import static com.mouse.framework.test.application.ApplicationAssert.assertRequiredLogged;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class ApplicationAssertTest {

    @Test
    void should_be_able_to_assert_logged() {
        Throwable throwable = catchThrowable(() -> assertRequiredLogged(LoggedApplication.class));

        assertThat(throwable).isNull();

        Throwable unLogged = catchThrowable(() -> assertRequiredLogged(UnRequiredLoggedApplication.class));

        assertThat(unLogged).isNotNull();
        assertThat(unLogged).isInstanceOf(AssertionError.class);
        assertThat(unLogged).hasMessage("Please set requireLogged = true");

        Throwable nonApplication = catchThrowable(() -> assertRequiredLogged(NonApplication.class));

        assertThat(nonApplication).isNotNull();
        assertThat(nonApplication).isInstanceOf(AssertionError.class);
        assertThat(nonApplication).hasMessage("No CommandApplication or QueryApplication annotation");
    }

    @Test
    void should_be_able_to_assert_required_authorities() {
        Throwable throwable = catchThrowable(() -> assertRequiredAuthorities(RequireAuthoritiesQueryApplication.class, "authority-1"));

        assertThat(throwable).isNull();

        Throwable commandApplication = catchThrowable(() -> assertRequiredAuthorities(RequireAuthoritiesCommandApplication.class, "authority-1", "authority-2"));

        assertThat(commandApplication).isNull();

        Throwable notEquallyAuthorities = catchThrowable(() -> assertRequiredAuthorities(RequireAuthoritiesCommandApplication.class, "authority-1"));

        assertThat(notEquallyAuthorities).isNotNull();
        assertThat(notEquallyAuthorities).isInstanceOf(AssertionError.class);

        Throwable nonApplication = catchThrowable(() -> assertRequiredAuthorities(NonApplication.class));

        assertThat(nonApplication).isNotNull();
        assertThat(nonApplication).isInstanceOf(AssertionError.class);
        assertThat(nonApplication).hasMessage("No CommandApplication or QueryApplication annotation");
    }

    private static class NonApplication {
    }

    @CommandApplication(requireLogged = true)
    private static class LoggedApplication {
    }

    @QueryApplication
    private static class UnRequiredLoggedApplication {
    }

    @QueryApplication("authority-1")
    private static class RequireAuthoritiesQueryApplication {
    }

    @CommandApplication({"authority-1", "authority-2"})
    private static class RequireAuthoritiesCommandApplication {
    }
}
