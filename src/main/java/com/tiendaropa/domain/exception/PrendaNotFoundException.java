package com.tiendaropa.domain.exception;

/**
 * Excepcion de dominio lanzada cuando una prenda solicitada no existe.
 */
public class PrendaNotFoundException extends RuntimeException {

    public PrendaNotFoundException(Long id) {
        super("No se encontro ninguna prenda con el ID: " + id);
    }

    public PrendaNotFoundException(String sku) {
        super("No se encontro ninguna prenda con el SKU: " + sku);
    }
}
