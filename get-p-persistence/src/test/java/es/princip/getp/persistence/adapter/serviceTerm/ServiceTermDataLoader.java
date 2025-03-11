package es.princip.getp.persistence.adapter.serviceTerm;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ServiceTermDataLoader {

    private final EntityManager entityManager;

    public void load() {
        final List<ServiceTermJpaEntity> projectList = List.of(
            new ServiceTermJpaEntity(
                "필수 서비스 약관1",
                true,
                false
            ),
            new ServiceTermJpaEntity(
                "필수 서비스 약관2",
                true,
                false
            ),
            new ServiceTermJpaEntity(
                "선택 서비스 약관1",
                false,
                true
            )
        );
        projectList.forEach(entityManager::persist);
    }

    public void teardown() {
        entityManager.createQuery("DELETE FROM ServiceTermJpaEntity").executeUpdate();
    }
}
