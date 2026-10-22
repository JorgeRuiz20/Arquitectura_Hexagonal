package com.tiendaropa.infrastructure.adapter.out.persistence;

import com.tiendaropa.domain.model.Prenda;
import com.tiendaropa.domain.port.out.PrendaRepositoryPort;
import com.tiendaropa.infrastructure.adapter.out.persistence.entity.PrendaJpaEntity;
import com.tiendaropa.infrastructure.adapter.out.persistence.mapper.PrendaPersistenceMapper;
import com.tiendaropa.infrastructure.adapter.out.persistence.repository.SpringDataPrendaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Adaptador Secundario (Driven Adapter / Persistencia).
 * Implementa el puerto de salida PrendaRepositoryPort conectando el Dominio con Spring Data JPA.
 */
@Component
public class PrendaPersistenceAdapter implements PrendaRepositoryPort {

    private final SpringDataPrendaRepository springDataPrendaRepository;
    private final PrendaPersistenceMapper mapper;

    public PrendaPersistenceAdapter(SpringDataPrendaRepository springDataPrendaRepository,
                                   PrendaPersistenceMapper mapper) {
        this.springDataPrendaRepository = Objects.requireNonNull(springDataPrendaRepository, "springDataPrendaRepository no puede ser nulo");
        this.mapper = Objects.requireNonNull(mapper, "mapper no puede ser nulo");
    }

    @Override
    public Prenda guardar(Prenda prenda) {
        PrendaJpaEntity entity = mapper.toJpaEntity(prenda);
        PrendaJpaEntity savedEntity = springDataPrendaRepository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Prenda> buscarPorId(Long id) {
        return springDataPrendaRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public Optional<Prenda> buscarPorSku(String codigoSku) {
        return springDataPrendaRepository.findByCodigoSku(codigoSku)
                .map(mapper::toDomain);
    }

    @Override
    public List<Prenda> listarTodas() {
        return springDataPrendaRepository.findAll()
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public List<Prenda> listarActivas() {
        return springDataPrendaRepository.findByActivoTrue()
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public void eliminarPorId(Long id) {
        springDataPrendaRepository.deleteById(id);
    }

    @Override
    public boolean existePorSku(String codigoSku) {
        return springDataPrendaRepository.existsByCodigoSku(codigoSku);
    }

    @Override
    public boolean existePorId(Long id) {
        return springDataPrendaRepository.existsById(id);
    }
}
