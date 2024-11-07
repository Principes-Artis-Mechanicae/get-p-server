package es.princip.getp.persistence.adapter.serviceTerm;

import es.princip.getp.application.serviceTerm.exception.NotFoundServiceTermException;
import es.princip.getp.domain.serviceTerm.model.ServiceTermTag;
import es.princip.getp.persistence.support.PersistenceAdapterTest;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ServiceTermPersistenceAdapterTest extends PersistenceAdapterTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private ServiceTermPersistenceAdapter serviceTermPersistenceAdapter;

    private ServiceTermDataLoader dataLoader;

    @BeforeEach
    void setUp() {
        dataLoader = new ServiceTermDataLoader(entityManager);
        dataLoader.load();
    }

    @AfterEach
    void tearDown() {
        dataLoader.teardown();
    }

    @Test
    void 존재하지_않는_서비스_약관을_찾는다() {
        final Set<ServiceTermTag> tags = Set.of(
            ServiceTermTag.of("존재하지 않는 서비스 약관1"),
            ServiceTermTag.of("존재하지 않는 서비스 약관2")
        );

        final String message = NotFoundServiceTermException.formatMessage(tags.stream()
            .map(ServiceTermTag::getValue)
            .collect(Collectors.toSet()));
        assertThatThrownBy(() -> serviceTermPersistenceAdapter.existsBy(tags))
            .isInstanceOf(NotFoundServiceTermException.class)
            .hasMessageContaining(message);
    }
}