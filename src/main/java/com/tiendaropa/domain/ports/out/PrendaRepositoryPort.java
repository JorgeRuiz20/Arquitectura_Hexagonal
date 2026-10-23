package com.tiendaropa.domain.ports.out;

import com.tiendaropa.domain.model.Prenda;

import java.util.List;

public interface PrendaRepositoryPort {
    List<Prenda> obtenerTodas();
}
