package es.princip.getp.persistence.adapter.serviceTerm;

import es.princip.getp.domain.serviceTerm.domain.ServiceTerm;
import es.princip.getp.domain.serviceTerm.domain.ServiceTermTag;
import es.princip.getp.domain.serviceTerm.port.out.CheckServiceTermPort;
import es.princip.getp.domain.serviceTerm.port.out.LoadServiceTermPort;
import es.princip.getp.domain.serviceTerm.port.out.SaveServiceTermPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
class ServiceTermPersistenceAdapter implements LoadServiceTermPort, CheckServiceTermPort, SaveServiceTermPort {

    private final ServiceTermJpaRepository serviceTermJpaRepository;
    private final ServiceTermPersistenceMapper mapper;

    @Override
    public boolean existsBy(final ServiceTermTag tag) {
        return serviceTermJpaRepository.existsByTag(tag.getValue());
    }

    @Override
    public ServiceTerm loadBy(final ServiceTermTag tag) {
        return serviceTermJpaRepository.findByTag(tag.getValue())
            .map(mapper::mapToDomain)
            .orElseThrow(NotFoundServiceTermException::new);
    }

    @Override
    public void save(final ServiceTerm serviceTerm) {
        serviceTermJpaRepository.save(mapper.mapToJpa(serviceTerm));
    }
}
