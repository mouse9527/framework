package com.mouse.framework.test.application;

import com.mouse.framework.application.CommandApplication;
import com.mouse.framework.application.QueryApplication;
import org.junit.jupiter.api.Test;

import static com.mouse.framework.test.application.ApplicationAssertions.assertApplication;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class ApplicationAssertionsTest {

    @Test
    void should_be_able_to_assert_logged() {
        Throwable throwable = catchThrowable(() -> assertApplication(LoggedApplication.class).requiredLogged());

        assertThat(throwable).isNull();

        Throwable unLogged = catchThrowable(() -> assertApplication(UnRequiredLoggedApplication.class).requiredLogged());

        assertThat(unLogged).isNotNull();
        assertThat(unLogged).isInstanceOf(AssertionError.class);
        assertThat(unLogged).hasMessage("Please set requireLogged = true");

        Throwable nonApplication = catchThrowable(() -> assertApplication(NonApplication.class).requiredLogged());

        assertThat(nonApplication).isNotNull();
        assertThat(nonApplication).isInstanceOf(AssertionError.class);
        assertThat(nonApplication).hasMessage("No CommandApplication or QueryApplication annotation");
    }

    @Test
    void should_be_able_to_assert_required_authorities() {
        Throwable throwable = catchThrowable(() -> assertApplication(RequireAuthoritiesQueryApplication.class).requiredAuthorities("authority-1"));

        assertThat(throwable).isNull();

        Throwable commandApplication = catchThrowable(() -> assertApplication(RequireAuthoritiesCommandApplication.class).requiredAuthorities("authority-1", "authority-2"));

        assertThat(commandApplication).isNull();

        Throwable notEquallyAuthorities = catchThrowable(() -> assertApplication(RequireAuthoritiesCommandApplication.class).requiredAuthorities("authority-1"));

        assertThat(notEquallyAuthorities).isNotNull();
        assertThat(notEquallyAuthorities).isInstanceOf(AssertionError.class);

        Throwable nonApplication = catchThrowable(() -> assertApplication(NonApplication.class).requiredAuthorities());

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
