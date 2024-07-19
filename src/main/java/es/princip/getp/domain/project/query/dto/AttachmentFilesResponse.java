package es.princip.getp.domain.project.query.dto;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import es.princip.getp.domain.common.domain.URL;
import es.princip.getp.domain.project.command.domain.AttachmentFile;
import lombok.ToString;

import java.io.IOException;
import java.util.List;

@JsonSerialize(using = AttachmentFilesResponse.AttachmentFilesResponseSerializer.class)
@ToString
public class AttachmentFilesResponse {

    private final List<AttachmentFile> attachmentFiles;

    private AttachmentFilesResponse(final List<AttachmentFile> attachmentFiles) {
        this.attachmentFiles = attachmentFiles;
    }

    public static AttachmentFilesResponse from(final List<AttachmentFile> attachmentFiles) {
        return new AttachmentFilesResponse(attachmentFiles);
    }

    static class AttachmentFilesResponseSerializer extends JsonSerializer<AttachmentFilesResponse> {

        @Override
        public void serialize(
            final AttachmentFilesResponse response,
            final JsonGenerator jsonGenerator,
            final SerializerProvider serializerProvider
        ) throws IOException {
            List<String> urls = response.attachmentFiles.stream()
                .map(AttachmentFile::getUrl)
                .map(URL::getValue)
                .toList();
            jsonGenerator.writeObject(urls);
        }
    }
}
