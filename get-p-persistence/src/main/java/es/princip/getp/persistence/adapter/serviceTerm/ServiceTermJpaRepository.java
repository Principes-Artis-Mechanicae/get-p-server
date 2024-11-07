package es.princip.getp.persistence.adapter.serviceTerm;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

interface ServiceTermJpaRepository extends JpaRepository<ServiceTermJpaEntity, String> {

    List<ServiceTermJpaEntity> findAllByRequired(boolean required);

    boolean existsByTag(String tag);

    @Query("SELECT tag FROM ServiceTermJpaEntity WHERE tag IN :tags")
    Set<String> findTagByTagIn(Set<String> tags);
}