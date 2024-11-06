package es.princip.getp.persistence.adapter.serviceTerm;

import es.princip.getp.application.serviceTerm.exception.NotFoundServiceTermException;
import es.princip.getp.application.serviceTerm.port.out.CheckServiceTermPort;
import es.princip.getp.application.serviceTerm.port.out.LoadServiceTermPort;
import es.princip.getp.application.serviceTerm.port.out.SaveServiceTermPort;
import es.princip.getp.domain.serviceTerm.model.ServiceTerm;
import es.princip.getp.domain.serviceTerm.model.ServiceTermTag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
class ServiceTermPersistenceAdapter implements LoadServiceTermPort, CheckServiceTermPort, SaveServiceTermPort {

    private final ServiceTermJpaRepository serviceTermJpaRepository;
    private final ServiceTermPersistenceMapper mapper;

    @Override
    public void existsBy(final Set<ServiceTermTag> serviceTermTags) {
        final Set<String> tags = serviceTermTags.stream()
            .map(ServiceTermTag::getValue)
            .collect(Collectors.toSet());
        final Set<String> existing = serviceTermJpaRepository.findTagByTagIn(tags);
        final Set<String> notExisting = tags.stream()
            .filter(tag -> !existing.contains(tag))
            .collect(Collectors.toSet());
        if (!notExisting.isEmpty()) {
            throw new NotFoundServiceTermException(notExisting);
        }
    }

    @Override
    public boolean existsBy(final ServiceTermTag tag) {
        return serviceTermJpaRepository.existsByTag(tag.getValue());
    }

    @Override
    public List<ServiceTerm> loadAllBy(final boolean required) {
        return serviceTermJpaRepository.findAllByRequired(required)
            .stream()
            .map(mapper::mapToDomain)
            .toList();
    }

    @Override
    public void save(final ServiceTerm serviceTerm) {
        serviceTermJpaRepository.save(mapper.mapToJpa(serviceTerm));
    }
}
