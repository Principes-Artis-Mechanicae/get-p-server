package es.princip.getp.api.support;

import com.fasterxml.jackson.databind.ObjectMapper;
import es.princip.getp.api.config.MockDaoBeanFactoryPostProcessor;
import es.princip.getp.api.config.MockServiceBeanFactoryPostProcessor;
import es.princip.getp.api.security.PrincipalDetailsParameterResolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;

@WebMvcTest
@ComponentScan("es.princip.getp.api")
@Execution(ExecutionMode.SAME_THREAD)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Import({
    SecurityConfig.class,
    MockDaoBeanFactoryPostProcessor.class,
    MockServiceBeanFactoryPostProcessor.class,
})
@ExtendWith({RestDocumentationExtension.class, PrincipalDetailsParameterResolver.class})
public abstract class ControllerTest {

    @Value("${server.servlet.context-path}")
    protected String contextPath;

    private MockHttpServletRequestBuilder contextPathAndContentType(final MockHttpServletRequestBuilder builder) {
        return builder.contextPath(contextPath).contentType(MediaType.APPLICATION_JSON);
    }

    protected MockHttpServletRequestBuilder get(final String uri, final Object... values) {
        return contextPathAndContentType(RestDocumentationRequestBuilders.get(contextPath + uri, values));
    }

    protected MockHttpServletRequestBuilder post(final String uri, final Object... values) {
        return contextPathAndContentType(RestDocumentationRequestBuilders.post(contextPath + uri, values));
    }

    protected MockHttpServletRequestBuilder put(final String uri, final Object... values) {
        return contextPathAndContentType(RestDocumentationRequestBuilders.put(contextPath + uri, values));
    }

    protected MockHttpServletRequestBuilder delete(final String uri, final Object... values) {
        return contextPathAndContentType(RestDocumentationRequestBuilders.delete(contextPath + uri, values));
    }

    protected MockMultipartHttpServletRequestBuilder multipart(final String uri, final Object... values) {
        return (MockMultipartHttpServletRequestBuilder) RestDocumentationRequestBuilders
            .multipart(contextPath + uri, values)
            .contextPath(contextPath);
    }

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @BeforeEach
    void setUp(final WebApplicationContext context, final RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(documentationConfiguration(restDocumentation)
                    .operationPreprocessors()
                    .withRequestDefaults(prettyPrint())
                    .withResponseDefaults(prettyPrint())
                    .and()
                    .uris()
                    .withScheme("https")
                    .withHost("api.principes.xyz")
                    .withPort(443)
                )
                .alwaysDo(MockMvcResultHandlers.print())
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .build();
    }
}
