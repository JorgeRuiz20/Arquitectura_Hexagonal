package com.tiendaropa.infrastructure.adapter.in.web.dto;

import com.tiendaropa.domain.model.CategoriaPrenda;
import com.tiendaropa.domain.model.TallaPrenda;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO de respuesta inmutable para la API REST.
 * Evita exponer directamente entidades internas o de base de datos.
 */
public record PrendaResponseDto(
        Long id,
        String codigoSku,
        String nombre,
        String descripcion,
        CategoriaPrenda categoria,
        TallaPrenda talla,
        String color,
        BigDecimal precio,
        Integer stock,
        Boolean activo,
        LocalDateTime fechaCreacion
) {}
