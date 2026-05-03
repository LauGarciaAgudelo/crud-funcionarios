CREATE DATABASE IF NOT EXISTS funcionarios_db;
USE funcionarios_db;

CREATE TABLE tipo_documento (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL
);

CREATE TABLE estado_civil (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL
);

CREATE TABLE formacion_academica (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nivel VARCHAR(100) NOT NULL
);

CREATE TABLE funcionarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    tipo_documento_id INT NOT NULL,
    numero_documento VARCHAR(20) NOT NULL UNIQUE,
    nombres VARCHAR(100) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    fecha_nacimiento DATE NOT NULL,
    direccion VARCHAR(150),
    telefono VARCHAR(30),
    correo VARCHAR(100),
    estado_civil_id INT NOT NULL,
    formacion_academica_id INT NOT NULL,

    FOREIGN KEY (tipo_documento_id) REFERENCES tipo_documento(id),
    FOREIGN KEY (estado_civil_id) REFERENCES estado_civil(id),
    FOREIGN KEY (formacion_academica_id) REFERENCES formacion_academica(id)
);

CREATE TABLE grupo_familiar (
    id INT AUTO_INCREMENT PRIMARY KEY,
    funcionario_id INT NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    parentesco VARCHAR(50) NOT NULL,
    edad INT,

    FOREIGN KEY (funcionario_id) REFERENCES funcionarios(id)
);