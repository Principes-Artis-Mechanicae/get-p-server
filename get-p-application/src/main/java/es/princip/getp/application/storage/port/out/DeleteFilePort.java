package es.princip.getp.application.storage.port.out;

import java.net.URI;

public interface DeleteFilePort {

    void delete(URI destination);
}
