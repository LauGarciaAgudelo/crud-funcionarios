USE funcionarios_db;

INSERT INTO tipo_documento (nombre) VALUES
('Cédula de ciudadanía'),
('Cédula de extranjería'),
('Pasaporte');

INSERT INTO estado_civil (nombre) VALUES
('Soltero'),
('Casado'),
('Unión libre'),
('Divorciado');

INSERT INTO formacion_academica (nivel) VALUES
('Bachiller'),
('Técnico'),
('Tecnólogo'),
('Profesional'),
('Especialización'),
('Maestría');

INSERT INTO funcionarios (
    tipo_documento_id,
    numero_documento,
    nombres,
    apellidos,
    fecha_nacimiento,
    direccion,
    telefono,
    correo,
    estado_civil_id,
    formacion_academica_id
) VALUES
(1, '100000001', 'Laura', 'Gómez Pérez', '1990-05-12', 'Calle 10 #20-30', '3001234567', 'laura.gomez@email.com', 1, 4),
(1, '100000002', 'Carlos', 'Ramírez López', '1985-09-22', 'Carrera 45 #12-15', '3019876543', 'carlos.ramirez@email.com', 2, 5);

INSERT INTO grupo_familiar (funcionario_id, nombre, parentesco, edad) VALUES
(1, 'Ana Gómez', 'Hija', 8),
(2, 'María López', 'Esposa', 35);