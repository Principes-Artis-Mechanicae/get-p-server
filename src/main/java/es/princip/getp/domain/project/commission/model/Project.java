package es.princip.getp.domain.project.commission.model;

import es.princip.getp.domain.support.BaseEntity;
import es.princip.getp.domain.client.model.Client;
import es.princip.getp.domain.common.model.AttachmentFile;
import es.princip.getp.domain.common.model.Duration;
import es.princip.getp.domain.common.model.Hashtag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Getter
public class Project extends BaseEntity {

    private Long projectId;
    @NotBlank private String title; // 제목
    @NotNull private Long payment; // 금액
    @NotNull private Duration applicationDuration; // 지원자 모집 기간
    @NotNull private Duration estimatedDuration; // 예상 작업 기간
    @NotBlank private String description; // 상세 설명
    @NotNull private MeetingType meetingType; // 미팅 유형
    @NotNull private ProjectCategory category; // 프로젝트 카테고리
    @NotNull private ProjectStatus status; // 프로젝트 상태
    @NotNull private final Long clientId; // 의뢰자
    private int interestsCount; // 관심 수
    private final List<@NotNull AttachmentFile> attachmentFiles; // 첨부 파일 목록
    private final List<@NotNull Hashtag> hashtags; // 해시태그 목록

    @Builder
    public Project(
        final Long projectId,
        final String title,
        final Long payment,
        final Duration applicationDuration,
        final Duration estimatedDuration,
        final String description,
        final MeetingType meetingType,
        final ProjectCategory category,
        final ProjectStatus status,
        final Long clientId,
        final int interestsCount,
        final List<AttachmentFile> attachmentFiles,
        final List<Hashtag> hashtags,
        final LocalDateTime createdAt,
        final LocalDateTime updatedAt
    ) {
        super(createdAt, updatedAt);

        this.projectId = projectId;
        this.title = title;
        this.payment = payment;
        this.applicationDuration = applicationDuration;
        this.estimatedDuration = estimatedDuration;
        this.description = description;
        this.meetingType = meetingType;
        this.category = category;
        this.status = status;
        this.clientId = clientId;
        this.interestsCount = interestsCount;
        this.attachmentFiles = attachmentFiles;
        this.hashtags = hashtags;

        validate();
    }

    public List<AttachmentFile> getAttachmentFiles() {
        return Collections.unmodifiableList(attachmentFiles);
    }

    public List<Hashtag> getHashtags() {
        return Collections.unmodifiableList(hashtags);
    }

    public boolean isApplicationClosed(final Clock clock) {
        return applicationDuration.isEnded(clock) || !status.isApplying();
    }

    public boolean isClient(final Client client) {
        return clientId.equals(client.getClientId());
    }
}
