package com.mouse.framework.test.application;


import com.mouse.framework.application.CommandApplication;
import com.mouse.framework.application.QueryApplication;
import lombok.Generated;

import java.util.Optional;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;

public final class ApplicationAssertions {
    private ApplicationAssertions() {
    }

    public static ApplicationAssert assertApplication(Class<?> clazz) {
        return getQueryApplication(clazz).orElseGet(getCommandApplicationAssert(clazz));
    }

    private static Optional<ApplicationAssert> getQueryApplication(Class<?> clazz) {
        return Optional.ofNullable(clazz.getAnnotation(QueryApplication.class))
                .map(it -> new ApplicationAssert(it.requireLogged(), it.value(), it.requireAuthorities()));
    }

    private static Supplier<ApplicationAssert> getCommandApplicationAssert(Class<?> clazz) {
        return () -> Optional.ofNullable(clazz.getAnnotation(CommandApplication.class))
                .map(it -> new ApplicationAssert(it.requireLogged(), it.value(), it.requireAuthorities()))
                .orElseGet(ApplicationAssert::new);
    }

    @Generated
    public static final class ApplicationAssert {
        private final boolean required;
        private final String[] value;
        private final String[] authorities;
        private final boolean hasAnnotation;

        private ApplicationAssert(Boolean required, String[] value, String[] authorities) {
            this.required = required;
            this.value = value;
            this.authorities = authorities;
            this.hasAnnotation = true;
        }

        private ApplicationAssert() {
            this.required = false;
            this.value = null;
            this.authorities = null;
            this.hasAnnotation = false;
        }

        public void requiredLogged() {
            requireHasAnnotation();
            assert required : "Please set requireLogged = true";
        }

        public void requiredAuthorities(String... authorities) {
            requireHasAnnotation();
            assertThat(getActualAuthorities()).isEqualTo(authorities);
        }

        private String[] getActualAuthorities() {
            // value must not be null
            //noinspection ConstantConditions
            if (value.length == 1 && value[0].equals("")) {
                return this.authorities;
            }
            return value;
        }

        private void requireHasAnnotation() {
            assert hasAnnotation : "No CommandApplication or QueryApplication annotation";
        }
    }
}
