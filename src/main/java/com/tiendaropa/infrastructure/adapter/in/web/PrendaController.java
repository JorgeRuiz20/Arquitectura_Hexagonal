package com.tiendaropa.infrastructure.adapter.in.web;

import com.tiendaropa.domain.model.Prenda;
import com.tiendaropa.domain.port.in.ActualizarPrendaUseCase;
import com.tiendaropa.domain.port.in.CrearPrendaUseCase;
import com.tiendaropa.domain.port.in.EliminarPrendaUseCase;
import com.tiendaropa.domain.port.in.ObtenerPrendaUseCase;
import com.tiendaropa.domain.port.in.command.ActualizarPrendaCommand;
import com.tiendaropa.domain.port.in.command.CrearPrendaCommand;
import com.tiendaropa.infrastructure.adapter.in.web.dto.PrendaActualizarDto;
import com.tiendaropa.infrastructure.adapter.in.web.dto.PrendaRequestDto;
import com.tiendaropa.infrastructure.adapter.in.web.dto.PrendaResponseDto;
import com.tiendaropa.infrastructure.adapter.in.web.mapper.PrendaWebMapper;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Objects;

/**
 * Adaptador Primario (Driving Adapter / REST Controller).
 * Expone la API HTTP y traduce las peticiones web en invocaciones a los puertos de entrada.
 */
@RestController
@RequestMapping("/api/v1/prendas")
@CrossOrigin(origins = "*")
public class PrendaController {

    private final CrearPrendaUseCase crearPrendaUseCase;
    private final ObtenerPrendaUseCase obtenerPrendaUseCase;
    private final ActualizarPrendaUseCase actualizarPrendaUseCase;
    private final EliminarPrendaUseCase eliminarPrendaUseCase;
    private final PrendaWebMapper mapper;

    public PrendaController(CrearPrendaUseCase crearPrendaUseCase,
                            ObtenerPrendaUseCase obtenerPrendaUseCase,
                            ActualizarPrendaUseCase actualizarPrendaUseCase,
                            EliminarPrendaUseCase eliminarPrendaUseCase,
                            PrendaWebMapper mapper) {
        this.crearPrendaUseCase = Objects.requireNonNull(crearPrendaUseCase, "crearPrendaUseCase no puede ser nulo");
        this.obtenerPrendaUseCase = Objects.requireNonNull(obtenerPrendaUseCase, "obtenerPrendaUseCase no puede ser nulo");
        this.actualizarPrendaUseCase = Objects.requireNonNull(actualizarPrendaUseCase, "actualizarPrendaUseCase no puede ser nulo");
        this.eliminarPrendaUseCase = Objects.requireNonNull(eliminarPrendaUseCase, "eliminarPrendaUseCase no puede ser nulo");
        this.mapper = Objects.requireNonNull(mapper, "mapper no puede ser nulo");
    }

    /**
     * Crear una nueva prenda.
     */
    @PostMapping
    public ResponseEntity<PrendaResponseDto> crearPrenda(@Valid @RequestBody PrendaRequestDto requestDto) {
        CrearPrendaCommand command = mapper.toCreateCommand(requestDto);
        Prenda nuevaPrenda = crearPrendaUseCase.crearPrenda(command);
        PrendaResponseDto responseDto = mapper.toResponseDto(nuevaPrenda);
        return ResponseEntity.created(URI.create("/api/v1/prendas/" + nuevaPrenda.getId())).body(responseDto);
    }

    /**
     * Listar prendas. Permite filtrar solo activas mediante ?soloActivas=true.
     */
    @GetMapping
    public ResponseEntity<List<PrendaResponseDto>> listarPrendas(
            @RequestParam(name = "soloActivas", defaultValue = "false") boolean soloActivas) {
        List<Prenda> prendas = soloActivas
                ? obtenerPrendaUseCase.listarActivas()
                : obtenerPrendaUseCase.listarTodas();

        List<PrendaResponseDto> responseList = prendas.stream()
                .map(mapper::toResponseDto)
                .toList();

        return ResponseEntity.ok(responseList);
    }

    /**
     * Obtener una prenda por su ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PrendaResponseDto> obtenerPorId(@PathVariable Long id) {
        Prenda prenda = obtenerPrendaUseCase.obtenerPorId(id);
        return ResponseEntity.ok(mapper.toResponseDto(prenda));
    }

    /**
     * Obtener una prenda por su codigo SKU.
     */
    @GetMapping("/sku/{sku}")
    public ResponseEntity<PrendaResponseDto> obtenerPorSku(@PathVariable String sku) {
        Prenda prenda = obtenerPrendaUseCase.obtenerPorSku(sku);
        return ResponseEntity.ok(mapper.toResponseDto(prenda));
    }

    /**
     * Actualizar una prenda existente.
     */
    @PutMapping("/{id}")
    public ResponseEntity<PrendaResponseDto> actualizarPrenda(
            @PathVariable Long id,
            @Valid @RequestBody PrendaActualizarDto actualizarDto) {
        ActualizarPrendaCommand command = mapper.toUpdateCommand(id, actualizarDto);
        Prenda prendaActualizada = actualizarPrendaUseCase.actualizarPrenda(command);
        return ResponseEntity.ok(mapper.toResponseDto(prendaActualizada));
    }

    /**
     * Desactivar prenda (baja logica).
     */
    @PatchMapping("/{id}/desactivar")
    public ResponseEntity<PrendaResponseDto> desactivarPrenda(@PathVariable Long id) {
        Prenda prenda = actualizarPrendaUseCase.cambiarEstadoActivo(id, false);
        return ResponseEntity.ok(mapper.toResponseDto(prenda));
    }

    /**
     * Reactivar prenda.
     */
    @PatchMapping("/{id}/activar")
    public ResponseEntity<PrendaResponseDto> activarPrenda(@PathVariable Long id) {
        Prenda prenda = actualizarPrendaUseCase.cambiarEstadoActivo(id, true);
        return ResponseEntity.ok(mapper.toResponseDto(prenda));
    }

    /**
     * Eliminar prenda fisicamente.
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> eliminarPrenda(@PathVariable Long id) {
        eliminarPrendaUseCase.eliminarPorId(id);
        return ResponseEntity.noContent().build();
    }
}
