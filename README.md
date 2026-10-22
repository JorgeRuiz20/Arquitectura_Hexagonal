# 🛍️ Tienda de Ropa - Backend (Arquitectura Hexagonal + Spring Boot 3)

Backend profesional y desacoplado para una **Tienda de Ropa**, construido con **Java 17**, **Spring Boot 3.4.2**, **Spring Data JPA**, **Hibernate** y **MySQL**. Diseñado siguiendo los principios de la **Arquitectura Hexagonal (Puertos y Adaptadores)** y buenas prácticas de Programación Orientada a Objetos (POO / SOLID).

---

## 🏛️ 1. Arquitectura Hexagonal (Puertos y Adaptadores)

El sistema separa estrictamente las reglas de negocio del framework y de los detalles tecnológicos:

```text
                           ┌──────────────────────────────────────────────────┐
                           │            ARQUITECTURA HEXAGONAL                │
                           └──────────────────────────────────────────────────┘

        [ ADAPTADOR PRIMARIO ]                          [ ADAPTADOR SECUNDARIO ]
             (Inbound Web)                                (Outbound Persistence)
                   │                                                ▲
         ┌─────────┴─────────┐                            ┌─────────┴─────────┐
         │  PrendaController │                            │ PrendaPersistence │
         │  (REST API / DTO) │                            │      Adapter      │
         └─────────┬─────────┘                            └─────────▲─────────┘
                   │                                                │
         ┌─────────▼─────────┐                            ┌─────────┴─────────┐
         │   PUERTO ENTRADA  │                            │   PUERTO SALIDA   │
         │ (Use Cases / In)  │                            │ (Repository / Out)│
         └─────────┬─────────┘                            └─────────▲─────────┘
                   │                                                │
                   └────────────────► ┌───────────────┐ ◄───────────┘
                                      │  CAPA DOMINIO │
                                      │ (Java Puro:   │
                                      │  Prenda POJO) │
                                      └───────────────┘
```

### 📂 Estructura de Paquetes

```text
src/main/java/com/tiendaropa/
├── domain/                                      # [NÚCLEO DEL DOMINIO: Java Puro, 0 frameworks]
│   ├── model/
│   │   ├── Prenda.java                          # Entidad de dominio con invariantes y métodos de negocio (POO)
│   │   ├── CategoriaPrenda.java                 # Enum de categorías (CAMISAS, PANTALONES, VESTIDOS, etc.)
│   │   └── TallaPrenda.java                     # Enum de tallas (XS, S, M, L, XL, XXL, UNICA)
│   ├── exception/
│   │   ├── PrendaNotFoundException.java         # Excepción cuando el recurso no existe (404)
│   │   └── ReglaNegocioException.java           # Excepción para reglas violadas (400)
│   └── port/
│       ├── in/                                  # Puertos de Entrada (Casos de Uso)
│       │   ├── CrearPrendaUseCase.java
│       │   ├── ObtenerPrendaUseCase.java
│       │   ├── ActualizarPrendaUseCase.java
│       │   ├── EliminarPrendaUseCase.java
│       │   └── command/
│       │       ├── CrearPrendaCommand.java      # Command desacoplado del protocolo HTTP
│       │       └── ActualizarPrendaCommand.java
│       └── out/                                 # Puertos de Salida (Contratos hacia el exterior)
│           └── PrendaRepositoryPort.java        # Interfaz de persistencia del Dominio
│
├── application/                                 # [CAPA DE APLICACIÓN: Orquesta los casos de uso]
│   └── service/
│       └── PrendaService.java                   # Implementa Use Cases, inyecta el puerto de salida
│
└── infrastructure/                              # [CAPA DE INFRAESTRUCTURA: Tecnologías externas]
    ├── adapter/
    │   ├── in/web/                              # Adaptador Primario (HTTP / REST)
    │   │   ├── PrendaController.java            # Endpoints REST (@RestController)
    │   │   ├── dto/
    │   │   │   ├── PrendaRequestDto.java        # Validación de entrada (@Valid, @NotBlank, etc.)
    │   │   │   ├── PrendaActualizarDto.java
    │   │   │   └── PrendaResponseDto.java       # DTO inmutable de respuesta
    │   │   └── mapper/
    │   │       └── PrendaWebMapper.java         # Mapeo DTO <-> Command / Domain
    │   └── out/persistence/                     # Adaptador Secundario (MySQL / Hibernate)
    │       ├── PrendaPersistenceAdapter.java    # Implementa PrendaRepositoryPort
    │       ├── entity/
    │       │   └── PrendaJpaEntity.java         # Mapeo JPA (@Entity, @Table("prendas"))
    │       ├── repository/
    │       │   └── SpringDataPrendaRepository.java # JpaRepository
    │       └── mapper/
    │           └── PrendaPersistenceMapper.java # Mapeo Prenda (Dominio) <-> PrendaJpaEntity (JPA)
    ├── exception/
    │   ├── GlobalExceptionHandler.java          # @RestControllerAdvice centralizado
    │   └── ErrorResponse.java                   # Formato JSON estándar de errores
    └── config/
        └── DataInitializer.java                 # Carga automática de 5 prendas de prueba al arrancar
```

---

## 🗄️ 2. Base de Datos MySQL Configurada

- **Host / Puerto:** `localhost:3306`
- **Base de Datos:** `tienda_ropa_db`
- **Usuario:** `admin`
- **Contraseña:** `1234`
- **Estrategia DDL:** `update` (crea y actualiza tablas automáticamente)

---

## 🚀 3. Cómo Ejecutar el Proyecto

### Opción A: Desde Spring Tool Suite (STS)
1. Abre tu **Spring Tool Suite 4**.
2. Ve a **File** -> **Open Projects from File System...** o **Import... -> Existing Maven Projects**.
3. Selecciona la carpeta `TiendaRopa`.
4. Haz clic derecho sobre el proyecto -> **Run As** -> **Spring Boot App**.

### Opción B: Desde la Consola (Terminal)
En la carpeta del proyecto, ejecuta:
```bash
./mvnw spring-boot:run
```
O en Windows PowerShell / CMD:
```cmd
mvnw.cmd spring-boot:run
```

El servidor iniciará en: `http://localhost:8080`

---

## 📡 4. Guía de Endpoints del CRUD (REST API)

### 1. Listar todas las prendas
- **Método:** `GET`
- **URL:** `http://localhost:8080/api/v1/prendas`
- **Parámetro opcional:** `?soloActivas=true`

### 2. Obtener prenda por ID
- **Método:** `GET`
- **URL:** `http://localhost:8080/api/v1/prendas/1`

### 3. Obtener prenda por SKU
- **Método:** `GET`
- **URL:** `http://localhost:8080/api/v1/prendas/sku/CAM-OXF-001`

### 4. Crear nueva prenda
- **Método:** `POST`
- **URL:** `http://localhost:8080/api/v1/prendas`
- **Headers:** `Content-Type: application/json`
- **Body:**
```json
{
  "codigoSku": "POL-CAS-010",
  "nombre": "Polo Oversize Vintage",
  "descripcion": "Polo 100% algodon peinado corte relajado",
  "categoria": "POLOS",
  "talla": "L",
  "color": "Verde Militar",
  "precio": 55.00,
  "stock": 30
}
```

### 5. Actualizar prenda existente
- **Método:** `PUT`
- **URL:** `http://localhost:8080/api/v1/prendas/1`
- **Body:**
```json
{
  "nombre": "Camisa Oxford Manga Larga Slim Fit Premium",
  "descripcion": "Camisa algodon egipcio corte moderno",
  "categoria": "CAMISAS",
  "talla": "M",
  "color": "Azul Noche",
  "precio": 95.00,
  "stock": 28
}
```

### 6. Desactivar prenda (Baja Lógica)
- **Método:** `PATCH`
- **URL:** `http://localhost:8080/api/v1/prendas/1/desactivar`

### 7. Reactivar prenda
- **Método:** `PATCH`
- **URL:** `http://localhost:8080/api/v1/prendas/1/activar`

### 8. Eliminar prenda (Física)
- **Método:** `DELETE`
- **URL:** `http://localhost:8080/api/v1/prendas/6`

---

## 🧪 5. Pruebas Automatizadas
Para ejecutar la suite completa de pruebas unitarias y de integración:
```bash
./mvnw test
```
Todas las pruebas corren y validan la base de datos MySQL, reglas de negocio e integración web.
