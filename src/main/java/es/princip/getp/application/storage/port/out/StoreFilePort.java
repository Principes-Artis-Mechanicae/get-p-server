package es.princip.getp.application.storage.port.out;

import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.nio.file.Path;

public interface StoreFilePort {

    URI store(MultipartFile file, Path destination);
}
