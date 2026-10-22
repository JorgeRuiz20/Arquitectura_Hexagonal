package com.tiendaropa;

import com.tiendaropa.domain.exception.ReglaNegocioException;
import com.tiendaropa.domain.model.CategoriaPrenda;
import com.tiendaropa.domain.model.Prenda;
import com.tiendaropa.domain.model.TallaPrenda;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class PrendaDomainTest {

    @Test
    @DisplayName("Debe crear una prenda valida y mantener invariantes de negocio")
    void debeCrearPrendaValida() {
        Prenda prenda = Prenda.crearNueva(
                "CAM-001",
                "Camisa Elegante",
                "Camisa formal",
                CategoriaPrenda.CAMISAS,
                TallaPrenda.M,
                "Blanco",
                new BigDecimal("79.90"),
                10
        );

        assertNotNull(prenda);
        assertEquals("CAM-001", prenda.getCodigoSku());
        assertEquals("Camisa Elegante", prenda.getNombre());
        assertTrue(prenda.getActivo());
        assertEquals(10, prenda.getStock());
    }

    @Test
    @DisplayName("Debe lanzar excepcion si el precio es menor o igual a cero")
    void debeFallarSiPrecioEsInvalido() {
        assertThrows(ReglaNegocioException.class, () ->
                Prenda.crearNueva(
                        "CAM-001",
                        "Camisa Elegante",
                        "Camisa formal",
                        CategoriaPrenda.CAMISAS,
                        TallaPrenda.M,
                        "Blanco",
                        BigDecimal.ZERO,
                        10
                )
        );
    }

    @Test
    @DisplayName("Debe permitir agregar y reducir stock correctamente")
    void debeAjustarStockCorrectamente() {
        Prenda prenda = Prenda.crearNueva(
                "CAM-001",
                "Camisa Elegante",
                "Camisa formal",
                CategoriaPrenda.CAMISAS,
                TallaPrenda.M,
                "Blanco",
                new BigDecimal("79.90"),
                10
        );

        prenda.agregarStock(5);
        assertEquals(15, prenda.getStock());

        prenda.reducirStock(3);
        assertEquals(12, prenda.getStock());
    }

    @Test
    @DisplayName("Debe lanzar excepcion si se intenta reducir mas stock del disponible")
    void debeFallarSiStockEsInsuficiente() {
        Prenda prenda = Prenda.crearNueva(
                "CAM-001",
                "Camisa Elegante",
                "Camisa formal",
                CategoriaPrenda.CAMISAS,
                TallaPrenda.M,
                "Blanco",
                new BigDecimal("79.90"),
                5
        );

        assertThrows(ReglaNegocioException.class, () -> prenda.reducirStock(10));
    }

    @Test
    @DisplayName("Debe desactivar y reactivar prenda correctamente")
    void debeCambiarEstadoActivo() {
        Prenda prenda = Prenda.crearNueva(
                "CAM-001",
                "Camisa Elegante",
                "Camisa formal",
                CategoriaPrenda.CAMISAS,
                TallaPrenda.M,
                "Blanco",
                new BigDecimal("79.90"),
                5
        );

        prenda.desactivar();
        assertFalse(prenda.getActivo());

        prenda.activar();
        assertTrue(prenda.getActivo());
    }
}
