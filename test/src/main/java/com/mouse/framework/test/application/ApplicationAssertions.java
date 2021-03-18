package com.mouse.framework.test.application;


import com.mouse.framework.application.CommandApplication;
import com.mouse.framework.application.QueryApplication;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public final class ApplicationAssertions {
    private ApplicationAssertions() {
    }

    public static ApplicationAssert assertApplication(Class<?> clazz) {
        QueryApplication queryApplication = clazz.getAnnotation(QueryApplication.class);
        return Optional.ofNullable(queryApplication)
                .map(annotation -> new ApplicationAssert(annotation.requireLogged(), annotation.value(), annotation.requireAuthorities()))
                .or(() -> Optional.ofNullable(clazz.getAnnotation(CommandApplication.class))
                        .map(annotation -> new ApplicationAssert(annotation.requireLogged(), annotation.value(), annotation.requireAuthorities()))
                        .or(() -> Optional.of(new ApplicationAssert()))).get();
    }

    public static class ApplicationAssert {
        private final Boolean required;
        private final String[] value;
        private final String[] authorities;

        public ApplicationAssert(Boolean required, String[] value, String[] authorities) {
            this.required = required;
            this.value = value;
            this.authorities = authorities;
        }

        public ApplicationAssert() {
            this.required = null;
            this.value = null;
            this.authorities = null;
        }

        public void requiredLogged() {
            assert required != null : "No CommandApplication or QueryApplication annotation";
            assert required : "Please set requireLogged = true";
        }

        public void requiredAuthorities(String... authorities) {
            assert value != null || this.authorities != null : "No CommandApplication or QueryApplication annotation";
            String[] actual = value;
            if (value != null && value.length == 1 && value[0].equals("")) {
                actual = this.authorities;
            }
            assertThat(actual).isEqualTo(authorities);
        }
    }
}
