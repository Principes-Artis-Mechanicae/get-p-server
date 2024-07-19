package es.princip.getp.domain.project.command.application;


import es.princip.getp.domain.people.command.domain.PeopleProfileRepository;
import es.princip.getp.domain.people.command.domain.PeopleRepository;
import es.princip.getp.domain.project.command.domain.ProjectApplicationRepository;
import es.princip.getp.domain.project.command.domain.ProjectRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProjectApplicationServiceTest {

    @Mock
    private PeopleRepository peopleRepository;

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private PeopleProfileRepository peopleProfileRepository;

    @Mock
    private ProjectApplicationRepository projectApplicationRepository;

    @InjectMocks
    private ProjectApplicationService projectApplicationService;

    @Nested
    @DisplayName("applyForProject()는")
    class ApplyForProject {

        @DisplayName("프로젝트에 지원한다.")
        @Test
        void applyForProject() {

        }
    }
}