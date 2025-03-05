
-- puntos
INSERT INTO calculadordepuntos (id, coeficiente_heladeras_activas, coeficiente_pesos_donados, coeficiente_tarjetas_repartidas, coeficiente_viandas_distribuidas, coeficiente_viandas_donadas, fechaAlta ) VALUES ( 1, 5.0, 0.5, 2.0, 1.0, 1.5, '2024-11-25' );

-- Insersion de roles
INSERT INTO rol (id, nombre) VALUES (1, 'admin');
INSERT INTO rol (id, nombre) VALUES (2, 'persona');
INSERT INTO rol (id, nombre) VALUES (3, 'personaJuridica');
INSERT INTO rol (id, nombre) VALUES (4, 'tecnico');

-- CREAR USUARIO ADMIN - ADMIN
INSERT INTO usuarios (username, password, rol_id)  VALUES ('admin', '$2a$12$Ftlo91KwVad7ttunftiNseVEZ9FocXD/xSl/qGpYw2IJ.Z5WqvNNS', 1);
INSERT INTO personas_humanas (id, altura, calle, codigoPostal, localidad, provincia, eliminado, preferenciaContacto, puntosCanjeados, puntosDisponibles, puntosTotales, tarjeta_id, username, apellido, documento, tipoDocumento, fechaNacimiento, nombre)  VALUES (1, 9800 , 'Av. Rivadavia', 1407, 'CABA', 'Buenos Aires', 0, 'MAIL', 0,100, 100, NULL, 'admin', 'Doe', '12345678', 'DNI', NULL, 'Jhon');
INSERT INTO contactos (id, tipo, valor, tecnico_id, colaborador_id) VALUES (1, 'MAIL', 'asd@frba.utn.edu.ar', NULL, 1);

-- Dir Heladera 1 y 2
INSERT INTO lugares (id, altura, calle, codigoPostal, localidad, provincia, latitud, longitud) VALUES (1, 5828, 'Ramon Falcon', 1407, 'CABA', 'Buenos Aires', -34.640027569387755, -58.508999108163266);
INSERT INTO lugares (id, altura, calle, codigoPostal, localidad, provincia, latitud, longitud) VALUES (2, 2300, 'Mozart', '1407', 'CABA', 'Buenos Aires', -34.6597832, -58.4680729);


-- Formas de Colaboracion
INSERT INTO formas_de_colaboracion (id, nombre, realizablePor) VALUES (1, 'Colocacion de Heladera', 'JURIDICA');
INSERT INTO formas_de_colaboracion (id, nombre, realizablePor) VALUES (2, 'Registro Persona Vulnerable', 'HUMANA');
INSERT INTO formas_de_colaboracion (id, nombre, realizablePor) VALUES (3, 'Donacion de Vianda', 'HUMANA');
INSERT INTO formas_de_colaboracion (id, nombre, realizablePor) VALUES (4, 'Ofertar', 'JURIDICA');
INSERT INTO formas_de_colaboracion (id, nombre, realizablePor) VALUES (5, 'Donacion de Dinero', 'TODOS');
INSERT INTO formas_de_colaboracion (id, nombre, realizablePor) VALUES (6, 'Distribucion de Vianda', 'HUMANA');

-- Heladera 1
INSERT INTO heladeras (id,cant_viandas ,capacidadEnUnidades, eliminada, estaActiva, fechaAlta, fechaColocada, fechaUltimoEstado, nombre, temperaturaMaxima, temperaturaMinima, lugar_id) VALUES (1,0 ,100, NULL, 1, '2024-11-25 11:22:42', '2024-11-25', NULL, 'Heladera Falcon', 10, -8, 1);

-- Heladera 2
INSERT INTO heladeras (id,cant_viandas ,capacidadEnUnidades, eliminada, estaActiva, fechaAlta, fechaColocada, fechaUltimoEstado, nombre, temperaturaMaxima, temperaturaMinima, lugar_id) VALUES (2,0 ,200, NULL, 1, '2024-11-25 11:44:50', '2024-11-25', NULL, 'Heladera Campus UTN', 8, -12, 2);

-- Colocaciones de Heladera
INSERT INTO colocaciones_de_heladera (id, fechaAlta, pendiente, colaborador_id, formaDeColaboracion_id, heladeraColocada_id) VALUES (1, NULL, 0, 1, 1, 1);
INSERT INTO colocaciones_de_heladera (id, fechaAlta, pendiente, colaborador_id, formaDeColaboracion_id, heladeraColocada_id) VALUES (2, NULL, 0, 1, 1, 2);

INSERT INTO acciones (uri, verbo) VALUES ("/admin", "get");
INSERT INTO acciones (uri, verbo) VALUES ("/admin/upload-csv", "post");
INSERT INTO acciones (uri, verbo) VALUES ("/admin/download-latest-report", "get");
INSERT INTO acciones (uri, verbo) VALUES ("/adminFormularios", "get");
-- INSERT INTO acciones (uri, verbo) VALUES ("/admin/modificarHeladera", "post");
-- INSERT INTO acciones (uri, verbo) VALUES ("/admin/eliminarHeladera", "post");

INSERT INTO acciones (uri, verbo) VALUES ("/tecnicos/cargar", "post");
-- INSERT INTO acciones (uri, verbo) VALUES ("/tecnico/{id}", "get");
-- INSERT INTO acciones (uri, verbo) VALUES ("/tecnicos/modificar", "post");
-- INSERT INTO acciones (uri, verbo) VALUES ("/tecnicos/eliminar", "post");

INSERT INTO acciones (uri, verbo) VALUES ("/coeficientesColaboracion", "get");

-- Acciones para colaboracionesr
INSERT INTO acciones (uri, verbo) VALUES ("/colaborar", "get");
INSERT INTO acciones (uri, verbo) VALUES ("/colaborar/donacionDeDinero", "post");
INSERT INTO acciones (uri, verbo) VALUES ("/colaborar/donacionDeVianda", "post");
INSERT INTO acciones (uri, verbo) VALUES ("/colaborar/distribucionDeVianda", "post");
INSERT INTO acciones (uri, verbo) VALUES ("/colaborar/registroPersonaVulnerable", "post");

-- Acciones para colaboraciones de personas jurídicas
INSERT INTO acciones (uri, verbo) VALUES ("/colaborarPersonaJuridica", "get");
INSERT INTO acciones (uri, verbo) VALUES ("/colaborarPersonaJuridica/heladera", "post");
INSERT INTO acciones (uri, verbo) VALUES ("/colaborarPersonaJuridica/ofertaProducto", "post");

-- Acciones para autorizar tarjetas
INSERT INTO acciones (uri, verbo) VALUES ('/autorizarTarjetaView', 'get');
INSERT INTO acciones (uri, verbo) VALUES ('/autorizarTarjeta', 'post');

-- Acciones para heladeras
INSERT INTO acciones (uri, verbo) VALUES ("/main", "get");
INSERT INTO acciones (uri, verbo) VALUES ("/heladeras", "get");
INSERT INTO acciones (uri, verbo) VALUES ("/heladeras/suscripcion", "post");
INSERT INTO acciones (uri, verbo) VALUES ("/heladeras/{id}", "put"); -- ??
INSERT INTO acciones (uri, verbo) VALUES ("/heladeras/{id}", "delete"); -- ??

-- Agregar la acción relacionada
INSERT INTO acciones (uri, verbo) VALUES ('/registroPersonaVulnerable', 'get');

-- Agregar Acciones de reporte Fallas
INSERT INTO acciones (uri, verbo) VALUES ('/reporteFallas', 'get');
INSERT INTO acciones (uri, verbo) VALUES ('/reporteFallas/guardarFalla', 'post');

-- Agregar las acciones relacionadas
INSERT INTO acciones (uri, verbo) VALUES ('/ofertas', 'get');
INSERT INTO acciones (uri, verbo) VALUES ('/oferta/{id}', 'post');
INSERT INTO acciones (uri, verbo) VALUES ('/misOfertas', 'get');
INSERT INTO acciones (uri, verbo) VALUES ('/ofertas/{id}/editar', 'post');
INSERT INTO acciones (uri, verbo) VALUES ('/ofertas/{id}/eliminar', 'post');
INSERT INTO acciones (uri, verbo) VALUES ('/ofertas/{id}/agregar-stock', 'post');

-- Acciones Tecnicos
INSERT INTO acciones (uri, verbo) VALUES ("/misReparaciones", "get");
INSERT INTO acciones (uri, verbo) VALUES ("/reparaciones/falla/{id}", "get");
INSERT INTO acciones (uri, verbo) VALUES ("/misReparaciones/realizarVisita", "post");

-- Sesion router acciones
INSERT INTO acciones (uri, verbo) VALUES ("/login", "get");
INSERT INTO acciones (uri, verbo) VALUES ("/login", "post");
INSERT INTO acciones (uri, verbo) VALUES ("/registro", "get");
INSERT INTO acciones (uri, verbo) VALUES ("/logout", "get");
INSERT INTO acciones (uri, verbo) VALUES ("/registro", "post");
INSERT INTO acciones (uri, verbo) VALUES ("/registroPersonaJuridica", "get");
INSERT INTO acciones (uri, verbo) VALUES ("/registroPersonaJuridica", "post");
INSERT INTO acciones (uri, verbo) VALUES ("/sinPermisos", "get");

-- SEGURO ES UN DESASTRE LO DE ABAJO PERO FUNCIONA, SOLO SI LO MANDAN A MANO EN EL MYSQL WORKBENCH
-- LO DE ARRIBA YA SE PERSISTE SOLO

-- Roles 'persona' (2) y 'personaJuridica' (3) pueden acceder a /heladeras (get) y /heladeras/suscripcion (post)
INSERT INTO roles_acciones (rol_id, accion_id) SELECT r.id, a.id FROM rol r INNER JOIN acciones a ON a.uri IN ('/heladeras', '/heladeras/suscripcion') AND a.verbo IN ('get', 'post') WHERE r.id IN (2, 3);

-- Relación de acciones para el rol 2
INSERT INTO roles_acciones (rol_id, accion_id) SELECT 2, a.id FROM acciones a WHERE a.uri IN ( '/colaborar' );

-- Relación de acciones para el rol 3
INSERT INTO roles_acciones (rol_id, accion_id) SELECT 3, a.id FROM acciones a WHERE a.uri IN ( '/colaborarPersonaJuridica' );

-- Roles 'persona' (2) y 'personaJuridica' (3) pueden acceder a /ofertas (get) y /oferta/{id} (post)
INSERT INTO roles_acciones (rol_id, accion_id) SELECT r.id, a.id FROM rol r INNER JOIN acciones a ON a.uri IN ('/ofertas', '/oferta/{id}') AND a.verbo IN ('get', 'post') WHERE r.id IN (2, 3);

-- Rol 'personaJuridica' (3) puede acceder a ciertas acciones adicionales
INSERT INTO roles_acciones (rol_id, accion_id) SELECT 3, a.id FROM acciones a WHERE a.uri IN ( '/misOfertas', '/ofertas/{id}/editar', '/ofertas/{id}/eliminar', '/ofertas/{id}/agregar-stock' ) AND a.verbo IN ('get', 'post', 'post', 'post');

-- Rol 'persona' (2) puede acceder a /registroPersonaVulnerable (get)
INSERT INTO roles_acciones (rol_id, accion_id) SELECT 2, a.id FROM acciones a WHERE a.uri = '/registroPersonaVulnerable' AND a.verbo = 'get';

-- Autorizacion de tarjetas rol 2, 3
INSERT INTO roles_acciones (rol_id, accion_id) SELECT 2, a.id FROM acciones a WHERE a.uri = '/autorizarTarjetaView' AND a.verbo = 'get';
INSERT INTO roles_acciones (rol_id, accion_id) SELECT 3, a.id FROM acciones a WHERE a.uri = '/autorizarTarjetaView' AND a.verbo = 'get';
INSERT INTO roles_acciones (rol_id, accion_id) SELECT 2, a.id FROM acciones a WHERE a.uri = '/autorizarTarjeta' AND a.verbo = 'post';
INSERT INTO roles_acciones (rol_id, accion_id) SELECT 3, a.id FROM acciones a WHERE a.uri = '/autorizarTarjeta' AND a.verbo = 'post';


-- Agregar permisos para roles 2 y 3
INSERT INTO roles_acciones (rol_id, accion_id) SELECT r.id, a.id FROM rol r INNER JOIN acciones a ON a.uri = '/reporteFallas' AND a.verbo = 'get' WHERE r.id IN (2, 3);
INSERT INTO roles_acciones (rol_id, accion_id) SELECT r.id, a.id FROM rol r INNER JOIN acciones a ON a.uri = '/reporteFallas/guardarFalla' AND a.verbo = 'post' WHERE r.id IN (2, 3);

-- Permisos relacionados con Sesion Router
INSERT INTO roles_acciones (rol_id, accion_id) SELECT r.id, a.id FROM rol r INNER JOIN acciones a ON a.uri IN ('/login', '/registro', '/logout', '/registroPersonaJuridica', '/sinPermisos') AND a.verbo IN ('get', 'post') WHERE r.id IN (2, 3, 4);

-- Permisos Técnicos para rol 4
INSERT INTO roles_acciones (rol_id, accion_id) SELECT 4, a.id FROM acciones a WHERE a.uri IN ('/misReparaciones', '/reparaciones/falla/{id}', '/misReparaciones/realizarVisita') AND a.verbo IN ('get', 'post');
