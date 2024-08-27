package es.princip.getp.application.client.port.out;

import es.princip.getp.api.controller.client.query.dto.ClientResponse;
import es.princip.getp.api.controller.project.query.dto.ProjectClientResponse;

public interface ClientQuery {

    ClientResponse findClientById(final Long clientId);

    ClientResponse findClientByMemberId(final Long memberId);

    ProjectClientResponse findProjectClientById(final Long clientId);
}
