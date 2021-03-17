package com.mouse.framework.jwt.verify.application;

import com.mouse.framework.application.QueryApplication;
import com.mouse.framework.domain.core.AuthoritiesSet;
import com.mouse.framework.domain.core.Authority;
import com.mouse.framework.security.Token;
import com.mouse.framework.security.TokenParser;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(classes = TestApplication.class)
@Import({FilterTest.TestQueryApplication.class, FilterTest.TestController.class})
public class FilterTest {
    @Resource
    private MockMvc mockMvc;
    @MockBean
    private TokenParser tokenParser;

    @Test
    void should_be_able_to_load_token_with_request() throws Exception {
        Token token = mock(Token.class);
        given(token.getAuthorities()).willReturn(new AuthoritiesSet(new Authority("authority-1")));
        given(tokenParser.parse("mock-token")).willReturn(Optional.of(token));
        MockHttpServletRequestBuilder requestBuilder = get("/get")
                .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", "mock-token"));

        ResultActions actions = mockMvc.perform(requestBuilder);

        actions.andExpect(status().isOk());
    }

    @RestController
    public static class TestController {
        @Resource
        private TestQueryApplication testQueryApplication;

        @GetMapping("/get")
        public void get() {
            testQueryApplication.execute();
        }
    }

    @QueryApplication("authority-1")
    public static class TestQueryApplication {
        public void execute() {
        }
    }
}
