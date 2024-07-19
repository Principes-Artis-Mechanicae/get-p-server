package es.princip.getp.infra.support;

import com.fasterxml.jackson.databind.ObjectMapper;
import es.princip.getp.infra.config.SecurityConfig;
import es.princip.getp.infra.config.SecurityTestConfig;
import es.princip.getp.infra.config.SpringRestDocsConfig;
import es.princip.getp.infra.exception.ErrorCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@Import({SecurityConfig.class, SecurityTestConfig.class, SpringRestDocsConfig.class})
@ExtendWith({RestDocumentationExtension.class, PrincipalDetailsParameterResolver.class})
public abstract class AbstractControllerTest {

    @Value("${server.servlet.context-path}")
    protected String contextPath;

    protected MockHttpServletRequestBuilder get(String uri, Object... values) {
        return RestDocumentationRequestBuilders.get(contextPath + uri, values)
            .contextPath(contextPath)
            .contentType(MediaType.APPLICATION_JSON);
    }

    protected MockHttpServletRequestBuilder post(String uri, Object... values) {
        return RestDocumentationRequestBuilders.post(contextPath + uri, values)
            .contextPath(contextPath)
            .contentType(MediaType.APPLICATION_JSON);
    }

    protected MockHttpServletRequestBuilder put(String uri, Object... values) {
        return RestDocumentationRequestBuilders.put(contextPath + uri, values)
            .contextPath(contextPath)
            .contentType(MediaType.APPLICATION_JSON);
    }

    protected static ResultMatcher errorCode(ErrorCode errorCode) {
        return status().is(errorCode.status().value());
    }

    @Autowired
    protected RestDocumentationResultHandler restDocs;

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @BeforeEach
    void setUp(final WebApplicationContext context, final RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(documentationConfiguration(restDocumentation).uris()
                    .withScheme("https")
                    .withHost("api.princip.es")
                    .withPort(443)
                )
                .alwaysDo(MockMvcResultHandlers.print())
                .alwaysDo(restDocs)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .build();
    }

    protected static void expectForbidden(ResultActions result) throws Exception {
        result
            .andExpect(status().isForbidden())
            .andDo(print());
    }
}