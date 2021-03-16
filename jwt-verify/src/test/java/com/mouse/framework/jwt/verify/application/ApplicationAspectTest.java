package com.mouse.framework.jwt.verify.application;

import com.mouse.framework.application.CommandApplication;
import com.mouse.framework.application.QueryApplication;
import com.mouse.framework.jwt.verify.ApplicationAspect;
import com.mouse.framework.jwt.verify.ApplicationAspectExecutor;
import com.mouse.framework.jwt.verify.TestApplicationInIllegalPackage;
import org.junit.jupiter.api.Test;
import org.mockito.internal.verification.Times;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import static org.mockito.BDDMockito.then;

@SpringBootTest
@EnableAspectJAutoProxy
@Import({ApplicationAspect.class,
        ApplicationAspectTest.TestRequireLoggedQueryApplication.class,
        TestApplicationInIllegalPackage.class,
        ApplicationAspectTest.TestApplicationWithoutAnnotation.class,
        ApplicationAspectTest.TestApplicationWithCommandApplication.class,
})
public class ApplicationAspectTest {
    @MockBean
    private ApplicationAspectExecutor applicationAspectExecutor;
    @Resource
    private TestRequireLoggedQueryApplication testRequireLoggedQueryApplication;
    @Resource
    private TestApplicationInIllegalPackage testApplicationInIllegalPackage;
    @Resource
    private TestApplicationWithoutAnnotation testApplicationWithoutAnnotation;
    @Resource
    private TestApplicationWithCommandApplication testApplicationWithCommandApplication;

    @Test
    void should_be_able_to_hit_require_logged_query_application() {
        testRequireLoggedQueryApplication.execute();

        then(applicationAspectExecutor).should(new Times(1)).execute(true, new String[]{""});
    }

    @Test
    void should_be_able_to_not_hit_when_not_in_application_package() {
        testApplicationInIllegalPackage.execute();

        then(applicationAspectExecutor).shouldHaveNoInteractions();
    }

    @Test
    void should_be_able_to_not_hit_without_annotation() {
        testApplicationWithoutAnnotation.execute();

        then(applicationAspectExecutor).shouldHaveNoInteractions();
    }

    @Test
    void should_be_able_to_hit_with_command_annotation() {
        testApplicationWithCommandApplication.execute();

        then(applicationAspectExecutor).should(new Times(1)).execute(false, new String[]{""});
    }

    @QueryApplication(requireLogged = true)
    public static class TestRequireLoggedQueryApplication {

        public void execute() {
        }
    }

    @Component
    public static class TestApplicationWithoutAnnotation {
        public void execute() {
        }
    }

    @CommandApplication
    public static class TestApplicationWithCommandApplication {
        public void execute() {
        }
    }
}
