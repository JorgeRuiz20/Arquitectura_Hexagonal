package com.tiendaropa.infrastructure.adapter.out.persistence.mapper;

import com.tiendaropa.domain.model.Prenda;
import com.tiendaropa.infrastructure.adapter.out.persistence.entity.PrendaJpaEntity;
import org.springframework.stereotype.Component;

/**
 * Mapper que transforma entre el modelo de Dominio (Prenda) y la entidad JPA (PrendaJpaEntity).
 * Permite que los cambios en el esquema de base de datos no afecten la logica del dominio.
 */
@Component
public class PrendaPersistenceMapper {

    public Prenda toDomain(PrendaJpaEntity entity) {
        if (entity == null) {
            return null;
        }
        return new Prenda(
                entity.getId(),
                entity.getCodigoSku(),
                entity.getNombre(),
                entity.getDescripcion(),
                entity.getCategoria(),
                entity.getTalla(),
                entity.getColor(),
                entity.getPrecio(),
                entity.getStock(),
                entity.getActivo(),
                entity.getFechaCreacion()
        );
    }

    public PrendaJpaEntity toJpaEntity(Prenda domain) {
        if (domain == null) {
            return null;
        }
        return new PrendaJpaEntity(
                domain.getId(),
                domain.getCodigoSku(),
                domain.getNombre(),
                domain.getDescripcion(),
                domain.getCategoria(),
                domain.getTalla(),
                domain.getColor(),
                domain.getPrecio(),
                domain.getStock(),
                domain.getActivo(),
                domain.getFechaCreacion()
        );
    }
}
