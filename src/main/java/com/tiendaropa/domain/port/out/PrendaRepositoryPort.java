package com.tiendaropa.domain.port.out;

import com.tiendaropa.domain.model.Prenda;

import java.util.List;
import java.util.Optional;

/**
 * Puerto de Salida (Driven Port): Define el contrato de persistencia para el Dominio.
 * El dominio y la capa de aplicacion solo conocen esta interfaz, no Spring Data ni JPA.
 */
public interface PrendaRepositoryPort {

    Prenda guardar(Prenda prenda);

    Optional<Prenda> buscarPorId(Long id);

    Optional<Prenda> buscarPorSku(String codigoSku);

    List<Prenda> listarTodas();

    List<Prenda> listarActivas();

    void eliminarPorId(Long id);

    boolean existePorSku(String codigoSku);

    boolean existePorId(Long id);
}
