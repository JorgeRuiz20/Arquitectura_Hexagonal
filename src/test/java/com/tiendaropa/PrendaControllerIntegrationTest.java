package com.tiendaropa;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tiendaropa.domain.model.CategoriaPrenda;
import com.tiendaropa.domain.model.TallaPrenda;
import com.tiendaropa.infrastructure.adapter.in.web.dto.PrendaActualizarDto;
import com.tiendaropa.infrastructure.adapter.in.web.dto.PrendaRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class PrendaControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Debe listar prendas correctamente")
    void debeListarPrendas() throws Exception {
        mockMvc.perform(get("/api/v1/prendas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", not(empty())));
    }

    @Test
    @DisplayName("Debe crear una nueva prenda mediante POST /api/v1/prendas")
    void debeCrearPrenda() throws Exception {
        String skuUnico = "TEST-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        PrendaRequestDto nuevo = new PrendaRequestDto(
                skuUnico,
                "Poleron Oversize Urbano",
                "Poleron con capucha de algodon frizado",
                CategoriaPrenda.CHAQUETAS,
                TallaPrenda.L,
                "Gris Jaspeado",
                new BigDecimal("119.00"),
                15
        );

        mockMvc.perform(post("/api/v1/prendas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(nuevo)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.codigoSku", is(skuUnico)))
                .andExpect(jsonPath("$.nombre", is("Poleron Oversize Urbano")))
                .andExpect(jsonPath("$.activo", is(true)));
    }

    @Test
    @DisplayName("Debe retornar 400 cuando los datos de entrada son invalidos")
    void debeRetornar400SiDatosInvalidos() throws Exception {
        PrendaRequestDto invalido = new PrendaRequestDto(
                "",
                "",
                "desc",
                null,
                null,
                "negro",
                new BigDecimal("-5.00"),
                -1
        );

        mockMvc.perform(post("/api/v1/prendas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalido)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.validaciones", notNullValue()));
    }
}
