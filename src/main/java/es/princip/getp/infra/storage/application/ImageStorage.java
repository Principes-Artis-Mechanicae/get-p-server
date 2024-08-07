package es.princip.getp.infra.storage.application;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Path;

public interface ImageStorage {

    URI storeImage(Path destination, InputStream imageStream) throws IOException;

    void deleteImage(URI destination);
}
