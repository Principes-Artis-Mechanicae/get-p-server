package es.princip.getp.application.client.port.out;

import es.princip.getp.domain.client.model.Client;

public interface SaveClientPort {

    Long save(Client client);
}
