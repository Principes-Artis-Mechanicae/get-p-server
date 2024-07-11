package es.princip.getp.domain.project.domain;

import es.princip.getp.domain.base.BaseTimeEntity;
import es.princip.getp.domain.people.domain.People;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "project_application")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProjectApplication extends BaseTimeEntity {

    @Id
    @Column(name = "project_application_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "people_id")
    private People people;

    // 희망 작업 기간
    @Embedded
    private ExpectedDuration expectedDuration;

    // 지원 내용
    @Column(name = "description")
    private String description;

    // 첨부 파일 목록
    @OneToMany(mappedBy = "projectApplication", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProjectApplicationAttachmentFile> attachmentFiles = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ProjectApplicationStatus status;

    @Builder
    public ProjectApplication(
        Project project,
        People people,
        LocalDate expectedStartDate,
        LocalDate expectedEndDate,
        String description,
        List<String> attachmentUris) {
        this.project = project;
        this.people = people;
        this.expectedDuration = ExpectedDuration.from(expectedStartDate, expectedEndDate);
        this.description = description;
        this.attachmentFiles.addAll(attachmentUris.stream()
            .map(attachmentUri -> ProjectApplicationAttachmentFile.of(this, attachmentUri))
            .toList());
        this.status = ProjectApplicationStatus.APPLICATION_COMPLETED;
    }

    public void updateStatus(ProjectApplicationStatus status) {
        this.status = status;
    }
}
