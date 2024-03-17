package es.princip.getp.domain.people.dto.request;

import java.util.List;
import es.princip.getp.domain.people.dto.PortfolioForm;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record UpdatePeopleProfileRequest(@NotNull String introduction, @NotNull String activityArea,
                                            @NotNull List<String> techStacks, @NotNull String education, @NotNull List<String> hashtags,
                                            @Valid List<PortfolioForm> portfolios) {
}