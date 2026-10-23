package com.tiendaropa.application;

import com.tiendaropa.domain.model.Prenda;
import com.tiendaropa.domain.ports.in.ListarPrendasUseCase;
import com.tiendaropa.domain.ports.out.PrendaRepositoryPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrendaApplicationService implements ListarPrendasUseCase {

    @Autowired
    private PrendaRepositoryPort prendaRepositoryPort;

    @Override
    public List<Prenda> listar() {
        return prendaRepositoryPort.obtenerTodas();
    }
}
