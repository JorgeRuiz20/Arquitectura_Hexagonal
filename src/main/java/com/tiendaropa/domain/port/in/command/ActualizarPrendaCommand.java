package com.tiendaropa.domain.port.in.command;

import com.tiendaropa.domain.model.CategoriaPrenda;
import com.tiendaropa.domain.model.TallaPrenda;

import java.math.BigDecimal;

/**
 * Command de entrada para actualizar una prenda existente.
 */
public record ActualizarPrendaCommand(
        Long id,
        String nombre,
        String descripcion,
        CategoriaPrenda categoria,
        TallaPrenda talla,
        String color,
        BigDecimal precio,
        Integer stock
) {}
