# 🛍️ Tienda de Ropa - Backend con Arquitectura Hexagonal

Proyecto backend elaborado en **Java 17** y **Spring Boot 3.4.2** conectado a **MySQL** mediante **Spring Data JPA y Hibernate**, diseñado para explicar de manera clara, directa y sin rodeos cómo funciona la **Arquitectura Hexagonal (Puertos y Adaptadores)**.

Este proyecto se enfoca en una única funcionalidad: **Listar prendas (`GET /api/v1/prendas`)**, eliminando la complejidad innecesaria para que la separación de capas sea 100% visible y fácil de entender.

---

## 🧭 1. ¿Qué es la Arquitectura Hexagonal?

La **Arquitectura Hexagonal** (creada por Alistair Cockburn) propone que el **núcleo de tu aplicación (el Dominio)** debe estar completamente aislado y protegido del mundo exterior.

- Tu lógica de negocio **no debe saber** si los datos se guardan en MySQL, Postgres o un archivo de texto.
- Tu lógica de negocio **no debe saber** si las peticiones vienen de una página web, de Postman o de una aplicación móvil.

Para lograr esto, la arquitectura utiliza la metáfora de **Puertos y Adaptadores**:
- **Puertos:** Son enchufes o contratos (interfaces Java) que dicen qué se puede hacer.
- **Adaptadores:** Son los cables concretos que se conectan a esos enchufes para hablar con tecnologías reales (HTTP, MySQL, etc.).

---

## 📂 2. Estructura de Paquetes del Proyecto

```text
com.tiendaropa/
│
├── 💎 domain/                                  # CAPA 1: DOMINIO (Java puro, cero frameworks)
│   ├── model/
│   │   └── Prenda.java                         # Estructura de datos directa del negocio
│   └── ports/                                  # Los contratos / enchufes
│       ├── in/
│       │   └── ListarPrendasUseCase.java       # Puerto IN (Caso de uso / ¿Qué puedo pedir?)
│       └── out/
│           └── PrendaRepositoryPort.java       # Puerto OUT (Persistencia / ¿Qué datos necesito?)
│
├── ⚙️ application/                             # CAPA 2: APLICACIÓN (El director de orquesta)
│   └── PrendaApplicationService.java           # Conecta el Puerto IN con el Puerto OUT
│
└── 🔌 infrastructure/                          # CAPA 3: INFRAESTRUCTURA (Tecnologías externas)
    └── adapters/
        ├── in/web/
        │   └── PrendaRestController.java       # Adaptador HTTP (Recibe el GET y llama al puerto IN)
        └── out/persistence/
            ├── PrendaMySqlAdapter.java         # Adaptador MySQL (Implementa el puerto OUT)
            ├── PrendaJpaEntity.java            # Molde JPA para crear la tabla 'prendas'
            └── SpringDataPrendaRepository.java # Consultas SQL automáticas con Spring Data
```

---

## 🔍 3. Explicación Clase por Clase: ¿Qué se está haciendo?

### 💎 CAPA 1: DOMINIO (`com.tiendaropa.domain`)

#### 📄 `model/Prenda.java`
- **¿Qué es?** Es el objeto que representa una prenda de vestir en nuestra tienda.
- **¿Qué hace?** Contiene los campos directos: `id`, `nombre`, `categoria`, `talla`, `precio` y `stock`.
- **Detalle clave:** Es Java puro. No tiene anotaciones de base de datos (`@Entity`, `@Table`) ni de Spring. No utiliza POO compleja, getters ni setters; son variables públicas directas para facilitar su lectura.

#### 📄 `ports/in/ListarPrendasUseCase.java` (Puerto de Entrada)
- **¿Qué es?** Una interfaz Java.
- **¿Qué hace?** Define el caso de uso del sistema: `List<Prenda> listar();`.
- **Por qué existe:** Es el contrato que le dice al controlador web: *"Si quieres usar mi sistema, este es el único método que tienes permitido invocar"*.

#### 📄 `ports/out/PrendaRepositoryPort.java` (Puerto de Salida)
- **¿Qué es?** Una interfaz Java.
- **¿Qué hace?** Define la necesidad de persistencia: `List<Prenda> obtenerTodas();`.
- **Por qué existe:** Aplica el **Principio de Inversión de Dependencias (DIP)**. El dominio no depende de MySQL; el dominio pone las reglas mediante esta interfaz y la base de datos debe adaptarse a ella.

---

### ⚙️ CAPA 2: APLICACIÓN (`com.tiendaropa.application`)

#### 📄 `PrendaApplicationService.java`
- **¿Qué es?** Una clase con la anotación `@Service`.
- **¿Qué hace?** 
  1. Implementa el Puerto IN (`ListarPrendasUseCase`).
  2. Inyecta mediante `@Autowired` el Puerto OUT (`PrendaRepositoryPort`).
  3. Cuando alguien le pide listar prendas, se las solicita al puerto de salida:
     ```java
     return prendaRepositoryPort.obtenerTodas();
     ```
- **Por qué existe:** Actúa como el puente entre el mundo exterior que pide cosas y la base de datos que guarda cosas, sin acoplarse a ninguna tecnología.

---

### 🔌 CAPA 3: INFRAESTRUCTURA (`com.tiendaropa.infrastructure`)

#### 📄 `adapters/in/web/PrendaRestController.java` (Adaptador de Entrada)
- **¿Qué es?** El controlador REST con `@RestController` y `@RequestMapping("/api/v1/prendas")`.
- **¿Qué hace?** Expone el endpoint HTTP `GET`. Cuando un cliente o navegador hace una petición, el controlador no busca en la base de datos; simplemente llama a:
  ```java
  listarPrendasUseCase.listar();
  ```
- **Respuesta:** Devuelve directamente la lista de `Prenda` que Spring Boot convierte en formato JSON.

#### 📄 `adapters/out/persistence/PrendaJpaEntity.java`
- **¿Qué es?** La entidad técnica de Hibernate con `@Entity` y `@Table(name = "prendas")`.
- **¿Qué hace?** Es el molde técnico para MySQL. Le dice a Hibernate cómo debe llamarse la tabla y qué columnas debe tener en la base de datos.

#### 📄 `adapters/out/persistence/SpringDataPrendaRepository.java`
- **¿Qué es?** Una interfaz que extiende de `JpaRepository<PrendaJpaEntity, Long>`.
- **¿Qué hace?** Spring Data genera en tiempo de ejecución las consultas SQL reales (`SELECT * FROM prendas`) sin que tengamos que escribir sentencias SQL manuales.

#### 📄 `adapters/out/persistence/PrendaMySqlAdapter.java` (Adaptador de Salida)
- **¿Qué es?** La clase que implementa el contrato `PrendaRepositoryPort`.
- **¿Qué hace (La Traducción)?**
  1. Consulta a MySQL a través de `repository.findAll()`, obteniendo objetos técnicos `PrendaJpaEntity`.
  2. Mediante un bucle `for`, extrae los datos y los copia a la clase limpia `Prenda` del dominio:
     ```java
     Prenda p = new Prenda();
     p.id = e.getId();
     p.nombre = e.getNombre();
     p.categoria = e.getCategoria();
     p.talla = e.getTalla();
     p.precio = e.getPrecio();
     p.stock = e.getStock();
     prendas.add(p);
     ```
- **Por qué existe:** Porque MySQL habla en términos de entidades JPA y tablas, mientras que tu Dominio habla en términos de objetos limpios. Este adaptador es el traductor entre ambos mundos.

---

## 🌊 4. Flujo Completo: ¿Cómo viajan los datos paso a paso?

Cuando abres en tu navegador o Postman: `GET http://localhost:8080/api/v1/prendas`:

```text
1. CLIENTE (Navegador / Postman)
   │  Envía: GET http://localhost:8080/api/v1/prendas
   ▼
2. PrendaRestController (Adaptador IN)
   │  Atrapa la petición HTTP en el puerto 8080.
   ▼  Invoca el método listar() del contrato de entrada.
3. ListarPrendasUseCase (Puerto IN - Interfaz)
   │
   ▼  Llega a la implementación en la capa de aplicación.
4. PrendaApplicationService (Servicio de Aplicación)
   │  Ejecuta el caso de uso y solicita los datos al puerto de salida.
   ▼  Invoca el método obtenerTodas() del contrato de salida.
5. PrendaRepositoryPort (Puerto OUT - Interfaz)
   │
   ▼  Llega a la implementación en la capa de infraestructura.
6. PrendaMySqlAdapter (Adaptador OUT)
   │  Usa SpringDataPrendaRepository para consultar la base de datos.
   ▼  Ejecuta: SELECT * FROM prendas;
7. MySQL (Tabla 'prendas')
   │  Retorna las filas de la base de datos.
   ▼
8. PrendaMySqlAdapter (Traducción de datos)
   │  Toma cada fila de base de datos (PrendaJpaEntity) y la convierte
   │  a la estructura limpia del Dominio (Prenda).
   ▼
9. PrendaApplicationService ──► PrendaRestController
   │  La lista List<Prenda> regresa hasta el controlador web.
   ▼
10. RESPUESTA AL CLIENTE (JSON)
    Spring Boot serializa la lista de prendas y la muestra en pantalla:
    [
      {
        "id": 1,
        "nombre": "Camisa Oxford Manga Larga Slim Fit",
        "categoria": "CAMISAS",
        "talla": "M",
        "precio": 89.90,
        "stock": 25
      },
      {
        "id": 2,
        "nombre": "Pantalon Jean Clasico Denim",
        "categoria": "PANTALONES",
        "talla": "L",
        "precio": 129.50,
        "stock": 18
      }
    ]
```

---

## 🗄️ 5. Configuración de Base de Datos

En el archivo `src/main/resources/application.properties`:
- **URL:** `jdbc:mysql://localhost:3306/tienda_ropa_db`
- **Usuario:** `admin`
- **Contraseña:** `1234`
- **Modo DDL:** `update` (crea y actualiza la tabla automáticamente si no existe)

---

## 🚀 6. Cómo Ejecutar el Proyecto

### Desde Spring Tool Suite (STS) / Eclipse:
1. Abre STS y ve a **File** ➔ **Import...** ➔ **Maven** ➔ **Existing Maven Projects**.
2. Selecciona la carpeta del proyecto.
3. Clic derecho sobre el proyecto ➔ **Run As** ➔ **Spring Boot App**.

### Desde la Consola (Terminal):
En la raíz del proyecto ejecuta:
```powershell
.\mvnw.cmd spring-boot:run
```

Abre tu navegador en:
👉 `http://localhost:8080/api/v1/prendas`
