package es.princip.getp.domain.project.command.domain;

import es.princip.getp.domain.common.domain.BaseTimeEntity;
import es.princip.getp.domain.common.domain.Hashtag;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@AllArgsConstructor
@Table(name = "project")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Project extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id")
    private Long projectId;

    // 제목
    @Column(name = "title")
    private String title;

    // 금액
    @Column(name = "payment")
    private Long payment;

    // 지원자 모집 기간
    @Embedded
    @AttributeOverrides(
        {
            @AttributeOverride(name = "startDate", column = @Column(name = "application_start_date")),
            @AttributeOverride(name = "endDate", column = @Column(name = "application_end_date"))
        }
    )
    private ApplicationDuration applicationDuration;

    // 예상 작업 기간
    @Embedded
    @AttributeOverrides(
        {
            @AttributeOverride(name = "startDate", column = @Column(name = "estimated_start_date")),
            @AttributeOverride(name = "endDate", column = @Column(name = "estimated_end_date"))
        }
    )
    private EstimatedDuration estimatedDuration;

    // 상세 설명
    @Column(name = "description")
    private String description;

    // 미팅 유형
    @Column(name = "meeting_type")
    @Enumerated(EnumType.STRING)
    private MeetingType meetingType;

    // 프로젝트 카테고리
    @Column(name = "category")
    @Enumerated(EnumType.STRING)
    private ProjectCategory category;

    // 프로젝트 상태
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ProjectStatus status;

    // 의뢰자
    @Column(name = "client_id")
    private Long clientId;

    // 관심 수
    //TODO: 관심 수를 계산하는 로직 구현 필요
    @Transient
    private int interestsCount;

    // 첨부 파일 목록
    @ElementCollection
    @CollectionTable(name = "project_attachment_file", joinColumns = @JoinColumn(name = "project_id"))
    private List<AttachmentFile> attachmentFiles = new ArrayList<>();

    // 해시태그 목록
    @ElementCollection
    @CollectionTable(name = "project_hashtag", joinColumns = @JoinColumn(name = "project_id"))
    private List<Hashtag> hashtags = new ArrayList<>();

    @Builder
    public Project(
        String title,
        Long payment,
        ApplicationDuration applicationDuration,
        EstimatedDuration estimatedDuration,
        String description,
        MeetingType meetingType,
        ProjectCategory category,
        ProjectStatus status,
        Long clientId,
        List<AttachmentFile> attachmentFiles,
        List<Hashtag> hashtags
    ) {
        this.title = title;
        this.payment = payment;
        this.applicationDuration = applicationDuration;
        this.estimatedDuration = estimatedDuration;
        this.description = description;
        this.meetingType = meetingType;
        this.category = category;
        this.status = status;
        this.clientId = clientId;
        this.attachmentFiles = attachmentFiles;
        this.hashtags = hashtags;
    }

    public List<AttachmentFile> getAttachmentFiles() {
        return Collections.unmodifiableList(attachmentFiles);
    }

    public List<Hashtag> getHashtags() {
        return Collections.unmodifiableList(hashtags);
    }

    public boolean isApplicationClosed() {
        return applicationDuration.isClosed() || status != ProjectStatus.APPLYING;
    }
}
