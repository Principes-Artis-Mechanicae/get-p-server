package es.princip.getp.domain.project.command.presentation;

import es.princip.getp.domain.project.command.application.ProjectService;
import es.princip.getp.infra.support.AbstractControllerTest;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@WebMvcTest(ProjectController.class)
class ProjectControllerTest extends AbstractControllerTest {

    @MockBean
    private ProjectService projectService;
}