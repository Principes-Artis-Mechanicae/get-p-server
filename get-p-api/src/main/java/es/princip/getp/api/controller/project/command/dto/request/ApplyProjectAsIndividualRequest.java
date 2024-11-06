package es.princip.getp.api.controller.project.command.dto.request;

import es.princip.getp.domain.common.model.Duration;

import java.util.List;

public class ApplyProjectAsIndividualRequest extends ApplyProjectRequest {

    public ApplyProjectAsIndividualRequest(
        final String type,
        final Duration expectedDuration,
        final String description,
        final List<String> attachmentFiles
    ) {
        super(type, expectedDuration, description, attachmentFiles);
    }
}