package es.princip.getp.application.resolver;

import es.princip.getp.api.controller.common.dto.AddressResponse;
import es.princip.getp.api.controller.project.query.dto.ProjectClientResponse;
import es.princip.getp.api.controller.project.query.dto.ProjectDetailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

import static es.princip.getp.application.resolver.MosaicUtil.convertMosaicMessage;
import static es.princip.getp.application.resolver.MosaicUtil.getLength;

@Service
@RequiredArgsConstructor
class MessageSourceMosaicResolver implements MosaicResolver {

    private final MessageSource messageSource;

    private List<String> mosaicAttachmentFilesResponse(final String message, final List<Integer> lengthList) {
        return lengthList.stream()
            .map(length -> message.repeat(Math.max(0, length)))
            .toList();
    }

    private ProjectClientResponse mosaicClientResponse(final ProjectClientResponse client, final String message) {
        final String mosaicNickname = convertMosaicMessage(message, getLength(client.nickname()));
        final AddressResponse mosaicAddress = mosaicAddress(client.address(), message);
        return new ProjectClientResponse(null, mosaicNickname, mosaicAddress);
    }

    private AddressResponse mosaicAddress(final AddressResponse address, final String message) {
        return new AddressResponse(
            convertMosaicMessage(message, getLength(address.zipcode())),
            convertMosaicMessage(message, getLength(address.street())),
            convertMosaicMessage(message, getLength(address.detail()))
        );
    }

    @Override
    public ProjectDetailResponse resolve(final ProjectDetailResponse response) {
        final String message = messageSource.getMessage("restricted.access", null, Locale.getDefault());
        final String mosaicDescription = convertMosaicMessage(message, getLength(response.getDescription()));
        final List<Integer> fileLengths = response.getAttachmentFiles()
            .stream()
            .map(MosaicUtil::getLength)
            .toList();
        final List<String> mosaicAttachmentFilesResponse = mosaicAttachmentFilesResponse(message, fileLengths);
        final ProjectClientResponse mosaicClientResponse = mosaicClientResponse(response.getClient(), message);
        response.mosaic(mosaicDescription, mosaicAttachmentFilesResponse, mosaicClientResponse);
        return response;
    }
}