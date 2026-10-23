package com.tiendaropa.infrastructure.adapters.in.web;

import com.tiendaropa.domain.model.Prenda;
import com.tiendaropa.domain.ports.in.ListarPrendasUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/prendas")
@CrossOrigin(origins = "*")
public class PrendaRestController {

    @Autowired
    private ListarPrendasUseCase listarPrendasUseCase;

    @GetMapping
    public List<Prenda> listar() {
        return listarPrendasUseCase.listar();
    }
}
