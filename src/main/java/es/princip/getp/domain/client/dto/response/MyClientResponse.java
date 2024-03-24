package es.princip.getp.domain.client.dto.response;

import es.princip.getp.domain.client.domain.entity.Client;
import es.princip.getp.domain.member.dto.response.MemberResponse;

public record MyClientResponse(
    MemberResponse member,
    ClientResponse client
) {

    public static MyClientResponse from(final Client client) {
        return new MyClientResponse(
            MemberResponse.from(client.getMember()),
            ClientResponse.from(client));
    }
}
