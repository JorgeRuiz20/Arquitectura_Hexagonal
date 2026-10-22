package com.tiendaropa.infrastructure.adapter.in.web.mapper;

import com.tiendaropa.domain.model.Prenda;
import com.tiendaropa.domain.port.in.command.ActualizarPrendaCommand;
import com.tiendaropa.domain.port.in.command.CrearPrendaCommand;
import com.tiendaropa.infrastructure.adapter.in.web.dto.PrendaActualizarDto;
import com.tiendaropa.infrastructure.adapter.in.web.dto.PrendaRequestDto;
import com.tiendaropa.infrastructure.adapter.in.web.dto.PrendaResponseDto;
import org.springframework.stereotype.Component;

/**
 * Mapper para la capa web.
 * Convierte DTOs HTTP a Commands de casos de uso y modelos de dominio a DTOs de respuesta.
 */
@Component
public class PrendaWebMapper {

    public CrearPrendaCommand toCreateCommand(PrendaRequestDto dto) {
        return new CrearPrendaCommand(
                dto.codigoSku(),
                dto.nombre(),
                dto.descripcion(),
                dto.categoria(),
                dto.talla(),
                dto.color(),
                dto.precio(),
                dto.stock()
        );
    }

    public ActualizarPrendaCommand toUpdateCommand(Long id, PrendaActualizarDto dto) {
        return new ActualizarPrendaCommand(
                id,
                dto.nombre(),
                dto.descripcion(),
                dto.categoria(),
                dto.talla(),
                dto.color(),
                dto.precio(),
                dto.stock()
        );
    }

    public PrendaResponseDto toResponseDto(Prenda prenda) {
        if (prenda == null) {
            return null;
        }
        return new PrendaResponseDto(
                prenda.getId(),
                prenda.getCodigoSku(),
                prenda.getNombre(),
                prenda.getDescripcion(),
                prenda.getCategoria(),
                prenda.getTalla(),
                prenda.getColor(),
                prenda.getPrecio(),
                prenda.getStock(),
                prenda.getActivo(),
                prenda.getFechaCreacion()
        );
    }
}
