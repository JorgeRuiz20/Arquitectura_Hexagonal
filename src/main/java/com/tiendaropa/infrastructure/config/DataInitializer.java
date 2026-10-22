package com.tiendaropa.infrastructure.config;

import com.tiendaropa.domain.model.CategoriaPrenda;
import com.tiendaropa.domain.model.TallaPrenda;
import com.tiendaropa.domain.port.in.CrearPrendaUseCase;
import com.tiendaropa.domain.port.in.ObtenerPrendaUseCase;
import com.tiendaropa.domain.port.in.command.CrearPrendaCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

/**
 * Inicializador de datos de prueba para la tienda de ropa.
 * Se ejecuta automaticamente al arrancar el backend si la base de datos no tiene prendas.
 */
@Configuration
public class DataInitializer {

    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);

    @Bean
    CommandLineRunner initData(CrearPrendaUseCase crearPrendaUseCase, ObtenerPrendaUseCase obtenerPrendaUseCase) {
        return args -> {
            if (obtenerPrendaUseCase.listarTodas().isEmpty()) {
                log.info("Inicializando catalogo inicial de prendas en MySQL...");

                crearPrendaUseCase.crearPrenda(new CrearPrendaCommand(
                        "CAM-OXF-001",
                        "Camisa Oxford Manga Larga Slim Fit",
                        "Camisa 100% algodon peinado, ideal para oficina o eventos casuales.",
                        CategoriaPrenda.CAMISAS,
                        TallaPrenda.M,
                        "Azul Celeste",
                        new BigDecimal("89.90"),
                        25
                ));

                crearPrendaUseCase.crearPrenda(new CrearPrendaCommand(
                        "PAN-JEA-002",
                        "Pantalon Jean Clasico Denim",
                        "Jean stretch de corte recto con acabado lavado premium.",
                        CategoriaPrenda.PANTALONES,
                        TallaPrenda.L,
                        "Azul Marino",
                        new BigDecimal("129.50"),
                        18
                ));

                crearPrendaUseCase.crearPrenda(new CrearPrendaCommand(
                        "POL-PIQ-003",
                        "Polo Cuello Pique Sport",
                        "Polo clasico de algodon pima con cuello camisero.",
                        CategoriaPrenda.POLOS,
                        TallaPrenda.M,
                        "Blanco",
                        new BigDecimal("49.90"),
                        40
                ));

                crearPrendaUseCase.crearPrenda(new CrearPrendaCommand(
                        "CHA-BOM-004",
                        "Chaqueta Bomber Acolchada",
                        "Chaqueta ligera cortaviento con forro termico interior.",
                        CategoriaPrenda.CHAQUETAS,
                        TallaPrenda.XL,
                        "Negro",
                        new BigDecimal("189.00"),
                        12
                ));

                crearPrendaUseCase.crearPrenda(new CrearPrendaCommand(
                        "VES-FLO-005",
                        "Vestido Casual Floral Primavera",
                        "Vestido corto de tela suave con estampado floral moderno.",
                        CategoriaPrenda.VESTIDOS,
                        TallaPrenda.S,
                        "Rojo con flores",
                        new BigDecimal("99.00"),
                        15
                ));

                log.info("Se cargaron 5 prendas de prueba exitosamente en la tienda.");
            } else {
                log.info("El catalogo de prendas ya contiene datos, omitiendo inicializacion.");
            }
        };
    }
}
