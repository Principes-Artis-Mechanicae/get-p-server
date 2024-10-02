package es.princip.getp.application.resolver;

import es.princip.getp.api.controller.project.query.dto.AttachmentFilesResponse;
import es.princip.getp.api.controller.project.query.dto.ProjectClientResponse;
import es.princip.getp.api.controller.project.query.dto.ProjectDetailResponse;
import es.princip.getp.domain.client.model.Address;
import es.princip.getp.domain.common.model.AttachmentFile;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class MessageSourceMosaicResolver implements MosaicResolver {

    private final MessageSource messageSource;

    private String convertMosaicMessage(String message, int length) {
        return message.repeat(Math.max(0, length));
    }

    private int getLength(String value) {
        if (value != null) {
            return value.length();
        }
        return 0;
    }

    private AttachmentFilesResponse mosaicAttachmentFilesResponse(String message, List<Integer> lengthList) {
        return AttachmentFilesResponse.from(lengthList.stream()
            .map(length -> AttachmentFile.from(message.repeat(Math.max(0, length))))
            .toList());
    }

    private ProjectClientResponse mosaicClientResponse(ProjectClientResponse client, String message) {
        String mosaicNickname = convertMosaicMessage(message, getLength(client.nickname()));
        Address mosaicAddress = mosaicAddress(client.address(), message);
        return new ProjectClientResponse(null, mosaicNickname, mosaicAddress);
    }

    private Address mosaicAddress(Address address, String message) {
        return new Address(
            convertMosaicMessage(message, getLength(address.getZipcode())),
            convertMosaicMessage(message, getLength(address.getStreet())),
            convertMosaicMessage(message, getLength(address.getDetail()))
        );
    }

    @Override
    public ProjectDetailResponse resolve(ProjectDetailResponse response) {
        String message = messageSource.getMessage("restricted.access", null, Locale.getDefault());
        String mosaicDescription = convertMosaicMessage(message, getLength(response.getDescription()));
        List<Integer> fileLengths = response.getAttachmentFiles().getAttachmentFiles()
            .stream()
            .map(file -> getLength(file.getUrl().getValue()))
            .toList();
        AttachmentFilesResponse mosaicAttachmentFilesResponse = mosaicAttachmentFilesResponse(message, fileLengths);
        ProjectClientResponse mosaicClientResponse = mosaicClientResponse(response.getClient(), message);
        response.mosaic(mosaicDescription, mosaicAttachmentFilesResponse, mosaicClientResponse);
        return response;
    }
}
