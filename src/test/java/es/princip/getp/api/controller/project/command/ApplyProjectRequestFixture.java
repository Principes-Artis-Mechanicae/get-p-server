package es.princip.getp.api.controller.project.command;

import es.princip.getp.api.controller.project.command.dto.request.ApplyProjectAsIndividualRequest;
import es.princip.getp.api.controller.project.command.dto.request.ApplyProjectAsTeamRequest;
import es.princip.getp.api.controller.project.command.dto.request.ApplyProjectRequest;
import es.princip.getp.domain.common.model.Duration;
import es.princip.getp.domain.project.apply.model.IndividualProjectApplication;
import es.princip.getp.domain.project.apply.model.TeamProjectApplication;

import java.time.LocalDate;
import java.util.List;

import static es.princip.getp.fixture.project.ProjectApplicationFixture.DESCRIPTION;

class ApplyProjectRequestFixture {

    static ApplyProjectRequest applyProjectAsIndividualRequest() {
        return new ApplyProjectAsIndividualRequest(
            IndividualProjectApplication.TYPE,
            Duration.of(
                LocalDate.of(2024, 7, 1),
                LocalDate.of(2024, 7, 31)
            ),
            DESCRIPTION,
            List.of(
                "https://example.com/attachment1",
                "https://example.com/attachment2"
            )
        );
    }

    static ApplyProjectRequest applyProjectAsTeamRequest() {
        return new ApplyProjectAsTeamRequest(
            TeamProjectApplication.TYPE,
            Duration.of(
                LocalDate.of(2024, 7, 1),
                LocalDate.of(2024, 7, 31)
            ),
            DESCRIPTION,
            List.of(
                "https://example.com/attachment1",
                "https://example.com/attachment2"
            ),
            List.of(1L, 2L, 3L, 4L)
        );
    }
}
