package com.mouse.framework.test;

import java.util.Locale;

public interface HeaderGiven {
    void mockLanguage(Locale locale);

    void mockToken(String token);

    void mock(String key, String value);
}
