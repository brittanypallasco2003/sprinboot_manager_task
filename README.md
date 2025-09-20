# Spring Boot Manager Task

Base URL: `http://localhost:8080/api/v1`

> [!NOTE]  
> Todos los endpoints de **tareas** requieren **Bearer Token** en el header `Authorization: Bearer <token>`.

---

## Tabla de Contenido

- [Tecnologías utilizadas](#tecnologías-utilizadas)
- [Instalación local con Docker Compose](#instalación-local-con-docker-compose)
  - [Variables de Entorno](#variables-de-entorno)
- [Postman](#postman)
- [Documentación](#documentación)
  - [Usuarios](#usuarios)
    - [Registrar usuario](#registrar-usuario)
    - [Login usuario](#login-usuario)
  - [Tareas](#tareas)
    - [Crear tarea](#crear-tarea)
    - [Obtener todas las tareas del usuario](#obtener-todas-las-tareas-del-usuario)
    - [Obtener tarea por ID](#obtener-tarea-por-id)
    - [Editar tarea](#editar-tarea)
    - [Eliminar tarea](#eliminar-tarea)

---

## Tecnologías utilizadas

| Tecnología                                                                                  | Descripción                                                                                             | Documentación Oficial                                                                                  |
| ------------------------------------------------------------------------------------------- | ------------------------------------------------------------------------------------------------------- | ------------------------------------------------------------------------------------------------------ |
| [Java 21](https://openjdk.org/projects/jdk/21/)                                             | Lenguaje de programación principal utilizado para desarrollar la aplicación.                            | [Documentación](https://docs.oracle.com/en/java/javase/21/)                                            |
| [Spring Boot](https://spring.io/projects/spring-boot)                                       | Framework para crear aplicaciones Java de manera rápida y sencilla.                                     | [Documentación](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/)                 |
| [Spring Security](https://spring.io/projects/spring-security)                               | Módulo de Spring para manejar autenticación y autorización.                                             | [Documentación](https://docs.spring.io/spring-security/reference/)                                     |
| [JWT (JSON Web Tokens)](https://jwt.io/)                                                    | Estándar para autenticación y transmisión segura de información entre partes como un objeto JSON.       | [Documentación](https://jwt.io/introduction)                                                           |
| [Spring Data JPA](https://spring.io/projects/spring-data-jpa)                               | Abstracción sobre JPA para el acceso a datos con bases relacionales como MySQL.                         | [Documentación](https://docs.spring.io/spring-data/jpa/reference/jpa.html)                             |
| [Spring Validation](https://docs.spring.io/spring-framework/reference/core/validation.html) | Módulo de validación de datos de entrada, especialmente útil en DTOs.                                   | [Documentación](https://docs.spring.io/spring-framework/reference/core/validation/beanvalidation.html) |
| [Lombok](https://projectlombok.org/)                                                        | Librería para reducir el código repetitivo (getters, setters, constructores, etc.).                     | [Documentación](https://projectlombok.org/features/all)                                                |
| [PostgreSQL](https://www.postgresql.org/)                                                   | Base de datos relacional utilizada para almacenar la información de usuarios, talleres, registros, etc. | [Documentación](https://www.postgresql.org/docs/)                                                      |
| [Docker](https://www.docker.com/)                                                           | Contenerización de la aplicación para facilitar su despliegue.                                          | [Documentación](https://docs.docker.com/)                                                              |
| [Docker Compose](https://docs.docker.com/compose/)                                          | Orquestación de múltiples contenedores (aplicación y base de datos) en desarrollo local.                | [Documentación](https://docs.docker.com/compose/)                                                      |


## Instalación local con Docker Compose

Clona este repositorio:

```bash
git clone https://github.com/brittanypallasco2003/sprinboot_manager_task.git
```

Ve al directorio del proyecto

```bash
cd sprinboot_manager_task
```

3. Copia el archivo .env.example a .env:

```bash
cp .env.example .env
```

### Variables de Entorno

Es obligatorio editarlo antes de ejecutar el proyecto, y reemplazar los valores por los tuyos reales:

| Variable                | Descripción                                                                                                      |
| ----------------------- | ---------------------------------------------------------------------------------------------------------------- |
| `SPRING_DATASOURCE_DB`        | Nombre de la base de datos que usará la aplicación.                                                              |
| `SPRING_DATASOURCE_USERNAME`            | Usuario que la aplicación utilizará para acceder a la base de datos.                                             |
| `SPRING_DATASOURCE_PASSWORD`        | Contraseña del usuario especificado en `SPRING_DATASOURCE_USERNAME`.                                                             |
| `SPRING_DATASOURCE_URL` | URL de conexión a la base de datos (generalmente `jdbc:mysql://db:5432/nombre_bd` cuando se usa Docker Compose). |
| `JWT_SECRET`            | Clave secreta utilizada para firmar y validar los tokens JWT.                                                    |
| `USER_GENERATOR`        | Entidad firmante del token                                                                                       |


4. Levanta los contenedores

```bash
docker-compose up --build
```

5. Las tablas se crearán automáticamente gracias a JPA/Hibernate (no hay datos iniciales insertados).


## Postman
> [!NOTE]  
> Se incluye una colección Postman ["API_Manager_tasks.postman_collection"](./docs/API_Manager_tasks.postman_collection.json) para probar todos los endpoints expuestos a continuación

## Documentación

### Usuarios

#### Registrar usuario

- **Método:** `POST`
- **Endpoint:** `/auth/register`
- **Autenticación:** No requerida

**Descripción:** Registra un nuevo usuario.

**Request Body:**

```json
{
  "nombre": "Juan Perez",
  "email": "juan@example.com",
  "password": "contraseña123"
}
```

**Respuesta Exitosa (201 Created):**

```json
{
  "mensaje": "Usuario Registrado",
  "id": 1,
  "nombre": "Juan Perez",
  "email": "juan@example.com"
}
```

**Errores Comunes:**

| Código | Descripción                                    |
| ------ | ---------------------------------------------- |
| 400    | Campos obligatorios faltantes o email inválido |
| 400    | El email ya está registrado                    |

#### Login usuario

- **Método:** `POST`
- **Endpoint:** `/auth/login`
- **Autenticación:** No requerida

**Descripción:** Inicia sesión y obtiene un token JWT..

**Request Body:**

```json
{
  "email": "juan@example.com",
  "password": "contraseña123"
}
```

**Respuesta Exitosa (200 OK):**

```json
{
  "mensaje": "Hola juan@example.com",
  "email": "juan@example.com",
  "token": "jwt_token_aqui"
}
```

**Errores Comunes:**

| Código | Descripción              |
| ------ | ------------------------ |
| 401    | Credenciales incorrectas |

### Tareas

#### Crear tarea

- **Método:** `POST`
- **Endpoint:** `/tareas`
- **Autenticación:** Requerida

**Descripción:** Crea una nueva tarea para el usuario autenticado.

**Request Body:**

```json
{
  "titulo": "Comprar leche",
  "descripcion": "Ir al supermercado",
  "estado": "PENDIENTE"
}
```

**Respuesta Exitosa (201 Created):**

```json
{
  "id": 11,
  "titulo": "Comprar leche",
  "estado": "PENDIENTE",
  "createdAt": "2025-09-19T23:19:57.3776775",
  "updatedAt": null,
  "userId": 1,
  "description": "Ir al supermercado"
}
```

**Errores Comunes:**

| Código | Descripción                                     |
| ------ | ----------------------------------------------- |
| 400    | Datos inválidos o campos obligatorios faltantes |
| 401    | No autorizado, token JWT faltante o inválido    |

#### Obtener todas las tareas del usuario

- **Método:** GET
- **Endpoint:** /tareas
- **Autenticación:** Requerida

**Descripción:** Obtiene todas las tareas del usuario autenticado.

**Respuesta Exitosa (200 OK):**

```json
[
  {
    "id": 3,
    "titulo": "pintar",
    "estado": "PENDIENTE",
    "createdAt": "2025-09-19T19:27:30.996062",
    "updatedAt": null,
    "userId": 1,
    "description": "terminar cuadro"
  },
  {
    "id": 4,
    "titulo": "arreglar cuarto",
    "estado": "EN_PROGRESO",
    "createdAt": "2025-09-19T19:43:11.495102",
    "updatedAt": null,
    "userId": 1,
    "description": "barrer y trapear"
  }
]
```

#### Obtener tarea por ID

- **Método:** GET
- **Endpoint:** /tareas/{id}
- **Autenticación:** Requerida

**Descripción:** Obtiene la tarea por su ID si pertenece al usuario autenticado.

**Respuesta Exitosa (200 OK):**

```json
{
  "id": 3,
  "titulo": "pintar",
  "estado": "PENDIENTE",
  "createdAt": "2025-09-19T19:27:30.996062",
  "updatedAt": null,
  "userId": 1,
  "description": "terminar cuadro"
}
```

**Errores Comunes:**

| Código | Descripción                              |
| ------ | ---------------------------------------- |
| 404    | La tarea no existe                       |
| 403    | No tienes permitido acceder a esta tarea |

#### Editar tarea

- **Método:** PUT
- **Endpoint:** /tareas/{id}
- **Autenticación:** Requerida

**Descripción:** Actualiza una tarea existente.

**Request Body:**

```json
{
  "titulo": "Estudiar Java Avanzado",
  "descripcion": "Practicar Spring Boot y Hibernate",
  "estado": "EN_PROGRESO"
}
```

**Respuesta Exitosa (200 OK):**

```json
{
  "id": 8,
  "titulo": "Estudiar Java Avanzado",
  "descripcion": "Practicar Spring Boot y Hibernate",
  "estado": "EN_PROGRESO",
  "createdAt": "2025-09-19T19:27:30.996062",
  "updatedAt": "2025-10-19T19:27:30.996062",
  "userId": 1
}
```

**Errores Comunes:**

| Código | Descripción                           |
| ------ | ------------------------------------- |
| 400    | Datos inválidos                       |
| 404    | La tarea no existe                    |
| 403    | No tienes permitido editar esta tarea |

#### Eliminar tarea

- **Método:** DELETE
- **Endpoint:** /tareas/{id}
- **Autenticación:** Requerida

**Descripción:** Elimina la tarea especificada.

**Respuesta Exitosa (204 No Content): No retorna contenido:**

**Errores Comunes:**

| Código | Descripción                              |
| ------ | ---------------------------------------- |
| 404    | La tarea no existe                       |
| 403    | No tienes permitido acceder a esta tarea |
