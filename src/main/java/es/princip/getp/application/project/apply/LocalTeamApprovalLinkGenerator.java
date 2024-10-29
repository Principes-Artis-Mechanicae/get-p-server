package es.princip.getp.application.project.apply;

import es.princip.getp.domain.people.model.PeopleId;
import es.princip.getp.domain.project.apply.model.ProjectApplicationId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("local")
class LocalTeamApprovalLinkGenerator implements TeamApprovalLinkGenerator {

    private final String port;
    private final String contextPath;
    private final TeamApprovalTokenService tokenService;

    @Autowired
    public LocalTeamApprovalLinkGenerator(
        final TeamApprovalTokenService tokenService,
        @Value("${server.port}") final String port,
        @Value("${server.servlet.context-path}") final String contextPath
    ) {
        this.tokenService = tokenService;
        this.port = port;
        this.contextPath = contextPath;
    }

    public String generate(final ProjectApplicationId applicationId, final PeopleId teammateId) {
        final String token = tokenService.generate(applicationId, teammateId);
        return String.format("http://localhost:%s%s/teammates/approve?token=%s", port, contextPath, token);
    }
}
