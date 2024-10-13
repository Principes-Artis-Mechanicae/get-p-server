package es.princip.getp.application.project.commission;

import es.princip.getp.api.controller.project.query.dto.ProjectDetailResponse;
import es.princip.getp.domain.project.commission.model.ProjectId;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;

import java.util.Locale;

import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static es.princip.getp.fixture.project.ProjectQueryResponseFixture.projectDetailResponse;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ProjectDetailResponseMosaicResolverTest {
    
    @Mock private MessageSource messageSource;
    @InjectMocks private ProjectDetailResponseMosaicResolver resolver;

    private static final String MESSAGE = "로그인 후 이용해주세요.";
    private final ProjectId projectId = new ProjectId(1L);
    private final ProjectDetailResponse response = projectDetailResponse(projectId);

    @BeforeEach
    void setup() {
        given(messageSource.getMessage("restricted.access", null, Locale.getDefault()))
            .willReturn(MESSAGE);
    }

    @Test
    void 프로젝트_상세_정보를_모자이크_한다() {
        final ProjectDetailResponse mosaicResponse = resolver.resolve(response);

        assertSoftly(projectDetailResponse -> {
            projectDetailResponse.assertThat(mosaicResponse.getDescription()).hasSameSizeAs(response.getDescription());
            projectDetailResponse.assertThat(mosaicResponse.getAttachmentFiles())
            .extracting(String::length)
            .containsExactlyElementsOf(response.getAttachmentFiles()
                .stream()
                .map(String::length)
                .toList());
            assertSoftly(client -> {
                client.assertThat(mosaicResponse.getClient().clientId()).isEqualTo(null);
                client.assertThat(mosaicResponse.getClient().nickname()).hasSameSizeAs(response.getClient().nickname());
                assertSoftly(address -> {
                    address.assertThat(mosaicResponse.getClient().address().zipcode()).hasSameSizeAs(response.getClient().address().zipcode());
                    address.assertThat(mosaicResponse.getClient().address().detail()).hasSameSizeAs(response.getClient().address().detail());
                    address.assertThat(mosaicResponse.getClient().address().street()).hasSameSizeAs(response.getClient().address().street());
                });
            });
        });
    }
}
