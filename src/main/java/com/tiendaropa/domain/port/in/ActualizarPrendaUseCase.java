package com.tiendaropa.domain.port.in;

import com.tiendaropa.domain.model.Prenda;
import com.tiendaropa.domain.port.in.command.ActualizarPrendaCommand;

/**
 * Puerto de Entrada (Driving Port): Caso de uso para modificar una prenda.
 */
public interface ActualizarPrendaUseCase {
    Prenda actualizarPrenda(ActualizarPrendaCommand command);
    Prenda cambiarEstadoActivo(Long id, boolean activo);
}
