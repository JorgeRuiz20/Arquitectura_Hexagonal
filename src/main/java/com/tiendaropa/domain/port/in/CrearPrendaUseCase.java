package com.tiendaropa.domain.port.in;

import com.tiendaropa.domain.model.Prenda;
import com.tiendaropa.domain.port.in.command.CrearPrendaCommand;

/**
 * Puerto de Entrada (Driving Port): Caso de uso para registrar una nueva prenda.
 */
public interface CrearPrendaUseCase {
    Prenda crearPrenda(CrearPrendaCommand command);
}
