package com.tiendaropa.domain.port.in.command;

import com.tiendaropa.domain.model.CategoriaPrenda;
import com.tiendaropa.domain.model.TallaPrenda;

import java.math.BigDecimal;

/**
 * Command de entrada para la creacion de una prenda.
 * Encapsula los datos requeridos por el caso de uso sin acoplarse a capas web.
 */
public record CrearPrendaCommand(
        String codigoSku,
        String nombre,
        String descripcion,
        CategoriaPrenda categoria,
        TallaPrenda talla,
        String color,
        BigDecimal precio,
        Integer stock
) {}
