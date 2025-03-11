package es.princip.getp.application.storage.port.in;

import es.princip.getp.application.storage.dto.command.UploadFileCommand;

import java.net.URI;

public interface UploadFileUseCase {

    URI upload(UploadFileCommand command);
}
