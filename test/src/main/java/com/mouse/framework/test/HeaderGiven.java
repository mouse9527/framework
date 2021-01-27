package com.mouse.framework.test;

import java.util.Locale;

public interface HeaderGiven {
    void setLanguage(Locale locale);

    void setToken(String token);

    void set(String key, String value);
}
