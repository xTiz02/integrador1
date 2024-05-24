
/* Creamos algunos usuarios con sus roles */
INSERT INTO `users` (apodo,enabled,fecha_creado,fecha_actualizado,password,username) VALUES ('Piero',1,now(),null,'$2a$10$O9wxmH/AeyZZzIS09Wp8YOEMvFnbRVJ8B4dmAMVSGloR62lj.yqXG','usuario');

INSERT INTO `users` (apodo,enabled,fecha_creado,fecha_actualizado,password,username) VALUES ('Luis',1,now(),null,'$2a$10$DOMDxjYyfZ/e7RcBfUpzqeaCs8pLgcizuiQWXPkU35nOhZlFcE9MS','admin');

INSERT INTO `authorities` (authority) VALUES ('ROLE_USER');
INSERT INTO `authorities` (authority) VALUES ('ROLE_ADMIN');
INSERT INTO `user_roles` (usuario_id,rol_id) VALUES (1,1);
INSERT INTO `user_roles` (usuario_id,rol_id) VALUES (2,1);
INSERT INTO `user_roles` (usuario_id,rol_id) VALUES (2,2);
/*Libros*/
INSERT INTO libreria_sistema.libro (id, autor, editorial, estado, fecha_publicacion, isbn, tag, titulo, stockDisponible, stockPrestado, stockTotal, fecha_actualizado) VALUES (1, 'Robert', 'Canvo', 1, '2023-10-25 14:48:11.000000', '91273800', 'FA-001', 'Cuento de hadas', 3, 0, 3, null);
INSERT INTO libreria_sistema.libro (id, autor, editorial, estado, fecha_publicacion, isbn, tag, titulo, stockDisponible, stockPrestado, stockTotal, fecha_actualizado) VALUES (2, 'Julian', 'Libra', 1, '2023-10-25 14:48:11.000000', '31276800', 'DR-001', 'Capital', 3, 0, 3, null);
INSERT INTO libreria_sistema.libro (id, autor, editorial, estado, fecha_publicacion, isbn, tag, titulo, stockDisponible, stockPrestado, stockTotal, fecha_actualizado) VALUES (3, 'Mario Vargas Llosa', 'Editorial', 1, '2023-10-25 14:48:11.000000', '81276800', 'DR-002', 'Paco Yunque', 3, 0, 3, null);
INSERT INTO libreria_sistema.libro (id, autor, editorial, estado, fecha_publicacion, isbn, tag, titulo, stockDisponible, stockPrestado, stockTotal, fecha_actualizado) VALUES (4, 'Celia', 'ENDD', 1, '2023-10-25 14:48:11.000000', '71276800', 'HO-001', 'Niebla', 3, 0, 3, null);
INSERT INTO libreria_sistema.libro (id, autor, editorial, estado, fecha_publicacion, isbn, tag, titulo, stockDisponible, stockPrestado, stockTotal, fecha_actualizado) VALUES (5, 'Juan', 'ENDD', 1, '2024-04-22 19:29:02.394000', '2624513178', 'DR-007', 'Brisa', 3, 0, 3, null);


/*Unidades*/
INSERT INTO libreria_sistema.librounidad (id, estado, fecha_ingreso, isbn, libro_id, nota, ubicacion) VALUES (1, 1, '2024-04-24 01:00:00.000000', '91273800', 1, '', 'sala3');
INSERT INTO libreria_sistema.librounidad (id, estado, fecha_ingreso,isbn, libro_id, nota, ubicacion) VALUES (2, 1, '2024-04-24 01:00:00.000000', '91273800', 1, '', 'sala3');
INSERT INTO libreria_sistema.librounidad (id, estado, fecha_ingreso,isbn, libro_id, nota, ubicacion) VALUES (3, 1, '2024-04-24 01:00:00.000000', '91273800', 1, '', 'sala3');
INSERT INTO libreria_sistema.librounidad (id, estado, fecha_ingreso,  isbn, libro_id, nota, ubicacion) VALUES (4, 1, '2024-04-24 01:00:00.000000', '31276800', 2, '', 'sala3');
INSERT INTO libreria_sistema.librounidad (id, estado, fecha_ingreso, isbn, libro_id, nota, ubicacion) VALUES (5, 1, '2024-04-24 01:00:00.000000', '31276800', 2, '', 'sala3');
INSERT INTO libreria_sistema.librounidad (id, estado, fecha_ingreso,  isbn, libro_id, nota, ubicacion) VALUES (6, 1, '2024-04-24 01:00:00.000000', '31276800', 2, '', 'sala3');
INSERT INTO libreria_sistema.librounidad (id, estado, fecha_ingreso, isbn, libro_id, nota, ubicacion) VALUES (7, 1, '2024-04-24 01:00:00.000000', '81276800', 3, '', 'sala3');
INSERT INTO libreria_sistema.librounidad (id, estado, fecha_ingreso,  isbn, libro_id, nota, ubicacion) VALUES (8, 1, '2024-04-24 01:00:00.000000', '81276800', 3, '', 'sala3');
INSERT INTO libreria_sistema.librounidad (id, estado, fecha_ingreso,  isbn, libro_id, nota, ubicacion) VALUES (9, 1, '2024-04-24 01:00:00.000000', '81276800', 3, '', 'sala3');
INSERT INTO libreria_sistema.librounidad (id, estado, fecha_ingreso,  isbn, libro_id, nota, ubicacion) VALUES (10, 1, '2024-04-24 01:00:00.000000', '71276800', 4, '', 'sala3');
INSERT INTO libreria_sistema.librounidad (id, estado, fecha_ingreso, isbn, libro_id, nota, ubicacion) VALUES (11, 1, '2024-04-24 01:00:00.000000', '71276800', 4, '', 'sala3');
INSERT INTO libreria_sistema.librounidad (id, estado, fecha_ingreso, isbn, libro_id, nota, ubicacion) VALUES (12, 1, '2024-04-24 01:00:00.000000', '71276800', 4, '', 'sala3');
INSERT INTO libreria_sistema.librounidad (id, estado, fecha_ingreso,  isbn, libro_id, nota, ubicacion) VALUES (13, 1, '2024-04-24 01:00:00.000000', '2624513178', 5, '', 'sala3');
INSERT INTO libreria_sistema.librounidad (id, estado, fecha_ingreso,  isbn, libro_id, nota, ubicacion) VALUES (14, 1, '2024-04-24 01:00:00.000000', '2624513178', 5, '', 'sala3');
INSERT INTO libreria_sistema.librounidad (id, estado, fecha_ingreso,  isbn, libro_id, nota, ubicacion) VALUES (15, 1, '2024-04-24 01:00:00.000000', '2624513178', 5, '', 'sala3');


/*Categorías*/
INSERT INTO `categoria` (fecha_creacion,nombre,nombre_corto, notas) VALUES ('2023-10-25 18:36:22', 'Acción', 'AC','Categoría de acción');
INSERT INTO `categoria` (fecha_creacion,nombre,nombre_corto) VALUES ('2023-10-25 19:36:22', 'Fantasia', 'FA');
INSERT INTO `categoria` (fecha_creacion,nombre,nombre_corto) VALUES ('2023-10-25 19:36:22', 'Horror', 'HO');
INSERT INTO `categoria` (fecha_creacion,nombre,nombre_corto) VALUES ('2023-10-25 19:36:22', 'Drama', 'DR');
INSERT INTO `categoria` (fecha_creacion,nombre,nombre_corto) VALUES ('2023-10-25 19:36:22', 'Familiar', 'FAM');

/*Categorías de los libros*/
INSERT INTO `libro_categoria` (libro_id,categoria_id) VALUES (1,1);
INSERT INTO `libro_categoria` (libro_id,categoria_id) VALUES (1,2);
INSERT INTO `libro_categoria` (libro_id,categoria_id) VALUES (2,3);
INSERT INTO `libro_categoria` (libro_id,categoria_id) VALUES (2,4);
INSERT INTO `libro_categoria` (libro_id,categoria_id) VALUES (3,4);
INSERT INTO `libro_categoria` (libro_id,categoria_id) VALUES (3,5);
INSERT INTO `libro_categoria` (libro_id,categoria_id) VALUES (4,3);
INSERT INTO `libro_categoria` (libro_id,categoria_id) VALUES (4,4);
INSERT INTO `libro_categoria` (libro_id,categoria_id) VALUES (5,3);
INSERT INTO `libro_categoria` (libro_id,categoria_id) VALUES (5,5);


insert into miembros (apellido, contacto, dni, email, fecha_ingreso, fecha_nac, genero, primer_nombre, segundo_nombre, tipo) VALUES ('Perez', '123456789', '82345678', 'juan@gmail.com', '2019-01-01', '2019-01-01', 'M', 'Juan', 'Lorenzo', 'Estudiante');
insert into miembros (apellido, contacto, dni, email, fecha_ingreso, fecha_nac, genero, primer_nombre, segundo_nombre, tipo) VALUES ('Gomez', '987654321', '87654321', 'gomez@gmail.com', '2018-02-15', '1995-05-20', 'F', 'Maria', 'Isabel', 'Otro');
insert into miembros (apellido, contacto, dni, email, fecha_ingreso, fecha_nac, genero, primer_nombre, segundo_nombre, tipo) VALUES ('Rodriguez', '555555555', '76634327', 'rodriguez@gmail.com', '2020-03-10', '1980-11-08', 'M', 'Carlos', 'Alberto', 'Padre');
insert into miembros (apellido, contacto, dni, email, fecha_ingreso, fecha_nac, genero, primer_nombre, segundo_nombre, tipo) VALUES ('Lopez', '123123123', '82253341', 'lopez@gmail.com', '2021-06-22', '2002-07-12', 'F', 'Ana', 'Carolina', 'Padre');
insert into miembros (apellido, contacto, dni, email, fecha_ingreso, fecha_nac, genero, primer_nombre, segundo_nombre, tipo) VALUES ('Martinez', '777888999', '56682721', 'martinez@gmail.com', '2017-12-05', '1998-09-28', 'M', 'Javier', 'Andres', 'Estudiante');
