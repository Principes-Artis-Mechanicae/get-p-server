package es.princip.getp.application.storage.port.out;

import es.princip.getp.application.storage.FileLog;

public interface LogFilePort {

    FileLog log(final FileLog fileLog);
}
