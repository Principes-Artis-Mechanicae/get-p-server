package es.princip.getp.api.controller.project.query.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import es.princip.getp.domain.common.model.Duration;
import es.princip.getp.domain.project.commission.model.MeetingType;
import es.princip.getp.domain.project.commission.model.ProjectCategory;
import es.princip.getp.domain.project.commission.model.ProjectStatus;
import lombok.Getter;

import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Getter
public class ProjectDetailResponse {
    private final Long projectId;
    private final String title;
    private final Long payment;
    private final Long recruitmentCount;
    private final Long applicantsCount;
    private final Duration applicationDuration;
    private final Duration estimatedDuration;
    private String description;
    private final MeetingType meetingType;
    private final ProjectCategory category;
    private final ProjectStatus status;
    private List<String> attachmentFiles;
    private final List<String> hashtags; 
    private final Long likesCount;
    @JsonInclude(NON_NULL) private final Boolean liked;
    private ProjectClientResponse client;

    public ProjectDetailResponse(
        final Long projectId,
        final String title,
        final Long payment,
        final Long recruitmentCount,
        final Long applicantsCount,
        final Duration applicationDuration,
        final Duration estimatedDuration,
        final String description,
        final MeetingType meetingType,
        final ProjectCategory category,
        final ProjectStatus status,
        final List<String> attachmentFiles,
        final List<String> hashtags,
        final Long likesCount,
        final Boolean liked,
        final ProjectClientResponse client
    ) {
        this.projectId = projectId;
        this.title = title;
        this.payment = payment;
        this.recruitmentCount = recruitmentCount;
        this.applicantsCount = applicantsCount;
        this.applicationDuration = applicationDuration;
        this.estimatedDuration = estimatedDuration;
        this.description = description;
        this.meetingType = meetingType;
        this.category = category;
        this.status = status;
        this.attachmentFiles = attachmentFiles;
        this.hashtags = hashtags;
        this.likesCount = likesCount;
        this.liked = liked;
        this.client = client;
    }

    public void mosaic(
        final String description,
        final List<String> attachmentFiles,
        final ProjectClientResponse client
    ) {
        this.description = description;
        this.attachmentFiles = attachmentFiles;
        this.client = client;
    }
}