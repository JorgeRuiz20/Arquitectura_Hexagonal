package com.tiendaropa.domain.ports.in;

import com.tiendaropa.domain.model.Prenda;

import java.util.List;


public interface ListarPrendasUseCase {
    List<Prenda> listar();
}
