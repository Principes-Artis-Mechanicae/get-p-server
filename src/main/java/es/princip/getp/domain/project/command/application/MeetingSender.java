package es.princip.getp.domain.project.command.application;

import es.princip.getp.domain.people.command.domain.People;
import es.princip.getp.domain.project.command.presentation.dto.request.ApplyProjectMeetingRequest;

public interface MeetingSender {
    void send(People people, ApplyProjectMeetingRequest request);
}
