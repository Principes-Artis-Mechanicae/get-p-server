package es.princip.getp.domain.project.command.infra;

import es.princip.getp.domain.common.domain.Duration;
import es.princip.getp.domain.common.domain.URL;
import es.princip.getp.domain.common.infra.URLMapperImpl;
import es.princip.getp.domain.project.command.application.command.ApplyProjectCommand;
import es.princip.getp.domain.project.command.domain.AttachmentFile;
import es.princip.getp.domain.project.command.presentation.dto.request.ApplyProjectRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ProjectMapperImpl.class, URLMapperImpl.class})
class ProjectMapperTest {

    @Autowired
    private ProjectMapper projectMapper;

    @Test
    void toCommand() {
        final ApplyProjectRequest request = new ApplyProjectRequest(
            Duration.of(
                LocalDate.of(2024, 1, 1),
                LocalDate.of(2024, 1, 2)
            ),
            "description",
            List.of("https://url1.com", "https://url2.com")
        );
        final ApplyProjectCommand command = projectMapper.toCommand(1L, 1L, request);

        assertThat(command).usingRecursiveComparison().isNotNull();
    }

    @Test
    void stringToAttachmentFile() {
        final URL url = new URL("https://url.com");
        final AttachmentFile attachmentFile = projectMapper.stringToAttachmentFile(url);
        
        assertThat(attachmentFile).isNotNull();
    }
}