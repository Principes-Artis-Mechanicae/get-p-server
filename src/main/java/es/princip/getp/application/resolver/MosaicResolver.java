package es.princip.getp.application.resolver;

import es.princip.getp.api.controller.project.query.dto.ProjectDetailResponse;

public interface MosaicResolver {
    ProjectDetailResponse resolve(ProjectDetailResponse response);
}
