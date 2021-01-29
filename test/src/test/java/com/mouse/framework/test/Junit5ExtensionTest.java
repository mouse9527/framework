package com.mouse.framework.test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(Extension.class)
@ExtendWith(OtherExtension.class)
public class Junit5ExtensionTest {

    @Test
    void should_be_able_to_only_call_once() {
        assertThat(Extension.callCount.get()).isEqualTo(1);
        assertThat(OtherExtension.callCount.get()).isEqualTo(1);
    }
}

@ExtendWith(Extension.class)
@ExtendWith(OtherExtension.class)
class Junit5ExtensionRepeatTest {

    @Test
    void should_be_able_to_only_call_once() {
        assertThat(Extension.callCount.get()).isEqualTo(1);
        assertThat(OtherExtension.callCount.get()).isEqualTo(1);
    }
}


class Extension implements BeforeAllCallback {
    public static AtomicInteger callCount = new AtomicInteger();

    @Override
    public void beforeAll(ExtensionContext context) {
        if (callCount.get() > 0) {
            return;
        }
        callCount.addAndGet(1);
    }
}

class OtherExtension implements BeforeAllCallback, AfterAllCallback {
    public static AtomicInteger callCount = new AtomicInteger();

    @Override
    public void beforeAll(ExtensionContext context) {
        if (callCount.get() > 0) {
            return;
        }
        callCount.addAndGet(1);
    }

    @Override
    public void afterAll(ExtensionContext context) throws Exception {

    }
}
