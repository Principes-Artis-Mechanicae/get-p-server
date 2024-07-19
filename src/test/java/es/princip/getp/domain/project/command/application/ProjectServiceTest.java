package es.princip.getp.domain.project.command.application;

import es.princip.getp.domain.client.command.domain.ClientRepository;
import es.princip.getp.domain.project.command.domain.ProjectRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ProjectServiceTest {

    @InjectMocks
    private ProjectService projectService;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private ProjectRepository projectRepository;

    @DisplayName("register()는")
    @Nested
    class Register {

        @DisplayName("프로젝트를 등록한다.")
        @Test
        void register() {
        }
    }
}
