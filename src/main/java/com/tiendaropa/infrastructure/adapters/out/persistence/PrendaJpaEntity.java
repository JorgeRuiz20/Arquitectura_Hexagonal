package com.tiendaropa.infrastructure.adapters.out.persistence;

import jakarta.persistence.*;

import java.math.BigDecimal;

/**
 * ENTIDAD JPA (Persistencia con Hibernate en MySQL)
 * 
 * Clase técnica exclusiva de la infraestructura.
 */
@Entity
@Table(name = "prendas")
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

    public PrendaJpaEntity() {
    }

    public PrendaJpaEntity(String nombre, String categoria, String talla, BigDecimal precio, Integer stock) {
        this(null, nombre, categoria, talla, precio, stock);
    }

    public PrendaJpaEntity(Long id, String nombre, String categoria, String talla, BigDecimal precio, Integer stock) {
        this.id = id;
        this.nombre = nombre;
        this.categoria = categoria;
        this.talla = talla;
        this.precio = precio;
        this.stock = stock;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getTalla() {
        return talla;
    }

    public void setTalla(String talla) {
        this.talla = talla;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }
}
