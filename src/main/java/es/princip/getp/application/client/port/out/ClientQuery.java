package es.princip.getp.application.client.port.out;

import es.princip.getp.api.controller.client.query.dto.ClientResponse;
import es.princip.getp.api.controller.project.query.dto.ProjectClientResponse;
import es.princip.getp.domain.member.model.MemberId;

public interface ClientQuery {

    ClientResponse findClientById(final Long clientId);

    ClientResponse findClientBy(final MemberId memberId);

    ProjectClientResponse findProjectClientById(final Long clientId);
}
