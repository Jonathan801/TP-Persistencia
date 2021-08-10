DROP SCHEMA IF EXISTS bichomon;
CREATE SCHEMA bichomon;

USE bichomon;
CREATE TABLE especie (
  id serial NOT NULL,
  nombre VARCHAR(255) NOT NULL UNIQUE,
  peso int NOT NULL,
  altura int NOT NULL,
  tipo VARCHAR(255) NOT NULL,
  cantidadBichos int NOT NULL,
  PRIMARY KEY (id)
)


engine = InnoDB;

