INSERT INTO person (identificacion, nombre, genero, edad, direccion, telefono) VALUES
('DEMO-SEED-01', 'Cliente Demo', 'M', 28, 'Av. Demo 100', '0999000111');

INSERT INTO client (identificacion, client_id, contrasena, estado) VALUES
('DEMO-SEED-01', CAST('a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11' AS UUID), 'demo123', TRUE);
