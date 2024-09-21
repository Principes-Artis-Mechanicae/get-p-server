package es.princip.getp.persistence.adapter.project.apply;

import es.princip.getp.domain.project.apply.model.IndividualProjectApplication;
import es.princip.getp.domain.project.apply.model.ProjectApplicationStatus;
import es.princip.getp.persistence.adapter.common.DurationJpaVO;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "individual_project_application")
@DiscriminatorValue(IndividualProjectApplication.TYPE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
class IndividualProjectApplicationJpaEntity extends ProjectApplicationJpaEntity {

    public IndividualProjectApplicationJpaEntity(
        final Long id,
        final Long applicantId,
        final Long projectId,
        final DurationJpaVO expectedDuration,
        final ProjectApplicationStatus status,
        final String description,
        final List<String> attachmentFiles
    ) {
        super(
            id,
            applicantId,
            projectId,
            expectedDuration,
            status,
            description,
            attachmentFiles
        );
    }
}
