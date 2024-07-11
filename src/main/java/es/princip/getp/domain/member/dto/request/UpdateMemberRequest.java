package es.princip.getp.domain.member.dto.request;

import es.princip.getp.domain.client.dto.request.CreateClientRequest;
import es.princip.getp.domain.client.dto.request.UpdateClientRequest;
import es.princip.getp.domain.people.dto.request.CreatePeopleRequest;
import es.princip.getp.domain.people.dto.request.UpdatePeopleRequest;

public record UpdateMemberRequest(
    String nickname,
    String phoneNumber
) {

    public static UpdateMemberRequest from(final CreatePeopleRequest request) {
        return new UpdateMemberRequest(
            request.nickname(),
            request.phoneNumber()
        );
    }

    public static UpdateMemberRequest from(final UpdatePeopleRequest request) {
        return new UpdateMemberRequest(
            request.nickname(),
            request.phoneNumber()
        );
    }

    public static UpdateMemberRequest from(final CreateClientRequest request) {
        return new UpdateMemberRequest(
            request.nickname(),
            request.phoneNumber()
        );
    }

    public static UpdateMemberRequest from(final UpdateClientRequest request) {
        return new UpdateMemberRequest(
            request.nickname(),
            request.phoneNumber()
        );
    }
}
