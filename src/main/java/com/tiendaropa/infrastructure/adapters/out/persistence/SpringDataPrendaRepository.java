package com.tiendaropa.infrastructure.adapters.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * REPOSITORIO SPRING DATA JPA
 */
@Repository
public interface SpringDataPrendaRepository extends JpaRepository<PrendaJpaEntity, Long> {
}
