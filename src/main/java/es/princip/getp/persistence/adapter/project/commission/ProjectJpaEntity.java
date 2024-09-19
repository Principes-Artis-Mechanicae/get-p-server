package es.princip.getp.persistence.adapter.project.commission;

import es.princip.getp.domain.project.commission.model.MeetingType;
import es.princip.getp.domain.project.commission.model.ProjectCategory;
import es.princip.getp.domain.project.commission.model.ProjectStatus;
import es.princip.getp.persistence.adapter.BaseTimeJpaEntity;
import es.princip.getp.persistence.adapter.common.DurationJpaVO;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Builder
@AllArgsConstructor
@Table(name = "project")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProjectJpaEntity extends BaseTimeJpaEntity {

    @Id
    @Column(name = "project_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "payment")
    private Long payment;

    @Embedded
    @AttributeOverrides(
        {
            @AttributeOverride(name = "startDate", column = @Column(name = "application_start_date")),
            @AttributeOverride(name = "endDate", column = @Column(name = "application_end_date"))
        }
    )
    private DurationJpaVO applicationDuration;

    @Embedded
    @AttributeOverrides(
        {
            @AttributeOverride(name = "startDate", column = @Column(name = "estimated_start_date")),
            @AttributeOverride(name = "endDate", column = @Column(name = "estimated_end_date"))
        }
    )
    private DurationJpaVO estimatedDuration;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "meeting_type")
    private MeetingType meetingType;

    @Column(name = "category")
    @Enumerated(EnumType.STRING)
    private ProjectCategory category;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ProjectStatus status;

    @Column(name = "client_id")
    private Long clientId;

    @Builder.Default
    @ElementCollection
    @CollectionTable(name = "project_attachment_file", joinColumns = @JoinColumn(name = "project_id"))
    private List<String> attachmentFiles = new ArrayList<>();

    @Builder.Default
    @ElementCollection
    @CollectionTable(name = "project_hashtag", joinColumns = @JoinColumn(name = "project_id"))
    private List<String> hashtags = new ArrayList<>();
}
