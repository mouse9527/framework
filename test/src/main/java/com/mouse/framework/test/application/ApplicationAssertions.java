package com.mouse.framework.test.application;


import com.mouse.framework.application.CommandApplication;
import com.mouse.framework.application.QueryApplication;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

public final class ApplicationAssertions {
    private ApplicationAssertions() {
    }

    public static ApplicationAssert assertApplication(Class<?> clazz) {
        QueryApplication queryApplication = clazz.getAnnotation(QueryApplication.class);
        boolean required;
        String[] authorities;
        String[] value;
        if (Objects.isNull(queryApplication)) {
            CommandApplication commandApplication = clazz.getAnnotation(CommandApplication.class);
            if (Objects.isNull(commandApplication)) {
                return new ApplicationAssert(null, null, null);
            }
            required = commandApplication.requireLogged();
            value = commandApplication.value();
            authorities = commandApplication.requireAuthorities();
        } else {
            required = queryApplication.requireLogged();
            value = queryApplication.value();
            authorities = queryApplication.requireAuthorities();
        }
        return new ApplicationAssert(required, value, authorities);
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
