package com.tiendaropa.infrastructure.adapter.out.persistence.repository;

import com.tiendaropa.infrastructure.adapter.out.persistence.entity.PrendaJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio Spring Data JPA para la entidad de persistencia PrendaJpaEntity.
 */
@Repository
public interface SpringDataPrendaRepository extends JpaRepository<PrendaJpaEntity, Long> {

    Optional<PrendaJpaEntity> findByCodigoSku(String codigoSku);

    List<PrendaJpaEntity> findByActivoTrue();

    boolean existsByCodigoSku(String codigoSku);
}
