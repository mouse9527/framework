package com.mouse.framework.test.application;


import com.mouse.framework.application.CommandApplication;
import com.mouse.framework.application.QueryApplication;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

public final class ApplicationAssert {
    private ApplicationAssert() {
    }

    public static void assertRequiredLogged(Class<?> clazz) {
        QueryApplication annotation = clazz.getAnnotation(QueryApplication.class);
        if (Objects.nonNull(annotation)) {
            requiredLogged(annotation.requireLogged());
            return;
        }
        CommandApplication commandApplication = clazz.getAnnotation(CommandApplication.class);
        assert commandApplication != null : "No CommandApplication or QueryApplication annotation";
        boolean requireLogged = commandApplication.requireLogged();
        requiredLogged(requireLogged);
    }

    public static void assertRequiredAuthorities(Class<?> clazz, String... authorities) {
        QueryApplication queryApplication = clazz.getAnnotation(QueryApplication.class);
        if (Objects.nonNull(queryApplication)) {
            String[] requireAuthorities = queryApplication.requireAuthorities();
            if (requireAuthorities.length == 1 && requireAuthorities[0].equals("")) {
                requireAuthorities = queryApplication.value();
            }
            assertThat(requireAuthorities).isEqualTo(authorities);
            return;
        }
        CommandApplication commandApplication = clazz.getAnnotation(CommandApplication.class);
        assert commandApplication != null : "No CommandApplication or QueryApplication annotation";
        String[] requireAuthorities = commandApplication.requireAuthorities();
        if (requireAuthorities.length == 1 && requireAuthorities[0].equals("")) {
            requireAuthorities = commandApplication.value();
        }
        assertThat(requireAuthorities).isEqualTo(authorities);
    }

    private static void requiredLogged(boolean requireLogged) {
        assert requireLogged : "Please set requireLogged = true";
    }
}
