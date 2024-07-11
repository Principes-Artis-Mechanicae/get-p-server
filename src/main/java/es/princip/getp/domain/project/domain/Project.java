package es.princip.getp.domain.project.domain;

import es.princip.getp.domain.base.BaseTimeEntity;
import es.princip.getp.domain.client.domain.entity.Client;
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
import jakarta.persistence.Transient;
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

    // 관심 수
    //TODO: 관심 수를 계산하는 로직 구현 필요
    @Transient
    private int interestsCount;

    // 첨부 파일 목록
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProjectAttachmentFile> attachmentFiles = new ArrayList<>();

    // 해시태그 목록
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProjectHashtag> hashtags = new ArrayList<>();

    @Builder
    public Project(
        final Long projectId,
        final MeetingType meetingType,
        final String title,
        final String description,
        final Long payment,
        final LocalDate applicationDeadline,
        final LocalDate estimatedStartDate,
        final LocalDate estimatedEndDate,
        final List<String> attachmentUris,
        final List<String> hashtags,
        final Client client) {
        this.projectId = projectId;
        this.meetingType = meetingType;
        this.title = title;
        this.description = description;
        this.payment = payment;
        this.estimatedDuration = EstimatedDuration.from(estimatedStartDate, estimatedEndDate);
        this.applicationDeadline = ApplicationDeadline.from(applicationDeadline, estimatedDuration);
        this.attachmentFiles.addAll(attachmentUris.stream()
            .map(attachmentUri -> ProjectAttachmentFile.of(this, attachmentUri))
            .toList());
        this.hashtags.addAll(hashtags.stream()
            .map(hashtag -> ProjectHashtag.of(this, hashtag))
            .toList());
        this.client = client;
    }
}
