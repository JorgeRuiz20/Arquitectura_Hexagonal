package com.tiendaropa.infrastructure.adapter.in.web.dto;

import com.tiendaropa.domain.model.CategoriaPrenda;
import com.tiendaropa.domain.model.TallaPrenda;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

/**
 * DTO para la solicitud de creacion de una prenda.
 */
public record PrendaRequestDto(
        @NotBlank(message = "El codigo SKU es obligatorio")
        @Size(max = 50, message = "El codigo SKU no debe exceder 50 caracteres")
        String codigoSku,

        @NotBlank(message = "El nombre de la prenda es obligatorio")
        @Size(min = 3, max = 150, message = "El nombre debe tener entre 3 y 150 caracteres")
        String nombre,

        @Size(max = 500, message = "La descripcion no debe exceder 500 caracteres")
        String descripcion,

        @NotNull(message = "La categoria es obligatoria (ej: CAMISAS, PANTALONES, POLOS, CHAQUETAS, VESTIDOS, ACCESORIOS)")
        CategoriaPrenda categoria,

        @NotNull(message = "La talla es obligatoria (ej: XS, S, M, L, XL, XXL, UNICA)")
        TallaPrenda talla,

        @Size(max = 50, message = "El color no debe exceder 50 caracteres")
        String color,

        @NotNull(message = "El precio es obligatorio")
        @Positive(message = "El precio debe ser un numero positivo mayor a cero")
        BigDecimal precio,

        @NotNull(message = "El stock inicial es obligatorio")
        @PositiveOrZero(message = "El stock no puede ser negativo")
        Integer stock
) {}
