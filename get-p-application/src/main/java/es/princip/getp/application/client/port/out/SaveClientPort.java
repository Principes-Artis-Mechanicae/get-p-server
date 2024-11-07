package es.princip.getp.application.client.port.out;

import es.princip.getp.domain.client.model.Client;
import es.princip.getp.domain.client.model.ClientId;

public interface SaveClientPort {

    ClientId save(Client client);
}
