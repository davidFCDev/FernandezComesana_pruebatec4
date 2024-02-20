# Prueba técnica SpringBoot || Hack a Boss & Softtek

Última prueba técnica del Bootcamp de Java con Springboot. CRUD con apis, security y documentación.

## Contenido

- [Características](#características)
- [Requisitos](#requisitos)
- [Estructura](#estructura)
- [Instalación](#instalación)
- [Supuestos](#supuestos)

## Características

- Simulación de aplicación de reservas de habitiaciones de hotel y vuelos.
- Operaciones CRUD para cada servicio y modelos.
- Validación de datos, manejo de errores y excepciones.
- Documentación con Swagger.
- Test unitarios con Spring Security.


## Requisitos

- Java Development Kit (JDK) 17
- Apache Maven
- Base de datos SQL (archivo incluído):
    * DB: "prueba-tecnica"
    * Usuario: root
    * Contraseña: ""
- Spring Security - rutas protegidas
    * Usuario: hackaboss
    * Contraseña: 1234

## Estructura

- **../config** - Bean para security y ModelMapper
- **../controller** - Llamadas api
- **../dto** - Todos los modelos dto
- **../exception** - Excepciones personalizadas
- **../model** - Todos los modelos
- **../repository** - Gestión de los datos
- **../service** - Servicios para el CRUD

## Instalación

- Descargar el proyecto y abrirlo en IntelliJ
- Abrir el xampp u otro gestor de base de datos. Añadir la base de datos (archivo sql) que se encuentra en /resources.
- Se incluye lista de peticiones para Postman. El archivo se encuentra en /resources.
- Se incluye documentación con Swagger http://localhost:8080/doc/swagger-ui.html.
- Lanzar el proyecto y utilizar los distintos endpoints desde Swagger o Postman. Se incluyen tests unitarios.


## Supuestos
- Se modifican los JSON de referencia de la consigna para una mejor implementación de los datos.
- Aunque no se indica, se crea una tabla de clientes en la base de datos.
- Se utiliza ModelMapper para facilitar la conversion de los modelos en dto.
- No se menciona el número de personas en la reserva, se calcula mediante los objetos de persona.
- Debido a los parámetros de las reservas, sólo se puede hacer update de las fechas. En otros casos se deben eliminar.
- No se permiten eliminar hoteles o vuelos con reservas pendientes.
- Para la creación de los registros se usa un flujo de trabajo natural. EJ.: se crea un vuelo, se crean los clientes y por último se añaden las reservas, no se crea una reserva en forma de cascada.
