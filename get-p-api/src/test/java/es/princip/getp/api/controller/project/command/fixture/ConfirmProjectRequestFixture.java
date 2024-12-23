package es.princip.getp.api.controller.project.command.fixture;

import es.princip.getp.api.controller.project.command.dto.request.ConfirmProjectRequest;

public class ConfirmProjectRequestFixture {
    public static ConfirmProjectRequest confirmProjectRequest() {
        return new ConfirmProjectRequest(1L);
    }
}
