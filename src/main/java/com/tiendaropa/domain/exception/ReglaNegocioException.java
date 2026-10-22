package com.tiendaropa.domain.exception;

/**
 * Excepcion de dominio lanzada cuando se viola alguna regla de negocio o invariante.
 */
public class ReglaNegocioException extends RuntimeException {

    public ReglaNegocioException(String mensaje) {
        super(mensaje);
    }
}
