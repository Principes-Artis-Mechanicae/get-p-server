package es.princip.getp.domain.project.entity;

import es.princip.getp.domain.base.BaseTimeEntity;
import es.princip.getp.domain.client.entity.Client;
import es.princip.getp.domain.hashtag.entity.Hashtag;
import es.princip.getp.domain.project.enums.MeetingType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
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
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "project")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    // 지원자 모집 마감일
    @Embedded
    private ApplicationDeadline applicationDeadline;

    // 예상 작업 기간
    @Embedded
    private EstimatedDuration estimatedDuration;

    // 상세 설명
    @Column(name = "description")
    private String description;

    // 미팅 유형
    @Column(name = "meeting_type")
    @Enumerated(EnumType.STRING)
    private MeetingType meetingType;

    // 의뢰자
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private Client client;

    // 첨부 파일 목록
    @ElementCollection
    @CollectionTable(name = "attachment_files", joinColumns = @JoinColumn(name = "project_id"))
    @Column(name = "attachment_files_id")
    private List<AttachmentFile> attachmentFiles = new ArrayList<>();

    // 해시태그 목록
    @ElementCollection
    @CollectionTable(name = "project_hashtags", joinColumns = @JoinColumn(name = "project_id"))
    @Column(name = "hashtags_id")
    private List<Hashtag> hashtags = new ArrayList<>();

    @Builder
    public Project(final MeetingType meetingType,
        final String title,
        final String description,
        final Long payment,
        final LocalDate applicationDeadline,
        final LocalDate estimatedStartDate,
        final LocalDate estimatedEndDate,
        final List<String> attachmentUris,
        final List<String> hashtags,
        final Client client) {
        this.meetingType = meetingType;
        this.title = title;
        this.description = description;
        this.payment = payment;
        this.estimatedDuration = EstimatedDuration.from(estimatedStartDate, estimatedEndDate);
        this.applicationDeadline = ApplicationDeadline.from(applicationDeadline, estimatedDuration);
        this.attachmentFiles.addAll(attachmentUris.stream()
            .map(AttachmentFile::from)
            .toList());
        this.hashtags.addAll(hashtags.stream()
            .map(Hashtag::from)
            .toList());
        this.client = client;
    }
}
