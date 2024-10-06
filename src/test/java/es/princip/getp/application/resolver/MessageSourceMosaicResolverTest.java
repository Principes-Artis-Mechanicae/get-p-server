package es.princip.getp.application.resolver;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.Locale;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;

import es.princip.getp.api.controller.project.query.dto.ProjectDetailResponse;
import es.princip.getp.domain.project.commission.model.ProjectId;
import es.princip.getp.fixture.project.ProjectQueryResponseFixture;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class MessageSourceMosaicResolverTest {
    
    @Mock private MessageSource messageSource;
    
    @InjectMocks MessageSourceMosaicResolver resolver;

    private final ProjectId projectId = new ProjectId(1L);

    private final ProjectDetailResponse response = ProjectQueryResponseFixture.projectDetailResponse(projectId);

    @BeforeEach
    void setup() {
        when(messageSource.getMessage("restricted.access", null, Locale.getDefault()))
            .thenReturn("로그인 후 이용해주세요.");
    }

    @Test
    void testResolve() {
        ProjectDetailResponse mosaicResponse = resolver.resolve(response);

        log.info(mosaicResponse.getDescription());
    
        assertNotNull(mosaicResponse);
        assertEquals(mosaicResponse.getDescription().length(), response.getDescription().length());
    }
}
