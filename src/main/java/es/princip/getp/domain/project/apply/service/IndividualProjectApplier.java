package es.princip.getp.domain.project.apply.service;

import es.princip.getp.domain.people.exception.NotRegisteredPeopleProfileException;
import es.princip.getp.domain.people.model.People;
import es.princip.getp.domain.project.apply.exception.ClosedProjectApplicationException;
import es.princip.getp.domain.project.apply.model.IndividualProjectApplication;
import es.princip.getp.domain.project.apply.model.ProjectApplication;
import es.princip.getp.domain.project.apply.model.ProjectApplicationData;
import es.princip.getp.domain.project.commission.model.Project;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class IndividualProjectApplier {

    public ProjectApplication apply(
        final People applicant,
        final Project project,
        final ProjectApplicationData data
    ) {
        if (project.isApplicationClosed()) {
            throw new ClosedProjectApplicationException();
        }
        if (!applicant.isProfileRegistered()) {
            throw new NotRegisteredPeopleProfileException();
        }
        return IndividualProjectApplication.of(applicant.getId(), project.getId(), data);
    }
}
