package es.princip.getp.application.project.commission;

import es.princip.getp.api.controller.common.dto.AddressResponse;
import es.princip.getp.api.controller.project.query.dto.ProjectClientResponse;
import es.princip.getp.api.controller.project.query.dto.ProjectDetailResponse;
import es.princip.getp.application.support.MosaicResolver;
import es.princip.getp.application.support.MosaicResolverSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class ProjectDetailResponseMosaicResolver extends MosaicResolverSupport
    implements MosaicResolver<ProjectDetailResponse> {

    @Autowired
    public ProjectDetailResponseMosaicResolver(final MessageSource messageSource) {
        super(messageSource);
    }

    private List<String> mosaicAttachmentFiles(final List<String> attachmentFiles) {
        return attachmentFiles.stream()
            .map(this::mosaicMessage)
            .toList();
    }

    private ProjectClientResponse mosaicClient(final ProjectClientResponse client) {
        final String mosaicNickname = mosaicMessage(client.nickname());
        final AddressResponse mosaicAddress = mosaicAddress(client.address());
        return new ProjectClientResponse(null, mosaicNickname, mosaicAddress);
    }

    private AddressResponse mosaicAddress(final AddressResponse address) {
        return new AddressResponse(
            mosaicMessage(address.zipcode()),
            mosaicMessage(address.street()),
            mosaicMessage(address.detail())
        );
    }

    @Override
    public boolean supports(final Class<?> clazz) {
        return ProjectDetailResponse.class.equals(clazz);
    }

    @Override
    public ProjectDetailResponse resolve(final ProjectDetailResponse response) {
        final String description = mosaicMessage(response.getDescription());
        final List<String> attachmentFiles= mosaicAttachmentFiles(response.getAttachmentFiles());
        final ProjectClientResponse client = mosaicClient(response.getClient());
        response.mosaic(description, attachmentFiles, client);
        return response;
    }
}