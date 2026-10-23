package com.tiendaropa.infrastructure.adapters.out.persistence;

import com.tiendaropa.domain.model.Prenda;
import com.tiendaropa.domain.ports.out.PrendaRepositoryPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PrendaMySqlAdapter implements PrendaRepositoryPort {

    @Autowired
    private SpringDataPrendaRepository repository;

    @Override
    public List<Prenda> obtenerTodas() {
        List<PrendaJpaEntity> entidades = repository.findAll();
        List<Prenda> prendas = new ArrayList<>();

        for (PrendaJpaEntity e : entidades) {
            Prenda p = new Prenda();
            p.id = e.getId();
            p.nombre = e.getNombre();
            p.categoria = e.getCategoria();
            p.talla = e.getTalla();
            p.precio = e.getPrecio();
            p.stock = e.getStock();
            prendas.add(p);
        }

        return prendas;
    }
}
