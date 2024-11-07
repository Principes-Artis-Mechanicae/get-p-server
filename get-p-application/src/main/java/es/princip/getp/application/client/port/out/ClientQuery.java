package es.princip.getp.application.client.port.out;

import es.princip.getp.application.client.dto.response.ClientResponse;
import es.princip.getp.application.project.commission.dto.response.ProjectClientResponse;
import es.princip.getp.domain.client.model.ClientId;
import es.princip.getp.domain.member.model.MemberId;

public interface ClientQuery {

    ClientResponse findClientBy(final ClientId clientId);
    ClientResponse findClientBy(final MemberId memberId);
    ProjectClientResponse findProjectClientBy(final ClientId clientId);
}
