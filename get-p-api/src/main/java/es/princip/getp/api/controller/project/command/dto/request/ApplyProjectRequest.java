package es.princip.getp.api.controller.project.command.dto.request;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import es.princip.getp.domain.common.model.Duration;
import es.princip.getp.domain.project.apply.model.IndividualProjectApplication;
import es.princip.getp.domain.project.apply.model.TeamProjectApplication;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.EXISTING_PROPERTY,
    property = "type",
    visible = true
)
@JsonSubTypes({
    @JsonSubTypes.Type(
        value = ApplyProjectAsIndividualRequest.class,
        name = IndividualProjectApplication.TYPE
    ),
    @JsonSubTypes.Type(
        value = ApplyProjectAsTeamRequest.class,
        name = TeamProjectApplication.TYPE
    )
})
public abstract class ApplyProjectRequest {

    private final @NotNull String type;
    private final @NotNull Duration expectedDuration;
    private final @NotNull String description;
    private final @NotNull List<@NotNull String> attachmentFiles;
}