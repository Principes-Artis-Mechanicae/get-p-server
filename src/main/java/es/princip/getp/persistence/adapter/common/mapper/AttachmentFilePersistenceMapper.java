package es.princip.getp.persistence.adapter.common.mapper;

import es.princip.getp.domain.common.model.AttachmentFile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AttachmentFilePersistenceMapper {

    @Mapping(source = "value", target = "url.value")
    AttachmentFile mapToAttachmentFile(String value);

    default String mapToString(AttachmentFile attachmentFile) {
        if (attachmentFile == null || attachmentFile.getUrl() == null) {
            return null;
        }
        return attachmentFile.getUrl().getValue();
    }
}
