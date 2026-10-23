package com.tiendaropa.infrastructure.adapters.out.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * ENTIDAD JPA (Persistencia con Hibernate en MySQL)
 * 
 * Clase técnica exclusiva de la infraestructura.
 * Utiliza Lombok (@Getter, @Setter, @NoArgsConstructor, @AllArgsConstructor)
 * para generar automáticamente los constructores, getters y setters en tiempo de compilación.
 */
@Entity
@Table(name = "prendas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PrendaJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false, length = 150)
    private String nombre;

    @Column(name = "categoria", nullable = false, length = 50)
    private String categoria;

    @Column(name = "talla", nullable = false, length = 20)
    private String talla;

    @Column(name = "precio", nullable = false, precision = 10, scale = 2)
    private BigDecimal precio;

    @Column(name = "stock", nullable = false)
    private Integer stock;
}
