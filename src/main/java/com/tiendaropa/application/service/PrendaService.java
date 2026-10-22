package com.tiendaropa.application.service;

import com.tiendaropa.domain.exception.PrendaNotFoundException;
import com.tiendaropa.domain.exception.ReglaNegocioException;
import com.tiendaropa.domain.model.Prenda;
import com.tiendaropa.domain.port.in.ActualizarPrendaUseCase;
import com.tiendaropa.domain.port.in.CrearPrendaUseCase;
import com.tiendaropa.domain.port.in.EliminarPrendaUseCase;
import com.tiendaropa.domain.port.in.ObtenerPrendaUseCase;
import com.tiendaropa.domain.port.in.command.ActualizarPrendaCommand;
import com.tiendaropa.domain.port.in.command.CrearPrendaCommand;
import com.tiendaropa.domain.port.out.PrendaRepositoryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * Capa de Aplicacion: Orquesta los casos de uso del dominio.
 * 
 * Principios aplicados:
 * - Implementa los puertos de entrada (Use Cases).
 * - Depende unicamente de abstracciones: inyecta el puerto de salida PrendaRepositoryPort (Inversion de Dependencias - DIP).
 * - Maneja las fronteras transaccionales (@Transactional).
 * - No conoce controladores HTTP ni detalles de JPA/Hibernate.
 */
@Service
@Transactional
public class PrendaService implements CrearPrendaUseCase, ObtenerPrendaUseCase, ActualizarPrendaUseCase, EliminarPrendaUseCase {

    private final PrendaRepositoryPort prendaRepositoryPort;

    // Inyeccion por constructor (Buenas practicas Spring / SOLID)
    public PrendaService(PrendaRepositoryPort prendaRepositoryPort) {
        this.prendaRepositoryPort = Objects.requireNonNull(prendaRepositoryPort, "prendaRepositoryPort no puede ser nulo");
    }

    // ==========================================
    // Caso de Uso: Crear Prenda
    // ==========================================
    @Override
    public Prenda crearPrenda(CrearPrendaCommand command) {
        String sku = command.codigoSku().trim().toUpperCase();
        if (prendaRepositoryPort.existePorSku(sku)) {
            throw new ReglaNegocioException("Ya existe una prenda registrada con el codigo SKU: " + sku);
        }

        Prenda nuevaPrenda = Prenda.crearNueva(
                sku,
                command.nombre(),
                command.descripcion(),
                command.categoria(),
                command.talla(),
                command.color(),
                command.precio(),
                command.stock()
        );

        return prendaRepositoryPort.guardar(nuevaPrenda);
    }

    // ==========================================
    // Casos de Uso: Consultar Prendas
    // ==========================================
    @Override
    @Transactional(readOnly = true)
    public Prenda obtenerPorId(Long id) {
        return prendaRepositoryPort.buscarPorId(id)
                .orElseThrow(() -> new PrendaNotFoundException(id));
    }

    @Override
    @Transactional(readOnly = true)
    public Prenda obtenerPorSku(String sku) {
        return prendaRepositoryPort.buscarPorSku(sku.trim().toUpperCase())
                .orElseThrow(() -> new PrendaNotFoundException(sku));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Prenda> listarTodas() {
        return prendaRepositoryPort.listarTodas();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Prenda> listarActivas() {
        return prendaRepositoryPort.listarActivas();
    }

    // ==========================================
    // Caso de Uso: Actualizar Prenda
    // ==========================================
    @Override
    public Prenda actualizarPrenda(ActualizarPrendaCommand command) {
        Prenda prendaExistente = prendaRepositoryPort.buscarPorId(command.id())
                .orElseThrow(() -> new PrendaNotFoundException(command.id()));

        // Delegamos la mutacion y validacion a la logica de dominio (POO)
        prendaExistente.actualizar(
                command.nombre(),
                command.descripcion(),
                command.categoria(),
                command.talla(),
                command.color(),
                command.precio(),
                command.stock()
        );

        return prendaRepositoryPort.guardar(prendaExistente);
    }

    @Override
    public Prenda cambiarEstadoActivo(Long id, boolean activo) {
        Prenda prendaExistente = prendaRepositoryPort.buscarPorId(id)
                .orElseThrow(() -> new PrendaNotFoundException(id));

        if (activo) {
            prendaExistente.activar();
        } else {
            prendaExistente.desactivar();
        }

        return prendaRepositoryPort.guardar(prendaExistente);
    }

    // ==========================================
    // Caso de Uso: Eliminar Prenda
    // ==========================================
    @Override
    public void eliminarPorId(Long id) {
        if (!prendaRepositoryPort.existePorId(id)) {
            throw new PrendaNotFoundException(id);
        }
        prendaRepositoryPort.eliminarPorId(id);
    }
}
