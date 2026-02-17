# PRUEBA JAVA WINGS — Spring Boot MVC + Thymeleaf + MySQL

Aplicación web desarrollada con **Spring Boot MVC**, vistas con **Thymeleaf** y persistencia con **Spring Data JPA** en **MySQL**.  
Incluye login básico y mantenimiento (crear/editar/borrar) de productos y familias.

---

## Tecnologías usadas

- Java: 21
- Spring Boot (MVC)
- Thymeleaf
- Spring Data JPA (Hibernate)
- MySQL
- Maven
- Servidor embebido: Tomcat (Spring Boot)

---

## Requisitos

- Java 21 instalado
- Maven 
- MySQL Server MySQL Workbench

---

## Base de datos (MySQL)

1) Crear la base y tablas:

```sql
CREATE DATABASE PRUEBA_JAVA
  DEFAULT CHARACTER SET utf8mb4
  DEFAULT COLLATE utf8mb4_unicode_ci;

USE PRUEBA_JAVA;

CREATE TABLE USUARIOS (
  ID_USUARIO      VARCHAR(30)  NOT NULL,
  NOMBRE_USUARIO  VARCHAR(255) NOT NULL,
  CONTRASENA      VARCHAR(30)  NOT NULL,
  PRIMARY KEY (ID_USUARIO)
);

CREATE TABLE PRODUCTOS_FAMILIAS (
  ID_FAMILIA      INT(11)      NOT NULL AUTO_INCREMENT,
  NOMBRE_FAMILIA  VARCHAR(255) NOT NULL,
  PRIMARY KEY (ID_FAMILIA)
);

CREATE TABLE PRODUCTOS (
  ID_PRODUCTO      INT(11)       NOT NULL,     -- IMPORTANTE: el usuario ingresa este código
  NOMBRE_PRODUCTO  VARCHAR(255)  NOT NULL,
  ID_FAMILIA       INT(11)       NOT NULL,
  PRECIO           DECIMAL(10,2) NOT NULL,
  PRIMARY KEY (ID_PRODUCTO),
  CONSTRAINT FK_PRODUCTOS_FAMILIAS
    FOREIGN KEY (ID_FAMILIA) REFERENCES PRODUCTOS_FAMILIAS(ID_FAMILIA)
    ON UPDATE CASCADE
    ON DELETE RESTRICT
);

-- Usuario de prueba
INSERT INTO USUARIOS (ID_USUARIO, NOMBRE_USUARIO, CONTRASENA)
VALUES ('admin', 'Administrador', '1234');

-- Familias de ejemplo
INSERT INTO PRODUCTOS_FAMILIAS (NOMBRE_FAMILIA)
VALUES ('Bebidas'), ('Snacks');

-- Productos de ejemplo
INSERT INTO PRODUCTOS (ID_PRODUCTO, NOMBRE_PRODUCTO, ID_FAMILIA, PRECIO)
VALUES (1001, 'Coca Cola', 1, 1.25),
       (1002, 'Agua', 1, 0.75),
       (1003, 'Papas', 2, 1.10);
Nota: En este proyecto el ID_PRODUCTO NO es autoincremental, porque el usuario lo digita en el formulario.






Configuración (application.properties)

Configura tus credenciales en:

src/main/resources/application.properties

Ejemplo:

spring.application.name=pruebaJavaWings
spring.datasource.url=jdbc:mysql://localhost:3306/PRUEBA_JAVA?useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=admin

spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=true

Cómo ejecutar
Opción A: Maven (terminal)

En la raíz del proyecto:

mvn spring-boot:run


Acceso a la aplicación

URL: http://localhost:8080/login

Credenciales de prueba

Usuario: admin

Contraseña: 1234

Funcionalidades

Login (sesión)

Listado de productos

Botones globales: Nuevo / Editar / Borrar

Para Editar o Borrar se debe seleccionar un producto de la lista

Alta/edición de productos

El usuario ingresa el código del producto

Selección de familia existente o creación de nueva familia

Regla: si selecciona familia existente, no puede crear una nueva (y viceversa)

Estructura del proyecto (resumen)

controller/ Controladores MVC (Spring)

model/ Entidades JPA (Producto, Familia, Usuario)

repository/ Repositorios Spring Data JPA

resources/templates/ Vistas Thymeleaf (HTML)

resources/static/ (si agregas CSS/JS)
