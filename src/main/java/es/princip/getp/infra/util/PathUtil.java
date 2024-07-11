package es.princip.getp.infra.util;

import java.io.File;
import java.net.URI;
import java.nio.file.Path;

public class PathUtil {

    public static Path getRootPath() {
        return new File("").getAbsoluteFile().toPath();
    }

    public static URI toURI(Path path) {
        return URI.create(path.toString().replace("\\", "/"));
    }
}
