package com.tiendaropa.domain.model;

import com.tiendaropa.domain.exception.ReglaNegocioException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Entidad de Dominio: Prenda.
 * Representa una prenda de vestir en la tienda de ropa.
 * 
 * En Arquitectura Hexagonal, el Dominio es Java puro:
 * - NO contiene anotaciones de Spring (@Service, @Autowired, etc.).
 * - NO contiene anotaciones de JPA/Hibernate (@Entity, @Table, @Id, etc.).
 * - Encapsula su estado y contiene logica de negocio e invariantes (POO).
 */
public class Prenda {

    private Long id;
    private String codigoSku;
    private String nombre;
    private String descripcion;
    private CategoriaPrenda categoria;
    private TallaPrenda talla;
    private String color;
    private BigDecimal precio;
    private Integer stock;
    private Boolean activo;
    private LocalDateTime fechaCreacion;

    /**
     * Constructor completo (usado al reconstruir desde persistencia o factory).
     */
    public Prenda(Long id, String codigoSku, String nombre, String descripcion,
                  CategoriaPrenda categoria, TallaPrenda talla, String color,
                  BigDecimal precio, Integer stock, Boolean activo, LocalDateTime fechaCreacion) {
        validarDatosBasicos(codigoSku, nombre, precio, stock);
        this.id = id;
        this.codigoSku = codigoSku.trim().toUpperCase();
        this.nombre = nombre.trim();
        this.descripcion = descripcion != null ? descripcion.trim() : "";
        this.categoria = Objects.requireNonNull(categoria, "La categoria es obligatoria");
        this.talla = Objects.requireNonNull(talla, "La talla es obligatoria");
        this.color = color != null ? color.trim() : "Sin especificar";
        this.precio = precio;
        this.stock = stock;
        this.activo = activo != null ? activo : true;
        this.fechaCreacion = fechaCreacion != null ? fechaCreacion : LocalDateTime.now();
    }

    /**
     * Factory method para crear una nueva prenda con valores por defecto.
     */
    public static Prenda crearNueva(String codigoSku, String nombre, String descripcion,
                                    CategoriaPrenda categoria, TallaPrenda talla, String color,
                                    BigDecimal precio, Integer stock) {
        return new Prenda(
                null,
                codigoSku,
                nombre,
                descripcion,
                categoria,
                talla,
                color,
                precio,
                stock,
                true,
                LocalDateTime.now()
        );
    }

    // ==========================================
    // Metodos de Negocio (POO / Invariantes)
    // ==========================================

    public void actualizar(String nombre, String descripcion, CategoriaPrenda categoria,
                           TallaPrenda talla, String color, BigDecimal precio, Integer stock) {
        validarNombre(nombre);
        validarPrecio(precio);
        validarStock(stock);

        this.nombre = nombre.trim();
        this.descripcion = descripcion != null ? descripcion.trim() : "";
        this.categoria = Objects.requireNonNull(categoria, "La categoria es obligatoria");
        this.talla = Objects.requireNonNull(talla, "La talla es obligatoria");
        this.color = color != null ? color.trim() : "Sin especificar";
        this.precio = precio;
        this.stock = stock;
    }

    public void desactivar() {
        if (!this.activo) {
            throw new ReglaNegocioException("La prenda ya se encuentra desactivada.");
        }
        this.activo = false;
    }

    public void activar() {
        if (this.activo) {
            throw new ReglaNegocioException("La prenda ya se encuentra activa.");
        }
        this.activo = true;
    }

    public void agregarStock(int cantidad) {
        if (cantidad <= 0) {
            throw new ReglaNegocioException("La cantidad a agregar debe ser mayor a cero.");
        }
        this.stock += cantidad;
    }

    public void reducirStock(int cantidad) {
        if (cantidad <= 0) {
            throw new ReglaNegocioException("La cantidad a reducir debe ser mayor a cero.");
        }
        if (this.stock < cantidad) {
            throw new ReglaNegocioException("Stock insuficiente. Stock actual: " + this.stock + ", solicitado: " + cantidad);
        }
        this.stock -= cantidad;
    }

    // ==========================================
    // Validaciones de Dominio (Invariantes)
    // ==========================================

    private void validarDatosBasicos(String codigoSku, String nombre, BigDecimal precio, Integer stock) {
        if (codigoSku == null || codigoSku.trim().isBlank()) {
            throw new ReglaNegocioException("El codigo SKU es obligatorio y no puede estar vacio.");
        }
        validarNombre(nombre);
        validarPrecio(precio);
        validarStock(stock);
    }

    private void validarNombre(String nombre) {
        if (nombre == null || nombre.trim().isBlank()) {
            throw new ReglaNegocioException("El nombre de la prenda es obligatorio.");
        }
        if (nombre.trim().length() < 3) {
            throw new ReglaNegocioException("El nombre debe tener al menos 3 caracteres.");
        }
    }

    private void validarPrecio(BigDecimal precio) {
        if (precio == null || precio.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ReglaNegocioException("El precio de la prenda debe ser mayor a cero.");
        }
    }

    private void validarStock(Integer stock) {
        if (stock == null || stock < 0) {
            throw new ReglaNegocioException("El stock no puede ser nulo ni negativo.");
        }
    }

    // ==========================================
    // Getters (Encapsulamiento estricto)
    // ==========================================

    public Long getId() {
        return id;
    }

    public String getCodigoSku() {
        return codigoSku;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public CategoriaPrenda getCategoria() {
        return categoria;
    }

    public TallaPrenda getTalla() {
        return talla;
    }

    public String getColor() {
        return color;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public Integer getStock() {
        return stock;
    }

    public Boolean getActivo() {
        return activo;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Prenda prenda)) return false;
        return Objects.equals(codigoSku, prenda.codigoSku);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigoSku);
    }
}
