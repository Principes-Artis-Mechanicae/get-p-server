package es.princip.getp.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import es.princip.getp.api.controller.people.command.PeopleCommandMapper;
import es.princip.getp.api.controller.project.command.ProjectCommandMapper;
import es.princip.getp.api.docs.SpringRestDocsConfig;
import es.princip.getp.api.security.PrincipalDetailsParameterResolver;
import es.princip.getp.api.security.SecurityConfig;
import es.princip.getp.api.security.SecurityTestConfig;
import es.princip.getp.application.projectMeeting.ProjectApplicationService;
import es.princip.getp.application.projectMeeting.ProjectCommissionService;
import es.princip.getp.application.projectMeeting.ProjectMeetingService;
import es.princip.getp.domain.auth.application.AuthService;
import es.princip.getp.domain.auth.application.SignUpService;
import es.princip.getp.domain.client.command.application.ClientService;
import es.princip.getp.domain.client.query.dao.ClientDao;
import es.princip.getp.domain.like.command.application.PeopleLikeService;
import es.princip.getp.domain.like.command.application.ProjectLikeService;
import es.princip.getp.domain.member.command.application.MemberService;
import es.princip.getp.domain.people.command.application.PeopleProfileService;
import es.princip.getp.domain.people.command.application.PeopleService;
import es.princip.getp.domain.people.query.dao.PeopleDao;
import es.princip.getp.domain.project.query.application.ProjectApplicantService;
import es.princip.getp.domain.project.query.dao.AppliedProjectDao;
import es.princip.getp.domain.project.query.dao.MyCommissionedProjectDao;
import es.princip.getp.domain.project.query.dao.ProjectDao;
import es.princip.getp.domain.serviceTerm.application.ServiceTermService;
import es.princip.getp.storage.application.FileUploadService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@ActiveProfiles("test")
@MockBean({
    AuthService.class,
    SignUpService.class,
    ClientService.class,
    ClientDao.class,
    PeopleLikeService.class,
    ProjectLikeService.class,
    MemberService.class,
    PeopleCommandMapper.class,
    PeopleDao.class,
    PeopleService.class,
    PeopleProfileService.class,
    ProjectCommandMapper.class,
    ProjectApplicationService.class,
    ProjectCommissionService.class,
    ProjectMeetingService.class,
    MyCommissionedProjectDao.class,
    ProjectApplicantService.class,
    ProjectDao.class,
    ServiceTermService.class,
    FileUploadService.class,
    AppliedProjectDao.class
})
@Execution(ExecutionMode.SAME_THREAD)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Import({SecurityConfig.class, SecurityTestConfig.class, SpringRestDocsConfig.class})
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
                    .withHost("api.principes.xyz")
                    .withPort(443)
                )
                .alwaysDo(MockMvcResultHandlers.print())
                .alwaysDo(restDocs)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .build();
    }

    protected static void expectForbidden(final ResultActions result) throws Exception {
        result
            .andExpect(status().isForbidden())
            .andDo(print());
    }
}
