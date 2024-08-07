package es.princip.getp.infra.storage.application;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Path;

public interface FileStorage {

    URI storeFile(InputStream in, Path destination) throws IOException;
}
