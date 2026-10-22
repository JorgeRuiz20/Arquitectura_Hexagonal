package com.tiendaropa.domain.port.in;

import com.tiendaropa.domain.model.Prenda;

import java.util.List;

/**
 * Puerto de Entrada (Driving Port): Casos de uso de consulta de prendas.
 */
public interface ObtenerPrendaUseCase {
    Prenda obtenerPorId(Long id);
    Prenda obtenerPorSku(String sku);
    List<Prenda> listarTodas();
    List<Prenda> listarActivas();
}
