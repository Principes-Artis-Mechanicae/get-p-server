package es.princip.getp.persistence.adapter.project.apply.model;

import es.princip.getp.domain.project.apply.model.IndividualProjectApplication;
import es.princip.getp.domain.project.apply.model.ProjectApplicationStatus;
import es.princip.getp.persistence.adapter.common.DurationJpaVO;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "individual_project_application")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue(IndividualProjectApplication.TYPE)
public class IndividualProjectApplicationJpaEntity extends ProjectApplicationJpaEntity {

    @Builder
    public IndividualProjectApplicationJpaEntity(
        final Long id,
        final Long applicantId,
        final Long projectId,
        final DurationJpaVO expectedDuration,
        final ProjectApplicationStatus status,
        final String description,
        final List<String> attachmentFiles,
        final LocalDateTime createdAt,
        final LocalDateTime updatedAt
    ) {
        super(
            id,
            applicantId,
            projectId,
            expectedDuration,
            status,
            description,
            attachmentFiles,
            createdAt,
            updatedAt
        );
    }
}
