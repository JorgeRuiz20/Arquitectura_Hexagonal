# 🛍️ Tienda de Ropa - Arquitectura Hexagonal Simplificada

Este proyecto implementa la **Arquitectura Hexagonal (Puertos y Adaptadores)** de la forma más limpia, directa y minimalista posible, utilizando **Java 17**, **Spring Boot 3.4.2**, **Spring Data JPA**, **Hibernate** y **MySQL**.

El objetivo es mostrar la separación estricta de capas y la inversión de dependencias con una sola funcionalidad clave: **Listar prendas (`GET /api/v1/prendas`)**, sin capas innecesarias, sin DTOs y con código directo.

---

## 🏛️ Flujo de la Arquitectura Hexagonal

```text
 [ CLIENTE HTTP ]
        │  (GET /api/v1/prendas)
        ▼
 [ ADAPTADOR DE ENTRADA (Web) ]
   └── PrendaRestController.java
        │  (invoca)
        ▼
 [ PUERTO DE ENTRADA (Driving Port) ]
   └── ListarPrendasUseCase.java (Interfaz)
        ▲
        │  (implementa)
 [ CAPA DE APLICACIÓN ]
   └── PrendaApplicationService.java
        │  (invoca)
        ▼
 [ PUERTO DE SALIDA (Driven Port) ]
   └── PrendaRepositoryPort.java (Interfaz)
        ▲
        │  (implementa)
 [ ADAPTADOR DE SALIDA (Persistencia) ]
   └── PrendaMySqlAdapter.java
        │  (usa Spring Data JPA)
        ▼
   SpringDataPrendaRepository.java  ──►  PrendaJpaEntity.java  ──►  [ MySQL ]
```

---

## 📂 Estructura del Proyecto

```text
com.tiendaropa/
│
├── 💎 domain/                                  # CAPA 1: DOMINIO (Java puro)
│   ├── model/
│   │   └── Prenda.java                         # Estructura de datos directa del negocio
│   └── ports/
│       ├── in/
│       │   └── ListarPrendasUseCase.java       # Puerto IN (Caso de Uso)
│       └── out/
│           └── PrendaRepositoryPort.java       # Puerto OUT (Persistencia)
│
├── ⚙️ application/                             # CAPA 2: APLICACIÓN (Orquestación)
│   └── PrendaApplicationService.java           # Coordina los puertos IN y OUT
│
└── 🔌 infrastructure/                          # CAPA 3: INFRAESTRUCTURA (Detalles técnicos)
    └── adapters/
        ├── in/
        │   └── web/
        │       └── PrendaRestController.java   # Adaptador HTTP REST
        └── out/
            └── persistence/
                ├── PrendaMySqlAdapter.java     # Adaptador MySQL (implementa el Puerto OUT)
                ├── PrendaJpaEntity.java        # Entidad JPA para la tabla 'prendas'
                └── SpringDataPrendaRepository.java # JpaRepository
```

---

## 🗄️ Base de Datos MySQL
- **Host / Puerto:** `localhost:3306`
- **Base de Datos:** `tienda_ropa_db`
- **Usuario:** `admin`
- **Contraseña:** `1234`

---

## 🚀 Cómo Ejecutar

En la raíz del proyecto:
```bash
./mvnw spring-boot:run
```
O en Windows PowerShell:
```powershell
.\mvnw.cmd spring-boot:run
```

Endpoint para probar en navegador o Postman:
`http://localhost:8080/api/v1/prendas`
