package es.princip.getp.domain.project.command.domain;

import es.princip.getp.domain.client.command.domain.Client;
import es.princip.getp.domain.common.domain.BaseTimeEntity;
import es.princip.getp.domain.common.domain.Duration;
import es.princip.getp.domain.common.domain.Hashtag;
import es.princip.getp.domain.like.command.domain.LikeReceivable;
import jakarta.persistence.*;
import lombok.*;

import java.time.Clock;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@AllArgsConstructor
@Table(name = "project")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Project extends BaseTimeEntity implements LikeReceivable {

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
    private Duration applicationDuration;

    // 예상 작업 기간
    @Embedded
    @AttributeOverrides(
        {
            @AttributeOverride(name = "startDate", column = @Column(name = "estimated_start_date")),
            @AttributeOverride(name = "endDate", column = @Column(name = "estimated_end_date"))
        }
    )
    private Duration estimatedDuration;

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
        final String title,
        final Long payment,
        final Duration applicationDuration,
        final Duration estimatedDuration,
        final String description,
        final MeetingType meetingType,
        final ProjectCategory category,
        final ProjectStatus status,
        final Long clientId,
        final List<AttachmentFile> attachmentFiles,
        final List<Hashtag> hashtags
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

    public boolean isApplicationClosed(final Clock clock) {
        return applicationDuration.isEnded(clock) || status != ProjectStatus.APPLYING;
    }

    public boolean isClient(final Client client) {
        return clientId.equals(client.getClientId());
    }

    @Override
    public Long getId() {
        return projectId;
    }
}
